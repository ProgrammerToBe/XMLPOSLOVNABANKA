var menjanje = angular.module('menjanje', []);

menjanje.service('tokenService', function(){
	var token = "";
	
	var setToken = function(vrednost){
		token = vrednost;
	}
	
	var getToken = function(){
		return token;
	}

	return {
		setToken : setToken,
		getToken : getToken
	};
	
});

menjanje.factory('httpRequestInterceptor',['tokenService', function (tokenService) {
	
		
	  return {
	    request: function (config) {
	    	var t = tokenService.getToken();
	      config.headers['X-XSRF-Token'] = t;
	       return config;
	    }
	  };
	}]);
//'$httpProvider', 'tokenService', '$http', 
menjanje.config(function ($httpProvider) {
	
	
	$httpProvider.interceptors.push('httpRequestInterceptor');
	});





menjanje.controller('IzmenaCtrl', [ '$window', '$scope', '$http', '$compile', 'tokenService', 
		function($window, $scope, $http, $compile, tokenService) {

			$scope.userToChange = {}

			
			$scope.lozinka1 = "";
			
			$scope.init = function(){
				
				
				$http.get('/special/getSafeToken').
				then(function mySucces(response) {
					
					
					if(angular.equals(response.data.name, 'OHNO')){
						$window.location.href=response.data.value;
					}
					
					tokenService.setToken(response.data.value);
				});
			
			};
			
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

menjanje.directive("passwordVerify", function() {
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