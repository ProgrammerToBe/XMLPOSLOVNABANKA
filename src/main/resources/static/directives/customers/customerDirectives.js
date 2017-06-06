(function(){
var direktive = angular.module('customerDirectives',['modalsModule']);

	direktive.directive('searchFriends',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/friendsSearch.html',
			controller : 'searchFriendsController',
			controllerAs: 'searchFriendsCtrl'

		};
	});


	direktive.directive('friendsEstablished',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/friendsEstablished.html',
			controller : 'friendsEstablishedController',
			controllerAs: 'friendsEstablishedCtrl'

		};
	});

	direktive.directive('friendsRequests',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/friendsRequests.html',
			controller : 'friendsRequestsController',
			controllerAs: 'friendsRequestsCtrl'

		};
	});
	
	
	direktive.directive('reservationsCreate',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/reservationsCreate.html',
			controller : 'reservationsCreateController',
			controllerAs: 'reservationsCreateCtrl'

		};
	});
	
	direktive.directive('reservationsActual',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/reservationsActual.html',
			controller : 'reservationsActualController',
			controllerAs: 'reservationsActualCtrl'

		};
	});

})(); // end, end