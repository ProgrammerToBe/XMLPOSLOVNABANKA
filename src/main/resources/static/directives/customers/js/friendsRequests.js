(function(){
var direktive = angular.module('customerDirectives');

	direktive.controller('friendsRequestsController',['$window','$http','$scope','localStorageService','$route','$log','RedirectionFactory', function($window,$http,$scope,localStorageService,$route,$log,RedirectionFactory){
		
		var friendsScope = $scope;

		var user = $scope.$parent.user;
		$scope.friendsRequests = {};
		$scope.friendsRequests.hasResult = false;
		$scope.friendsRequests.relations = [];
		$scope.friendsRequests.notificationsCount = 0;

		$log.log('Request injected');
		$scope.friendsRoute.subdirectives.Request = $scope.friendsRequests; // predamo se roditelju  / ali ansledjujemo njegovo obelezje


	    $scope.friendsRequests.allMyRequests = function(refresh){

	    	var relationshipPattern = {};
	    	relationshipPattern.source = user;
	    	relationshipPattern.type = "Request";



	    	$http({
	    		method:'POST',
	    		url:'/relationships/getUserRelationships',
	    		data:relationshipPattern
	    	}).then(
	    		function successGetFriends(response){
	    			$scope.friendsRequests.relations = response.data;
	    			$scope.friendsRequests.sortRecently();

	    			for(idx in $scope.friendsRequests.relations)
	    				$log.log("FULL: request: " + $scope.friendsRequests.relations[idx].destination.username);

	    			if($scope.friendsRequests.relations.length > 0 )
	    				$scope.friendsRequests.hasResult = true;
	    			else
	    				$scope.friendsRequests.hasResult = false;

	    			if(refresh){
	    				$log.log("friendsRequests -> allMyRequests");
	    				$scope.$apply();
	    			}

	    		},function errorGetFriends(response){
	    			$scope.friendsRequests.relations = []; 
	   				$scope.friendsRequests.hasResult = false;
	    		}
	    			
	    	);

	    }; // pokrece se ova metoda nakon definicije!
	    $scope.friendsRequests.allMyRequests(false);


	    $scope.friendsRequests.sortRecently = function(){
	    	var length = $scope.friendsRequests.relations.length;
	    	var newCnt = 0,i;
	    	for(i=0;i<length;i++){
	    		var relation = $scope.friendsRequests.relations[i];

	    		if(!relation.visited){
	    			$scope.friendsRequests.relations.splice(i,1);
	    			$scope.friendsRequests.relations.splice(newCnt,0,relation);
	    			newCnt++;
	    		} 
	    	}
	    	$scope.friendsRequests.notificationsCount = newCnt;
	    }

	    $scope.friendsRequests.newRelation = function(newRel,refresh){
	    	$scope.friendsRequests.relations.splice(0,0,newRel);
	    	$scope.friendsRequests.hasResult = true;
	    	// $scope.friendsRequests.notificationsCount++;
	    	if(refresh){
	    		$log.log("friendsRequests -> newRelation" );
	    		$scope.$apply();
	    	}
	    };

	    $scope.friendsRequests.getNotificationsCount = function(){
	    	$http({
	    		method:'POST',
	    		url:"/relationships/getFriendsNotificationsCount/requests", 
	    		data:user
	    	}).then(
	    		function successAnswering(response){
	    			$scope.friendsRequests.notificationsCount = response.data;
	    			if(refresh){
	    				$log.log("friendsRequests -> getNotificationsCount");
	    				$scope.$apply();
	    			}
	    		}
	    	);
	    }


	    $scope.friendsRequests.viewRelations = function(){
			$scope.$parent.friends.notificationsCount = 0;

	    	var pattern = {};
	    	pattern.source = user;
	    	pattern.type = 'Request';

	    	$http({
	    		method:'POST',
	    		url:"/relationships/viewUserRelationships", 
	    		data:pattern
	    	}).then(
	    		function successView(response){
	    			$scope.friendsRequests.notificationsCount = 0;
	    			$scope.$parent.friends.notificationsCount = 0;
	    		}
	    	);

	    }


	    $scope.friendsRequests.answerRequest = function(relation,answer){
	    	// $window.alert($scope.$parent.user.username + " break with : " + friend.username );
	    	if(answer)
		    	relation.type = "Established";
		    else
		    	relation.type = "None";

		    relation.visited = false;

	    	$http({
	    		method:'POST',
	    		url:"/relationships/setUserRelationships", 
	    		data:relation
	    	}).then(
	    		function successAnswering(response){
	    			var index = $scope.friendsRequests.relations.indexOf(relation);
					$scope.friendsRequests.relations.splice(index, 1);

					if($scope.friendsRequests.relations.length==0) // sakriti tabelu
						$scope.friendsRequests.hasResult = false;

					if(answer){ // ako je prihvacen zahtev, dodajemo ga medju prijatelje
						relation.visited = false;
						$scope.friendsRoute.subdirectives.Established.newRelation(relation,false); // ne treba refresh
					}
	    		}
	    	);

	    };

	    var stompFriendsEstablished = new SockJS('/stompFriendsRequest');
		var topicFriendsEstablished = Stomp.over(stompFriendsEstablished);
		topicFriendsEstablished.connect({}, function(frame) {
			topicFriendsEstablished.subscribe("/topicFriendsRequest/"+user.username, function(data) {
				var notification = JSON.parse(data.body);
				
				friendsScope.friendsRequests.notificationsCount++;
				friendsScope.friendsRequests.newRelation(notification,false);
				// friendsScope.friendsRequests.allMyRequests(false);
				friendsScope.$apply();
			});
		});//connect
		
	}]);

})(); // end

