(function(){
var app = angular.module("cookieModul",['ngRoute','LocalStorageModule']);

	
	app.controller('CheckCookie',['$window','$http','$scope','localStorageService','$route','RedirectionFactory',function($window,$http,$scope, localStorageService,$route,RedirectionFactory){
		var rolePage = $route.current.$$route.rolePage;
	    RedirectionFactory.setCurrentPage(rolePage);

	    automatizedRedirectioning($window,$http,$scope, localStorageService,RedirectionFactory);


	}]);


	automatizedRedirectioning = function($window,$http,$scope, localStorageService,RedirectionFactory){
			    cookie = localStorageService.get('user');
			    var path = 'X';
			    if( (cookie==undefined || cookie == null) && RedirectionFactory.getCurrentPage()!='none'){
			    	path = RedirectionFactory.getRedirectionPath('none');
			    	if(!path.includes('X'))
				    	$window.location.href=path;
			    }

			    objekat = JSON.parse(cookie);
			    $http({
						method:'POST',
						url: RedirectionFactory.getPrefix()+'autentification/login',
						data: objekat
					}).then(
						function successCallback(response) {
						    $scope.status = response.status;
			          		$scope.data = response.data;

			          		if(response.status<300){
			          			if(response.data.role=="musterija" && response.data.verification!="OK" ){
			          				localStorageService.remove("first");
			          				localStorageService.remove("user");
			          				// $window.location.href='login2.html#/main';
			          				path = RedirectionFactory.getRedirectionPath('none');
							    	if(!path.includes('X'))
								    	$window.location.href=path;

			          			}else if( ((response.data.role=="proizvodjac")||(response.data.role=="konobar")  ) && response.data.firstPassword==true){
			          				localStorageService.set("first",JSON.stringify(response.data));
			          				localStorageService.remove("user");
			          				$window.location.href = RedirectionFactory.getPrefix()+ 'login2.html#/firstChange';
			          			}else{
			          				localStorageService.set("user",JSON.stringify(response.data));
			          				localStorageService.remove("first");

			          				path = RedirectionFactory.getRedirectionPath(response.data.role);
			          				if(!path.includes('X'))
			          					$window.location.href = path;
			          			}
			          		}else{
			          			localStorageService.remove("first");
			          			localStorageService.remove("user");
			          			// $window.location.href=RedirectionFactory.getRedirectionPath('none');
			          			path = RedirectionFactory.getRedirectionPath('none');
						    	if(!path.includes('X'))
							    	$window.location.href=path;
			          		}

						}, function errorCallback(response) {
						    localStorageService.remove("first");
			          		localStorageService.remove("user");
			          		// $window.location.href=RedirectionFactory.getRedirectionPath('none');
			          		path = RedirectionFactory.getRedirectionPath('none');
					    	if(!path.includes('X'))
						    	$window.location.href=path;
					});
	}
	

	logout = function($window,$scope, localStorageService){
		localStorageService.clearAll();
		$window.location.href='login2.html#/main';
	}
	
	logout2 = function($window,$scope, localStorageService, RedirectionFactory){
		localStorageService.clearAll();
		$window.location.href=RedirectionFactory.getPrefix()+'login2.html#/main';
	}


	app.factory('RedirectionFactory',['$log', function($log){
		var paths=[];
		paths['none'] = "login2.html#/main";
		paths['musterija'] = "customer.html"
		paths['administrator'] = "adminUtils/AdministratorPage.html";
		paths['dostavljac'] ="supplierUtils/supplierPage.html";
		paths['konobar'] = "konobarUtils/KonobarPage.html";
		paths['proizvodjac'] = "KuvarUtils/KuvarPage.html";
		paths['menadzer'] = "menagerUtils/menagerPage.html";
		//paths['ponudz']
		

		var factory = {};
		factory.prefix ="";
		factory.setCurrentPage = function(role){
			factory.role = role;
		};
		factory.getCurrentPage = function(role){
			return factory.role;
		};
		factory.setPrefix = function(prefix){
			factory.prefix = prefix;
		};
		factory.getPrefix = function(){
			return factory.prefix;
		};
		factory.getRedirectionPath = function(role){

			$log.log(factory);
			$log.log(factory.role);
			$log.log(role);

			if(factory.role == role)
				return "X";
			else
				return factory.prefix+paths[role];
		};

		return factory;
	}]);



})();