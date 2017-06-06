angular.module("autentificationModule").controller("loginController",['$window','$scope','$http','localStorageService','RedirectionFactory','$log',function($window,$scope,$http,localStorageService, RedirectionFactory,$log){


	this.logging = function(){

		ctrl = this;

		/*cookie = localStorageService.get("user");
		$window.alert(cookie);*/

		$http({
			method:'POST',
			url: 'autentification/login',
			data: $scope.logger
		}).then(
			function successCallback(response) {
			    $scope.status = response.status;
          		$scope.data = response.data;

          		if(response.status<300){
          			if(response.data.role=="musterija" && response.data.verification!="OK" )
          				//!!!!!!!!!!!! ctrl.modal('Error', "You have to confirm you account by mail.");
          				toaster('warning','You have to confirm you account by mail.');
          			else if( ((response.data.role=="proizvodjac")||(response.data.role=="konobar")  ) && response.data.firstPassword==true){
          				//ctrl.modal('Error', "You have to change your default passsword");
          				localStorageService.set("first",JSON.stringify(response.data));
          				localStorageService.remove("user");
          				$window.location.href = 'login2.html#/firstChange';
          			}else{
          				//ctrl.modal('Succeed', "redirection");
          				localStorageService.set("user",JSON.stringify(response.data));
          				localStorageService.remove("first");

          				path = RedirectionFactory.getRedirectionPath(response.data.role);
          				if(path!='X')
          					$window.location.href = path;
          			}
          		}else{
          			//!!!!!!  ctrl.modal('Error', "There is no such user");
          			toaster('error','There is no such user');
          			localStorageService.remove("first");
          			localStorageService.remove("user");
          		}

			}, function errorCallback(response) {
			    //$window.alert( "status je: " + response.status + "data je: " + response.data + "objekat je: " + response);
			    //!!!!!!!!!!!!!!!!   ctrl.modal('Error',"There is no such user");
			    toaster('error','There is no such user');
		});
	};


	this.modal = function (title,data) {
    
	    $scope.modal = {};
	    $scope.modal.title = title;
	    $scope.modal.data = data;
	    $('#loginPanel #autentificationInfo').modal('show');
	};
	
}]);





angular.module("autentificationModule").controller('FirstChangeController',['$window','$http','$scope','localStorageService',function($window,$http,$scope,localStorageService){
	this.matching = function(){
		$scope.match = $scope.changer.password1 === $scope.changer.password2;
	}

	this.change = function(){
		//$window.alert($scope.register.username +": "+$scope.register.password+", "+$scope.register.mail );
		ctrl = this;

		cookie = localStorageService.get("first");
		obj = JSON.parse(cookie);
		obj.password = $scope.changer.password1;

		$http({
			method:'POST',
			url: 'autentification/firstChangeProducer',
			data: obj
		}).then(
			function successCallback(response) {
			    $scope.status = response.status;
          		$scope.data = response.data;

          		if( response.status < 300){
          			// ctrl.modal('Succeed!',"You changed your password");
          			localStorageService.set("user", JSON.stringify(response.data) );
          			localStorageService.remove("first");
          			$window.location.href = 'customer.html';
          		}else{
          			// ctrl.modal('Error', "Either is password alredy changed, either we can't find user with this username.");
          			localStorageService.remove("user");
          			localStorageService.remove("first");
          			$window.location.href = 'login2.html#/main';
          		}

          		


			}, function errorCallback(response) {
			    //$window.alert( "status je: " + response.status + "data je: " + response.data + "objekat je: " + response);
			    //!!!!!!!!!!!!!!!!!!  ctrl.modal('Error','Something went wrong.');
			    toaster('error','Something went wrong.');
		});

	}	



	this.modal = function (title,data) {
    
	    $scope.modal = {};
	    $scope.modal.title = title;
	    $scope.modal.data = data;
	    $('#firstChangePanel #autentificationInfo').modal('show');
	};


}]);