var administratorBanke = angular.module('administratorBanke', ['ui.router']);

administratorBanke.service('tokenService', function(){
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

administratorBanke.factory('httpRequestInterceptor',['tokenService', function (tokenService) {
	
		
	  return {
	    request: function (config) {
	    	var t = tokenService.getToken();
	      config.headers['X-XSRF-Token'] = t;
	       return config;
	    }
	  };
	}]);




administratorBanke.config(function($stateProvider, $httpProvider) {
    
	var homeState = {
		    name: 'home',
		    url: '/home',
		    template: '<h3>hello world!</h3>'
		  }

	var permisijeState = {
		    name: 'permisije',
		    url: '/permisije',
		    templateUrl: 'roleIpermisije/Permisije.html'
		  }

	var roleState = {
		    name: 'role',
		    url: '/role',
		    templateUrl: 'roleIpermisije/Role.html'
		  }

	
	var registracijaState = {
		    name: 'registracija',
		    url: '/registracija',
		    templateUrl: 'registracija/registracija.html'
		  }
	
	var registracijaPravnihState = {
		    name: 'registracijaPravnih',
		    url: '/registracijaPravnih',
		    templateUrl: 'registracija/registracijaPravnih.html'
		  }
	
	var izmenaLozinke = {
		    name: 'izmenaLozinke',
		    url: '/izmenaLozinke',
		    templateUrl: 'izmena/izmenaHTML.html'
		  }

	$stateProvider.state(homeState);
	$stateProvider.state(permisijeState);
	$stateProvider.state(roleState);
	$stateProvider.state(registracijaState);
	$stateProvider.state(registracijaPravnihState);
	$stateProvider.state(izmenaLozinke);
	$httpProvider.interceptors.push('httpRequestInterceptor');
});

administratorBanke.controller('Opsti', function($window, $scope, $http, $compile, tokenService) {
	
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
		
		$http.get('/logoff').
		then(function mySucces(response) {
			
			
				toastr.success("IZLOGOVAN");
				$window.location.href=response.data.username;
		});
	};
});
