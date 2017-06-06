(function(){
var module = angular.module("customerSettingsModule",['ngRoute','LocalStorageModule','cookieModul']);
	module.controller('SettingsController',['$http','$log',function($http,$log){

		$log.log('SettingsController');



	}]); // end

})(); // end, end
