(function(){
var module = angular.module("customerReservationsModule",['ngRoute','LocalStorageModule','cookieModul']);
	module.controller('ReservationsController',['$http','$log',function($http,$log){

		$log.log('ReservationsController');



	}]); // end

})(); // end, end
