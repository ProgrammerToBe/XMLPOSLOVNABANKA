(function(){
var modali = angular.module('modalsModule',[]);
	
/*
type je iz skupa {'success, info, error, warning,'}
*/
	toaster = function(type,content){
								toastr.options = {
								  "closeButton": true,
								  "debug": false,
								  "newestOnTop": true,
								  "progressBar": true,
								  "positionClass": "toast-top-center",
								  "preventDuplicates": false,
								  "onclick": null,
								  "showDuration": "300",
								  "hideDuration": "1000",
								  "timeOut": "3000",
								  "extendedTimeOut": "1000",
								  "showEasing": "swing",
								  "hideEasing": "linear",
								  "showMethod": "slideDown",
								  "hideMethod": "slideUp"
								}
								Command: toastr[type](content)

	}

	modali.directive('friendsInvite',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/friendsInviteModal.html',
			controller: 'FriendsInviteController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'friendsInviteCtrl'
		};
	});

	modali.controller('FriendsInviteController',['$log','$scope',function($log,$scope){
		// $('#registerInfo').modal({ show: false});

		$scope.inviteFriend = function(friend){
			$scope.friendsEstablished.choosen.push(friend);
			var poz = $scope.friendsEstablished.all.indexOf(friend);
			$scope.friendsEstablished.all.splice(poz,1);
		}

		$scope.dontInviteFriend = function(friend){
			$scope.friendsEstablished.all.push(friend);
			var poz = $scope.friendsEstablished.choosen.indexOf(friend);
			$scope.friendsEstablished.choosen.splice(poz,1);
		}
	}]);

	modali.directive('chooseRestaurant',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/reservationChooseRestaurantModal.html',
			controller: 'ChooseRestaurantController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'ChooseRestaurantCtrl'
		};
	});

	modali.controller('ChooseRestaurantController',['$log','$scope',function($log,$scope){

		$scope.chooseRestaurant = function(restaurant){

			$scope.ws.disconnect();

			if($scope.reservation.sala!=undefined && $scope.reservation.sala!=null)
				$scope.sale.push($scope.reservation.sala); // sacuvamo staru salu


			if($scope.reservation.restoran!=undefined && $scope.reservation.restoran!=null)
				$scope.restaurants.push($scope.reservation.restoran); // skinem stari zeljeni restoran
			$scope.reservation.restoran = restaurant; // ubacim novi zeljeni restoran



			$scope.reservation.sala = undefined; // obrisemo salu
			$scope.reservation.sto = {}; //obrisemo sto
			$scope.space = undefined;
			$scope.reservation.meal = []; //obrisemo sto


			if(restaurant!=undefined){
				var poz = $scope.restaurants.indexOf(restaurant); // izvadim ga iz liste mogucih restorana
				$scope.restaurants.splice(poz,1);

				$scope.sale = $scope.reservation.restoran.sale;
				$scope.menu = $scope.reservation.restoran.radnoMestoProizvodno;
				
			}else{

				$scope.sale = [];
				$scope.menu = [];
			}


			
			
			
			
		}


	}]);

	modali.directive('chooseRoom',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/reservationChooseRoomModal.html',
			controller: 'ChooseRoomController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'ChooseRoomCtrl'
		};
	});


	modali.controller('ChooseRoomController',['$log','$scope',function($log,$scope){

		$scope.chooseRoom = function(room){

			if($scope.reservation.sala!=undefined && $scope.reservation.sala!=null)
				$scope.sale.push($scope.reservation.sala); // skinem stari zeljeni restoran
			$scope.reservation.sala = room; // ubacim novi zeljeni restoran

			var poz = $scope.sale.indexOf(room); // izvadim ga iz liste mogucih restorana
			$scope.sale.splice(poz,1);

			$scope.reservation.sto.code = ""; //obrisemo sto
			$scope.reservation.sto = {}; //obrisemo sto

			// $scope.ws.wsRoom(room);
			$scope.reservation.meal = [];
			$scope.loadRoom(room);

		}
	}]);


	modali.directive('chooseMeal',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/reservationChooseMealModal.html',
			controller: 'OrderMealController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'OrderMealCtrl'
		};
	});

	modali.controller('OrderMealController',['$log','$scope',function($log,$scope){

		$scope.cancelProduct = function(narudzba){

			var idx = $scope.reservation.meal.indexOf(narudzba);
			$scope.reservation.meal.splice(idx,1);
		}

		$scope.orderProduct = function(product){

			var narudzba = {};
			narudzba.proizvod = product;
			$scope.reservation.meal.push(narudzba);
			toaster('success','you added product to you meal list');
		}

	}]);



	modali.directive('chooseTable',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/reservationChooseTableModal.html',
			controller: 'ChooseTableController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'ChooseTableCtrl'
		};
	});

	modali.controller('ChooseTableController',['$log','$scope',function($log,$scope){

		$log.log('matrica stolova se prikazuje');

		$scope.selectTable = function(table){

			$scope.reservation.sto.element = table;
			$scope.reservation.sto.code = (table.positionX-1)+'x'+(table.positionY-1);
			toaster('success','Table is selected.');

		};

	}]);


	modali.directive('addMeal',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/reservationAddMealModal.html',
			controller: 'AddMealController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'AddMealCtrl'
		};
	});


	modali.controller('AddMealController',['$log','$scope','$http',function($log,$scope,$http){

		$log.log('prikaz za dodavanje obroka se prikazuje');

		$scope.dodajNarudzbu = function(product){

			var newNarudzba = {};
			newNarudzba.proizvod = product;
			newNarudzba.zeljenoVreme = $scope.addModal.rezervacija.pocetak;
			newNarudzba.status="ORDERED";

			if($scope.addModal.toServer){
				$http({
					method:"POST",
					url:'/reservation/dodajNarudzbu/'+$scope.addModal.poseta.id,
					data : newNarudzba
				}).then(
					function successDodajNarudzbu(response){
						$scope.addModal.poseta.narudzbe.push(response.data);


					},function errorDodajNarudzbu(response){
						toaster('error',"You can't order this product");
					}
				);

			}else{

				$scope.addModal.poseta.narudzbe.push(newNarudzba);
			}

		}

	}]);

	modali.directive('gradesFeedback',function(){
		return{
			restrict:'EA',
			templateUrl:'directives/modals/template/gradesFeedback.html',
			controller: 'GradesFeedbackController',
			// controller: function(){
			// 		$('#registerInfo').modal({ show: false});
			// },
			controllerAs: 'GradesFeedbackCtrl'
		};
	});


	modali.controller('GradesFeedbackController',['$log','$scope','$http','$templateCache',function($log,$scope,$http,$templateCache){
		
		$scope.sendFeedback = function(){
			// $log.log('grades to send -> general: '+$scope.grades.general + ', meal: '+$scope.grades.meal+', service: '+$scope.grades.service );

			// var racunIndex = $scope.$parent.user.racuni.indexOf($scope.racun);
			// $scope.$parent.user.racuni.splice(racunIndex,1);

			$http({
				method:'POST',
				url:'/oceni/'+$scope.grades.general+'/'+$scope.grades.service+'/'+$scope.grades.meal,
				data:$scope.racun
			}).then(
				function successFeedback(resposne){
					var racunIndex = $scope.racuni.indexOf($scope.racun);
					$scope.racuni.splice(racunIndex,1);

					var dozivljaj = resposne.data;
					var dozivljaji = $scope.$parent.user.dozivljaji;
					var novi = true;

					for(var dozivljajIndex in dozivljaji){
						var currDozivljaj = dozivljaji[dozivljajIndex];
						if(currDozivljaj.restoran.id = dozivljaj.restoran.id){
							novi = false;
							currDozivljaj.brojOcena = dozivljaj.brojOcena;
							currDozivljaj.ukupnaOcena = dozivljaj.ukupnaOcena;
							//$templateCache.removeAll();
						}
					} // for

					if(novi==true){
						dozivljaji.push(dozivljaj);
						//$templateCache.removeAll();
					}

				}
			);
		};

	}]);




})(); // end