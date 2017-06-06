
administrator.controller('IzmenaCtrl', [ '$window', '$scope', '$http', '$compile', 
		function($window, $scope, $http, $compile) {

			$scope.userToChange = {}

			
			$scope.lozinka1 = "";
			
			
			
			this.logoff = function(){
				
				$http({
				    method: 'POST',
				    url: '/logoff',
				    data: $scope.userToChange
				}).
				then(function mySucces(response) {
					
						//$window.location.href="http://localhost:8080/authentification/login.html";
						
				});
			};
			
			this.submitClick = function(){
				
				if(angular.equals($scope.userToChange, {})){
					return;
				}
				
				$http({
				    method: 'POST',
				    url: '/passwordChange',
				    data: $scope.userToChange
				}).
				then(function mySucces(response) {
					
					
					if(response.data.id == -1){
						toastr.error('Ne postoji korisnik sa takvim korisnickim imenom i lozinkom!');
						return;
					}else if(response.data.id == -2){
						toastr.error('Uneti podaci se ne poklapaju sa podacima ulogovanog korisnika');
						return;
					}else if(response.data.id == -5){
						
						$window.location.href=response.data.username;
						
						return;
					}else{
						toastr.success('Uspesno logovanje!');
						return;
					}
				
					
				});
			};
			

		} ]);

administrator.directive("passwordVerify", function() {
	   return {
		      require: "ngModel",
		      scope: {
		        passwordVerify: '='
		      },
		      link: function(scope, element, attrs, ctrl) {
		        scope.$watch(function() {
		            var combined;

		            if (scope.passwordVerify || ctrl.$viewValue) {
		               combined = scope.passwordVerify + '_' + ctrl.$viewValue; 
		            }                    
		            return combined;
		        }, function(value) {
		            if (value) {
		                ctrl.$parsers.unshift(function(viewValue) {
		                    var origin = scope.passwordVerify;
		                    if (origin !== viewValue) {
		                        ctrl.$setValidity("passwordVerify", false);
		                        return undefined;
		                    } else {
		                        ctrl.$setValidity("passwordVerify", true);
		                        return viewValue;
		                    }
		                });
		            }
		        });
		     }
		   };
		});