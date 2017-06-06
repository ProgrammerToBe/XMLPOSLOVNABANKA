(function(){
var direktive = angular.module('customerDirectives');

	direktive.controller('searchFriendsController',['$window','$http','$scope','localStorageService','$route','$log','RedirectionFactory', function($window,$http,$scope,localStorageService,$route,$log,RedirectionFactory){
		
		var user = $scope.$parent.user;
		$scope.friendsSearch = {};
		$scope.friendsSearch.hasResult = false;
		$scope.friendsSearch.searchKey = "";
		$scope.friendsSearch.relations = [];

		$log.log('Request injected');
		$scope.friendsRoute.subdirectives.Search = $scope.friendsSearch; // predamo se roditelju  / ali ansledjujemo njegovo obelezje


	    $scope.friendsSearch.allMyPossibleFriends = function(){

	    	$http({
	    		method:'POST',
	    		url:'/relationships/searchForFriends/'+$scope.friendsSearch.searchKey,
	    		data:user
	    	}).then(
	    		function successGetFriends(response){
	    			$scope.friendsSearch.relations = response.data;

	    			for(idx in $scope.friendsSearch.relations)
	    				$log.log("FULL: possible: " + $scope.friendsSearch.relations[idx].destination.username);

	    			if($scope.friendsSearch.relations.length > 0 )
	    				$scope.friendsSearch.hasResult = true;
	    			else
	    				$scope.friendsSearch.hasResult = false;

	    		},function errorGetFriends(response){
	    			$scope.friendsSearch.relations = []; 
	   				$scope.friendsSearch.hasResult = false;
	    		}
	    			
	    	);

	    }; // pokrece se ova metoda nakon definicije!

	    $scope.friendsSearch.newRelation = function(newRel,refresh){
	    	$scope.friendsSearch.relations.splice(0,0,newRel);
	    	if(refresh)
	    		$scope.$apply();
	    };


	    $scope.friendsSearch.sendRequest = function(relation){
	    	
	    	relation.type = "Request";

	    	$http({
	    		method:'POST',
	    		url:"/relationships/setUserRelationships", 
	    		data:relation
	    	}).then(
	    		function successRequestSending(response){
	    			var index = $scope.friendsSearch.relations.indexOf(relation);
					$scope.friendsSearch.relations.splice(index, 1);

					if($scope.friendsSearch.relations.length==0) // sakriti tabelu
						$scope.friendsSearch.hasResult = false;
	    		}
	    	);

	    };

	}]); // end controller

})(); // end module

