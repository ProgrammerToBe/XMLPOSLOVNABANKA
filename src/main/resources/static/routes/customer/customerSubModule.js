(function(){
var direktive2 = angular.module('customerDirectives2',[]);

	direktive2.directive('searchFriends2',function(){
		return{
			restrict: 'EA',
			templateUrl:'directives/customers/template/friendsSearch.html'

		};
	});

})(); // end, end