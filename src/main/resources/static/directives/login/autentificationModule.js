(function(){
var app = angular.module("autentificationModule",['ngRoute','LocalStorageModule','cookieModul','modalsModule']);

app.directive('loginForm',function(){
	return{
		restrict:'E',
		templateUrl:'directives/login/template/mainLogin.html'
	};
});

app.directive('registrationForm',function(){
	return{
		restrict:'E',
		templateUrl:'directives/login/template/mainRegistration.html'
	};
});



app.directive('autentificationModal',function(){
	return{
		restrict:'E',
		templateUrl:'directives/modals/registerModal.html',
		controller: function(){
			$('#registerInfo').modal({ show: false});

		},
		controllerAs: 'registerModal'
	};
});

})();