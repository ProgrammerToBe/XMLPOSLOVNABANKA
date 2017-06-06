(function(){
var direktive = angular.module('customerDirectives');

	direktive.controller('reservationsCreateController',['$window','$http','$scope','localStorageService','$route','$log', function($window,$http,$scope,localStorageService,$route,$log){
		
		
		$scope.sender = {};
		$scope.reservation = {};

		
		var currentDate =  new Date();
		currentDate.setHours(0,0,0,0);
		$scope.reservation.date = currentDate;
		
		$scope.applyDisabled = false;
		$scope.datepicker = {};
		var minDay = new Date();
		var maxDay = new Date();
		maxDay.setDate(minDay.getDate()+14);
		minDay.setHours(0,0,0,0);
		maxDay.setHours(0,0,0,0);
		$scope.datepicker.dateOptions = {
		    //customClass: getDayClass,
		    minDate: minDay,
		    maxDate: maxDay,
		    showWeeks: false,
		    startingDay : 1,
		    popupPlacement : 'bottom',
		    showButtonBar : false
		};
		$scope.datepicker.opened = false;
		$scope.datepicker.format = 'dd.MM.yyyy';
		$scope.datepicker.open = function() {
		    $scope.datepicker.opened = true;
		};


		var timeStart = new Date();
		timeStart.setDate(0);
		timeStart.setHours( 14,0,0,0 );
    	var timeEnd = new Date();
		timeEnd.setDate(0);
		timeEnd.setHours( 15,0,0,0 );
    	$scope.reservation.timeStart = timeStart;
    	$scope.reservation.timeEnd = timeEnd;

    	$scope.$watchGroup(['reservation.date','reservation.timeStart','reservation.timeEnd'],function whenChanged(newValues,oldValues){
			
			timeBoundaries = $scope.getTimeBoundaries();
			var timeStart = timeBoundaries[0];
			var timeEnd = timeBoundaries[1];				
			
			$scope.sender.pocetak  = timeStart;
			$scope.sender.kraj  = timeEnd;
	
			$log.log('when changed');
			
			
			if($scope.space!=undefined && $scope.space!=null && $scope.space.width!=undefined){

				$scope.detectionToDo = true; // promenjeno je vreme, na prom prikazu ce biti potrebno opet detektovati sve rezervacije...
				
				
				if($scope.reservation.sto!=undefined && $scope.reservation.sto!=null && $scope.reservation.sto.element!=undefined && $scope.reservation.sto.element!=null){
					var myTable = $scope.reservation.sto.element;
					$scope.checkOneTableReservations(timeStart,timeEnd,myTable.positionX-1,myTable.positionY-1);
					$scope.detectMyTable('Table which you has choosen is used in selected period');
				}
				
			}
			
			
			

    	});




    	var minTime = new Date();
    	minTime.setDate(0);
    	minTime.setHours(8,0,0,0);
    	var maxTime = new Date();
    	maxTime.setDate(0);
    	maxTime.setHours(23,45,0,0);
    	$scope.timepicker = {};
    	$scope.timepicker.min = minTime;
    	$scope.timepicker.max = maxTime;

     	$scope.$watch('reservation.timeStart',function setupUpDistance(newValues,oldValues){
    		$scope.timepicker.distanceUp = addMinutes($scope.reservation.timeStart,30);
    	});
    	$scope.$watch('reservation.timeEnd',function setupUpDistance(newValues,oldValues){
    		$scope.timepicker.distanceDown = addMinutes($scope.reservation.timeEnd,-30);
    	});


    	$scope.restaurants = [];
    	$scope.sale = [];
    	$scope.reservation.restoran = undefined;
    	$scope.reservation.sala = undefined;
    	$scope.reservation.table = {};
    	$scope.reservation.meal = [];
    	$http({
    		method:'GET',
    		url:'/allRestaurants',
    	}).then(
    		function succesGetRestaurants(response){
    			$scope.restaurants = response.data;
    		}
    	);


    	var user = $scope.$parent.user;
		$scope.friendsEstablished = {};
		$scope.friendsEstablished.hasResult = false;
		$scope.friendsEstablished.all = [];
		$scope.friendsEstablished.choosen = [];
    	$scope.friendsEstablished.allMyFriends = function(refresh){

	    	var relationshipPattern = {};
	    	relationshipPattern.source = user;
	    	relationshipPattern.type = "Established";

	    	$http({
	    		method:'POST',
	    		url:'/relationships/getUserRelationships',
	    		data:relationshipPattern
	    	}).then(
	    		function successGetFriends(response){
	    			$scope.friendsEstablished.all = response.data;

	    			if($scope.friendsEstablished.all.length > 0 )
	    				$scope.friendsEstablished.hasResult = true;
	    			else
	    				$scope.friendsEstablished.hasResult = false;


	    			if(refresh){
	    				$log.log("friendsEstablished -> allMyFriends");
	    				$scope.$apply();
	    			}
	    		},function errorGetFriends(response){
	    			$scope.friendsEstablished.all = []; 
	   				$scope.friendsEstablished.hasResult = false;
	    		}
	    			
	    	);

	    }; // pokrece se ova metoda nakon definicije!
	    $scope.friendsEstablished.allMyFriends(false);

/*
		var stompRoom = new SockJS('/stompRoom');
	    $scope.ws.stompRoom = stompRoom;
	    $scope.ws.topicRoom = Stomp.over(stompRoom);
	    $scope.ws.topicRoom.connect({},function(frame){});

	    $scope.ws.wsRoom = function(room){

	    	if($scope.ws.topicSubscription!=undefined){
		    	$scope.ws.topicSubscriptionPlus.unsubscribe();
		    	$scope.ws.topicSubscriptionMinus.unsubscribe();
		    }
		    $scope.ws.topicSubscriptionPlus = $scope.ws.topicRoom.subscribe('/topicRoom/plus/'+room,function(data){
		    	$log.log('dodata je rezervacija sobi');
		    });
		    $scope.ws.topicSubscriptionMinus = $scope.ws.topicRoom.subscribe('/topicRoom/minus/'+room,function(data){
		    	$log.log('skinuta je rezervacija sobi');
		    });
	    };
*/
	    $scope.loadRoom = function(room){
	    	$scope.space = {}

	    	$http({
	    		method:'GET',
	    		url:'/getSalaById/'+room.id,
	    	}).then(
	    		function successRoomLoad(response){
					
					$scope.ws.disconnect(); // skinemo stare ws ako ih je bilo
					
	    			var room = response.data;
	    			$scope.space.core = room;

	    			var i=0;
	    			var width = [];
	    			var height = [];
	    			for(i=0; i<room.height;i++)
	    				height.push(i);
	    			for(i=0; i<room.width;i++)
	    				width.push(i);
	    			$scope.space.width = width;
	    			$scope.space.height = height;
					$scope.space.detectionToDo = true;
	    			$log.log('height = ' + height);

	    			$scope.space.map = new Array(room.width);
	    			for(i=0; i< room.width; i++)
	    				$scope.space.map[i] = new Array(room.height);
	    			// width je vertikala, broj redova, heigth broj kolona


					for(var idx in room.sto){
						var sto = room.sto[idx];
						var x = sto.positionX;
						var y = sto.positionY;
						$scope.space.map[x-1][y-1] = sto;
					}

					$scope.ws.connect(room); // postavimo nove ws
	    		}
	    	);
	    };
		
		$scope.detectAllReservations = function(){

			if($scope.space!=undefined && $scope.space!=null && $scope.space.width!=undefined && $scope.space.detectionToDo){

				$scope.detectionToDo = false; // odradjena je prva detekcija
				
				timeBoundaries = $scope.getTimeBoundaries();
				var timeStart = timeBoundaries[0];
				var timeEnd = timeBoundaries[1];
				
				for(var row in $scope.space.width){
					for(var col in $scope.space.height){
						
						$scope.checkOneTableReservations(timeStart,timeEnd,row,col);
					}
				}//for(var row in $scope.space.width){
			}
		};
		
		$scope.getTimeBoundaries = function(){
			
			// koristim timeStart i timeEnd za pretragu!
				var timeStart = new Date($scope.reservation.timeStart);
				var timeEnd = new Date($scope.reservation.timeEnd);
				var date = $scope.reservation.date;
				
				timeStart.setDate(date.getDate() );
				timeEnd.setDate(date.getDate() );
				timeStart.setMonth(date.getMonth());
				timeEnd.setMonth(date.getMonth());
				timeStart.setYear(date.getFullYear());
				timeEnd.setYear(date.getFullYear());
				
				return [timeStart,timeEnd];
		};
		
		$scope.checkOneTableReservations = function(timeStart, timeEnd, row, col){
			
			var currTable = $scope.space.map[row][col];
			if(currTable!=undefined){
				currTable.free = true; // iteriramo za svaki sto koji postoji
							
				var rezervacije = currTable.rezervacija; 
				for(var idxRezervacija in rezervacije){
								
					// ovde je kljuc provere, da li ima vec rezervacija u nasem terminu
					rezervacija = rezervacije[idxRezervacija];  
					if( $scope.checkIntervalsOverlapping(rezervacija.pocetak,rezervacija.kraj,timeStart,timeEnd) ) 
						currTable.free = false;
		
				}//for(var currRezervacija in rezervacije){
						
			}//if(currTable!=undefined){
			
		};
		
		// vraca odgovor da li se 2 termina preklapaju
		$scope.checkIntervalsOverlapping = function(start1,end1,start2,end2){
			
			if(start1>=start2 && start1<=end2)
				return true;
			if(end1>=start2 && end1<=end2)
				return true;
			if(start2>=start1 && start2<=end1)
				return true;
			if(end2>=start1 && end2<=end1)
				return true;
			
			return false;
			
		};
		
		$scope.detectMyTable = function(errorMessage){
			
			if($scope.reservation.sto!=undefined && $scope.reservation.sto!=null && $scope.reservation.sto.element!=undefined && $scope.reservation.sto.element!=null){
				var myTable = $scope.reservation.sto.element;
				
				if(myTable.free == false){
					
					toaster('error',errorMessage);
					$scope.reservation.sto.code = ""; //obrisemo sto
					$scope.reservation.sto = {}; //obrisemo sto
				}
			}
		};






		$scope.send = function(){

			$log.log('time defined');
/*			
			if($scope.sender.pocetak< new Date()){
				toaster('warning',"You can't make reservation for some period in past");
				return;
			}
*/
			$scope.sender.pocela = false;
			$scope.sender.otkazana = false;
			$scope.sender.organizator = user;
			// $scope.sender.organizator = Object.clone(user);
			// delete $scope.sender.organizator.rezervacije;
			$scope.sender.restoran = $scope.reservation.restoran;
			$scope.sender.sala = $scope.reservation.sala;
			// $scope.sender.sto = Object.clone($scope.reservation.sto.element);
			// var newObject = jQuery.extend({}, oldObject);
			$scope.sender.sto = jQuery.extend({}, $scope.reservation.sto.element);
			delete $scope.sender.sto.free;
			
			$scope.sender.zvanice = [];
			for(var index in $scope.friendsEstablished.choosen){
				var pozivnica = {};
				//pozivnica.rezervacija = $scope.sender;
				pozivnica.zvanica = ($scope.friendsEstablished.choosen[index]).destination;
				pozivnica.odgovor = 'POSLATA';
				$scope.sender.zvanice.push(pozivnica);
			}

			
			var posetaOrganizatora = {};
			posetaOrganizatora.musterija = user;
			posetaOrganizatora.narudzbe = [];
			for(var obrok in $scope.reservation.meal){
				var narudzba = {};
				narudzba.zeljenoVreme = $scope.sender.pocetak;
				narudzba.status = 'ORDERED';	
				narudzba.proizvod = $scope.reservation.meal[obrok].proizvod;
				posetaOrganizatora.narudzbe.push(narudzba);
			}
			$scope.sender.poseta = [posetaOrganizatora]; // prva poseta

			// NAPRAVLJEN SENDER DTO!!!
			//$scope.applyDisabled = true;

			$http({
				method:'POST',
				url:'/reservation/createNew',
				data:$scope.sender
			}).then(
				function successCreateReservation(response){

					var novaRezervacija = response.data;
					novaRezervacija.myVisit = novaRezervacija.poseta[0];
					$scope.my.reservations.push(response.data);
					
					
					toaster('success','Your reservation is accepted!');
					
					
					$scope.chooseRestaurant(undefined);
					$scope.reservation.meal = [];
					//$scope.friendsEstablished.choosen = [];
					

				},function errorCreateReservation(response){

					
					toaster('error',"You can't create this reservation");

				}
			);
		
		};

		
		addMinutes = function (date, minutes) {
				return new Date(date.getTime() + (minutes*60000) );
		}	

		
		$scope.ws = {};
		$scope.ws.create = {};
		$scope.ws.create.stomp  = new SockJS('/stompReservationsCreateRoom');
		$scope.ws.create.topic = Stomp.over($scope.ws.create.stomp);
		$scope.ws.create.topic.connect({},function(frame){});
		
		$scope.ws.delete = {};
		$scope.ws.delete.stomp  = new SockJS('/stompReservationsDeleteRoom');
		$scope.ws.delete.topic = Stomp.over($scope.ws.delete.stomp);
		$scope.ws.delete.topic.connect({},function(frame){});
		
		$scope.ws.create.connection = undefined;
		$scope.ws.delete.connection = undefined;
		
		$scope.ws.disconnect = function(){
			if($scope.ws.create.connection != undefined){
					$scope.ws.create.connection.unsubscribe();
					$scope.ws.create.connection = undefined;
					$scope.ws.delete.connection.unsubscribe();
					$scope.ws.delete.connection = undefined;
			}
		};
		
		
		$scope.ws.connect = function(sala){
			
			$scope.ws.create.connection = $scope.ws.create.topic.subscribe("/topicReservationsCreateRoom/"+sala.id,function(data){
				var notification = JSON.parse(data.body);
				
				timeBoundaries = $scope.getTimeBoundaries();
				var timeStart = timeBoundaries[0];
				var timeEnd = timeBoundaries[1];	
				
				var sto = notification.sto;
				$scope.space.map[sto.positionX-1][sto.positionY-1].rezervacija.push(notification);
				$scope.checkOneTableReservations(timeStart,timeEnd,sto.positionX-1,sto.positionY-1);
				$scope.detectMyTable('Somebody just has used table that you select during selected period');
				$scope.$apply();
			});
			
			
			$scope.ws.delete.connection = $scope.ws.create.topic.subscribe("/topicReservationsDeleteRoom/"+sala.id,function(data){
				var notification = JSON.parse(data.body);
				
				timeBoundaries = $scope.getTimeBoundaries();
				var timeStart = timeBoundaries[0];
				var timeEnd = timeBoundaries[1];	
				
				var sto = notification.sto;
				var rezervacije = $scope.space.map[sto.positionX-1][sto.positionY-1].rezervacija;
				for(var indexRezervacija in rezervacije){
					var rezervacija = rezervacije[indexRezervacija];
					if(rezervacija.id==notification.id)
						rezervacije.splice(indexRezervacija,1); // obrisemo trenutnu rezervaciju
				}
				
				$scope.checkOneTableReservations(timeStart,timeEnd,sto.positionX-1,sto.positionY-1);
				$scope.$apply();
			});
			
			
		};
		

	}]);

	
	


})(); // end