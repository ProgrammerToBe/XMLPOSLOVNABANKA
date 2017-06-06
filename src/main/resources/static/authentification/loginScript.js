var logovanje = angular.module('logovanje', []);

logovanje.controller('LogovanjeCtrl', ['$window', '$scope', '$http', '$compile',function($window, $scope, $http, $compile){

	$scope.user = {};
	
	this.metoda2 = function(){
		$http.get('/special/testToken').
	    then(function(response) {
	    	
	    });
	
	}
	
	this.submitClick = function(){
		
		if(angular.equals($scope.user, {})){
			toastr.error('Korisnicko ime i lozinka moraju biti uneti!')
			return;
		}else if(angular.isUndefined($scope.user.username) || angular.isUndefined($scope.user.password)){
			toastr.error('I korisnicko ime i lozinka moraju biti uneti!')
			return;
		}
	
		$http({
		    method: 'POST',
		    url: '/special/loginSubject',
		    data: $scope.user
		}).
		then(function mySucces(response) {
			
			if(response.data.id == -1){
				toastr.error('Ne postoji korisnik sa takvim korisnickim imenom i lozinkom!');
				return;
			}else if(response.data.id == -2){
				toastr.error('Neko je vec ulogovan');
				return;
			}else if(response.data.id == -3){
				toastr.error('Autentifikacija nije prosla kako treba!');
				return;
			}else if(response.data.id == -4){
				toastr.error('Vasa inicijalna lozinka je istekla!Kontaktirajte administratora da biste dobili novu.');
				return;
			}else if(response.data.id == -5){
				
				$window.location.href=response.data.url;
				
				return;
			}else{
				toastr.success('Uspesno logovanje!');
				return;
			}
		
		});

		
	}
	
}]);
