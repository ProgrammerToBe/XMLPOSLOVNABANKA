(function(){
var direktive = angular.module('customerDirectives');

	direktive.controller('friendsEstablishedController',['$window','$http','$scope','localStorageService','$route','$log', function($window,$http,$scope,localStorageService,$route,$log){
		
		var friendsScope = $scope;
		var user = $scope.$parent.user;
		$scope.friendsEstablished = {};
		$scope.friendsEstablished.hasResult = false;
		$scope.friendsEstablished.relations = [];
		$scope.friendsEstablished.notificationsCount = 0;

		$log.log('Established injected');
		$scope.friendsRoute.subdirectives.Established = $scope.friendsEstablished; // predamo se roditelju   / ali ansledjujemo njegovo obelezje

	    $scope.friendsEstablished.allMyFriends = function(refresh){

	    	var relationshipPattern = {};
	    	relationshipPattern.source = user;
	    	relationshipPattern.type = "Established";

	    	$http({
	    		method:'POST',
	    		url:'/relationships/getUserRelationships',
	    		data:relationshipPattern
	    	}).then(
	    		function successGetFriends(response){
	    			$scope.friendsEstablished.relations = response.data;
	    			$scope.friendsEstablished.sortRecently();

	    			for(idx in $scope.friendsEstablished.relations)
	    				$log.log("FULL: established: " + $scope.friendsEstablished.relations[idx].destination.username);

	    			if($scope.friendsEstablished.relations.length > 0 )
	    				$scope.friendsEstablished.hasResult = true;
	    			else
	    				$scope.friendsEstablished.hasResult = false;


	    			if(refresh){
	    				$log.log("friendsEstablished -> allMyFriends");
	    				//$log.log(" dodali smo ")
	    				$scope.$apply();
	    			}
	    		},function errorGetFriends(response){
	    			$scope.friendsEstablished.relations = []; 
	   				$scope.friendsEstablished.hasResult = false;
	    		}
	    			
	    	);

	    }; // pokrece se ova metoda nakon definicije!
	    $scope.friendsEstablished.allMyFriends(false);


	    $scope.friendsEstablished.getNotificationsCount = function(refresh){
	    	$http({
	    		method:'POST',
	    		url:"/relationships/getFriendsNotificationsCount/friends", 
	    		data:user
	    	}).then(
	    		function successCount(response){
	    			$scope.friendsEstablished.notificationsCount = response.data;
	    			if(refresh){
	    				$log.log("friendsEstablished -> getNotificationsCount");
	    				$scope.$apply();
	    			}
	    		}
	    	);
	    }

	    $scope.friendsEstablished.sortRecently = function(){
	    	var length = $scope.friendsEstablished.relations.length;
	    	var newCnt = 0,i;
	    	for(i=0;i<length;i++){
	    		var relation = $scope.friendsEstablished.relations[i];

	    		if(!relation.visited){
	    			$scope.friendsEstablished.relations.splice(i,1);
	    			$scope.friendsEstablished.relations.splice(newCnt,0,relation);
	    			newCnt++;
	    		} 
	    	}
	    	$scope.friendsEstablished.notificationsCount = newCnt;
	    }

	    $scope.friendsEstablished.newRelation = function(newRel,refresh){
	    	$scope.friendsEstablished.relations.splice(0,0,newRel);
	    	$scope.friendsEstablished.hasResult = true;
	    	// $scope.friendsEstablished.notificationsCount++;
	    	if(refresh){
	    				$log.log("friendsEstablished -> newRelation");
	    				//$log.log(" dodali smo ")
	    				$scope.$apply();
	    	}
	    };

	    $scope.friendsEstablished.viewRelations = function(){
	    	$scope.$parent.friends.notificationsCount = 0;

	    	var pattern = {};
	    	pattern.source = user;
	    	pattern.type = 'Established';

	    	$http({
	    		method:'POST',
	    		url:"/relationships/viewUserRelationships", 
	    		data:pattern
	    	}).then(
	    		function successView(response){
	    			$scope.friendsEstablished.notificationsCount = 0;
	    			$scope.$parent.friends.notificationsCount = 0;
	    		}
	    	);

	    }


	    $scope.friendsEstablished.breakUp = function(relation){
	    	// $window.alert($scope.$parent.user.username + " break with : " + friend.username );
	    	relation.type = "None";
	    	$http({
	    		method:'POST',
	    		url:"/relationships/setUserRelationships", 
	    		data:relation
	    	}).then(
	    		function successBreakUp(response){
	    			var index = $scope.friendsEstablished.relations.indexOf(relation);
					$scope.friendsEstablished.relations.splice(index, 1);

					if($scope.friendsEstablished.relations.length==0) // sakriti tabelu
						$scope.friendsEstablished.hasResult = false;
	    		}
	    	);

	    };

	    var stompFriendsEstablished = new SockJS('/stompFriendsEstablished');
		var topicFriendsEstablished = Stomp.over(stompFriendsEstablished);
		topicFriendsEstablished.connect({}, function(frame) {
			topicFriendsEstablished.subscribe("/topicFriendsEstablished/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				
				friendsScope.friendsEstablished.notificationsCount++;
				friendsScope.friendsEstablished.newRelation(notification,false);
				// friendsScope.friendsEstablished.allMyFriends(false);
				friendsScope.$apply();
			});
		});//connect

		var stompFriendsNone = new SockJS('/stompFriendsNone');
		var topicFriendsNone = Stomp.over(stompFriendsNone);
		topicFriendsNone.connect({}, function(frame) {
			topicFriendsNone.subscribe("/topicFriendsNone/"+user.username, function(data) {
				// friendsScope.friendsEstablished.getNotificationsCount(false);
				// friendsScope.friendsRequests.getNotificationsCount(false);

				
				friendsScope.friendsEstablished.allMyFriends(false);
				friendsScope.friendsRequests.allMyRequests(false);
				//friendsScope.$apply();
			});

		});//connect

	}]);

})(); // end

