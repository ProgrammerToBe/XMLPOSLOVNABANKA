//var proba = angular.module('proba', ['ngSanitize']);

var proba = angular.module('proba', []);

proba.controller('ProbaCtrl', function($scope, $http, $compile){//, $sanitize) {

	$scope.model1 = "";
	$scope.model2 = "";
	
	this.metoda1 = function(){
		$http.get('/special/napraviToken').
	    then(function(response) {
	    	
	    });
	
	}
	
	this.metoda2 = function(){
		$http.get('/special/testToken').
	    then(function(response) {
	    	
	    });
	
	}
	
	
	this.funkcija = function(){
		//$sanitize($scope.model1);
		$scope.model2 = $scope.model1;
	}
	
	this.getRedirect = function(){
		$http.get('/dajRedirekt').
	    then(function(response) {
	    	var k = response.headers();
	    });
	}
	
	this.vidiDaLiPuca = function(){
		
		$http.get('/vidiDaLiPuca').
	    then(function(response) {
	    	var k = response.data;
	    });
	}
	
});