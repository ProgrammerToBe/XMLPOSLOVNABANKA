(function(){
var module = angular.module("customerFriendsModule",['ngRoute','LocalStorageModule','cookieModul']);
	
	module.controller('FriendsController',['$http','$log',function($http,$log){

		$log.log('FriendsController');



	}]); // end

})(); // end, end