(function(){
var module = angular.module("customerHomeModule",['ngRoute','LocalStorageModule','cookieModul']);
	
	module.controller('HomeController',['$http','$log',function($http,$log){

		$log.log('HomeController');

	}]); // end

}]);


})(); // end, end

