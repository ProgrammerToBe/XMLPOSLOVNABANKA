(function(){
var direktive = angular.module('customerDirectives');

	direktive.controller('reservationsActualController',['$window','$http','$scope','localStorageService','$route','$log', function($window,$http,$scope,localStorageService,$route,$log){
		
		$log.log("reservationsActualController");
		var user = $scope.$parent.user;

		$scope.my = {};
		$scope.others = {};
		$scope.others.invitations = {};
		$scope.others.reservations = {};



		$http({
			method: 'GET',
			url:"reservation/getMyActiveReservations/"+user.id
		}).then(
			function successGetAllMy(response){
				$scope.my.reservations = response.data;

				for(var indexRezervacija in $scope.my.reservations){
					var currRezervacija = $scope.my.reservations[indexRezervacija];
					currRezervacija.mandatory = false;

					for(var indexPoseta in currRezervacija.poseta ){
						var currPoseta = currRezervacija.poseta[indexPoseta];
						if(currPoseta.musterija.id == user.id)
							currRezervacija.myVisit = currPoseta;
						if(currPoseta.mandatory)
							currRezervacija.mandatory = true;
					}
				}
			}
		);

		$http({
			method: 'GET',
			url:"reservation/getOthersActiveInvitations/"+user.id
		}).then(
			function successGetOthersActiveInvications(response){
				var sveMojePozivnice = response.data;
				$scope.others.invitations = response.data;

				for(var indexPozivnica in $scope.others.invitations){

					var mojaPozivnica = $scope.others.invitations[indexPozivnica];


					// ako sam potvrdio poziv, imam posetu, hocu da prikazem njene narudzbine
					if(mojaPozivnica.odgovor == 'POTVRDJENA'){

						var currRezervacija = mojaPozivnica.rezervacija;

						for(var indexPoseta in currRezervacija.poseta ){
							var currPoseta = currRezervacija.poseta[indexPoseta];
							if(currPoseta.musterija.id == user.id)
								currRezervacija.myVisit = currPoseta;
						}
					}//if(mojaPozivnica.odgovor == 'POTVRDJENA'){

					if(mojaPozivnica.odgovor == 'POSLATA'){

						var currRezervacija = mojaPozivnica.rezervacija;
						var mojaPoseta ={};
						mojaPoseta.narudzbe = [];
						currRezervacija.myVisit = mojaPoseta;
					}//if(mojaPozivnica.odgovor == 'POSLATA'){

				}

				/*
				for(var poz in sveMojePozivnice){
					var currentPozivnica = sveMojePozivnice[poz];
					var currentRezervacija = currentPozivnica.rezervacija;
					var currentRestoran = currentRezervacija.restoran;
				}
				*/
				
	
				

			}
		);


		$scope.deleteReservation = function(rezervacija){
			$http({
				method: 'POST',
				url: '/reservation/deleteReservation/'+rezervacija.id
			}).then(
				function successDeleteReservation(response){
					toaster('success',response.data);

					var index = $scope.my.reservations.indexOf(rezervacija); // obrisemo rezervaciju da je ne vidimo
					$scope.my.reservations.splice(index,1);


				}, function errorDeleteReservation(response){
					toaster('error',response.data);
				}
			);
		};

		$scope.acceptInvitation = function(invitation,baseInvitation){
			$http({
				method:'POST',
				url:'/reservation/acceptInvitation/'+invitation.id,
				data: baseInvitation.rezervacija.myVisit.narudzbe
			}).then(
				function successAcceptInvitation(response){
					toaster('success',"You successfully accepted invitation!");
					invitation.odgovor = 'POTVRDJENA';
					baseInvitation.odgovor = 'POTVRDJENA';
					baseInvitation.rezervacija.myVisit = response.data;

				}, function errorAcceptInvitation(response){
					toaster('error',"Some of products that you ordered doesn't exist. Please try again.");
				}

			);
		};

		$scope.cancelInvitation = function(invitation,baseInvitation){
			$http({
				method:'POST',
				url:'/reservation/cancelInvitation/'+invitation.id
			}).then(
				function successCancelInvitation(response){
					toaster('success',response.data);
					invitation.odgovor = 'OTKAZANA';
					baseInvitation.odgovor = 'OTKAZANA';

					var index = $scope.others.invitations.indexOf(baseInvitation);
					$scope.others.invitations.splice(index,1);

				}, function errorCancelInvitation(response){
					toaster('error',response.data);
				}

			);
		};

		$scope.ukloniNarudzbu = function(narudzba, myVisit){

			if(myVisit.id!=undefined && myVisit.id!=null){
				
				$http({
					method:'POST',
					url:'/reservation/ukloniNarudzbu/'+narudzba.id
				}).then(
					function successUkloniNarudzbu(response){
						var pos = myVisit.narudzbe.indexOf(narudzba);
						myVisit.narudzbe.splice(pos,1);

					},function errorUkloniNarudzbu(response){
						toaster('error',"You can't cancel this order, it's in progress.")
					}
				);	

			}else{
				var pos = myVisit.narudzbe.indexOf(narudzba);
				myVisit.narudzbe.splice(pos,1);				
			}
		};


		$scope.adjustModal = function(myVisit, rezervacija){

			$scope.addModal = {}; 														$log.log(' adjustModal');
			$scope.addModal.poseta = myVisit;											$log.log('$scope.addModal.poseta.id '+ $scope.addModal.poseta.id);
			$scope.addModal.rezervacija = rezervacija;									$log.log('$scope.addModal.rezervacija.id '+ $scope.addModal.rezervacija.id);
			$scope.addModal.restoran = rezervacija.restoran;							$log.log('$scope.addModal.restoran.naziv '+ $scope.addModal.restoran.naziv);
			$scope.addModal.toServer = myVisit.id!=undefined && myVisit.id!=null;		$log.log('$scope.addModal.toServer '+ $scope.addModal.toServer );
		};


		var stompInvitationAcceptFriends = new SockJS('/stompInvitationAcceptFriends');
		var topicInvitationAcceptFriends = Stomp.over(stompInvitationAcceptFriends);
		topicInvitationAcceptFriends.connect({}, function(frame) {
			topicInvitationAcceptFriends.subscribe("/topicInvitationAcceptFriends/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				
				var rezervacijaId = notification.rezervacija.id;
				var pozivnicaId = notification.id;

				$scope.wsAcceptInvitation(rezervacijaId,pozivnicaId,"POTVRDJENA");
				$scope.$apply();

				//notifyScope.$parent.friends.getNotificationsCount(true);
			});

		});//connect


		var stompInvitationCancelFriends = new SockJS('/stompInvitationCancelFriends');
		var topicInvitationCancelFriends = Stomp.over(stompInvitationCancelFriends);
		topicInvitationCancelFriends.connect({}, function(frame) {
			topicInvitationCancelFriends.subscribe("/topicInvitationCancelFriends/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				
				var rezervacijaId = notification.rezervacija.id;
				var pozivnicaId = notification.id;

				$scope.wsAcceptInvitation(rezervacijaId,pozivnicaId,"OTKAZANA");
				$scope.$apply();

				//notifyScope.$parent.friends.getNotificationsCount(true);
			});

		});//connect


		$scope.wsAcceptInvitation = function(rezervacijaId,pozivnicaId,newStatus){

				var targetReservation = undefined;


				for(var rezervacijaIndex in $scope.my.reservations){
					var currRezervacija = $scope.my.reservations[rezervacijaIndex];
					if(currRezervacija.id==rezervacijaId){
						targetReservation = currRezervacija;
						break;
					}
				}
				if(targetReservation==undefined){
					for(var pozivnicaIndex in $scope.others.invitations){
						var currMyPozivnica = $scope.others.invitations[pozivnicaIndex];
						if(currMyPozivnica.rezervacija.id==rezervacijaId){
							targetReservation = currMyPozivnica.rezervacija;
							break;
						}
					}
				}

				if(targetReservation!=undefined){
					for(pozivnicaIndex in targetReservation.zvanice){

						var currPozivnica = targetReservation.zvanice[pozivnicaIndex];
						if(currPozivnica.id==pozivnicaId)
							currPozivnica.odgovor = newStatus;
							break;
					}
				}
		}; //$scope.wsAcceptInvitation 



		var stompReservationsCreateFriends = new SockJS('/stompReservationsCreateFriends');
		var topicReservationsCreateFriends = Stomp.over(stompReservationsCreateFriends);
		topicReservationsCreateFriends.connect({}, function(frame) {
			topicReservationsCreateFriends.subscribe("/topicReservationsCreateFriends/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				

				var novaRezervacija = notification.rezervacija;
				var mojaPoseta ={};
				mojaPoseta.narudzbe = [];
				novaRezervacija.myVisit = mojaPoseta;

				$scope.others.invitations.push(notification);

				$scope.$apply();

				//notifyScope.$parent.friends.getNotificationsCount(true);
			});

		});//connect



		var stompReservationsDeleteFriends = new SockJS('/stompReservationsDeleteFriends');
		var topicReservationsDeleteFriends = Stomp.over(stompReservationsDeleteFriends);
		topicReservationsDeleteFriends.connect({}, function(frame) {
			topicReservationsDeleteFriends.subscribe("/topicReservationsDeleteFriends/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				
				for(var pozivnicaIndex in $scope.others.invitations){
					var currPozivnica = $scope.others.invitations[pozivnicaIndex];
					if(currPozivnica.id==notification.id){
						$scope.others.invitations.splice(pozivnicaIndex,1);
						break;
					}
				}

				$scope.$apply();

				//notifyScope.$parent.friends.getNotificationsCount(true);
			});
		});//connect


	}]);

})(); // end

