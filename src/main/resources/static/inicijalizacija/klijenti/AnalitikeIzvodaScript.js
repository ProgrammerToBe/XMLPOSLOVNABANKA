administrator.controller('RukovanjeAnalitikamaIzvoda', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	
	$scope.analitikaSearch = {};
	$scope.analitike = {};
	
	$scope.idSelektovaneAnalitike = null;
	
	
	$scope.$on('filterAnalitika', function (event, obj) {
		
		if(obj == null){
			$http.get('/sveAnalitike').
	        then(function(response) {
	        	$scope.analitike = response.data;
	        	
	    		
	        });
			
			return;
		}
		
		
	     $http.get('/filtrirajAnalitikePoStanju/'+obj).
        then(function(response) {
        	$scope.analitikaSearch = {};
			$scope.analitike = response.data;
        	$scope.rezim = 0;
        	$scope.idSelektovaneAnalitike = null;
        });
	    
	   
        
	  });
	
	
	
	$scope.init = function(){
		
		$http.get('/sveAnalitike').
        then(function(response) {
        	$scope.analitike = response.data;
        	
    		
        });
		
		
	};
	
	
	
	
	this.checkRezim = function(){
		if($scope.rezim === 0){
			return true;
		}else{
			return false;
		}
	}
	
	this.refresh = function(){
	
		
		$http.get('/sveAnalitike').
        then(function(response) {
        	$scope.analitike = response.data;
        	
        });
	}
	
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.analitikaSearch = {};
	};

	this.commitClickSearch = function(){
		
		$http({
		    method: 'POST',
		    url: '/filtrirajAnalitike',
		    data: $scope.analitikaSearch
		}).
		then(function mySucces(response) {
			$scope.analitikaSearch = {};
			
			$scope.analitike = response.data;
		});
	}
	
	this.rollbackClick = function(){
		
			$scope.rezim = 0;
			
			$scope.idSelektovaneAnalitike = {};
		
	};
	
	
	
	$scope.nultoStanje = function(){
		if(angular.equals($scope.rezim, 0)){
			return true;
		}else{
			return false;
		}
	}
	
	this.firstClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		
		$scope.idSelektovaneAnalitike = $scope.analitike[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.analitike.length; i++) { 
		    if(angular.equals($scope.analitike[i].id, $scope.idSelektovaneAnalitike)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.stanje = angular.copy($scope.analitike[temp-1]);
		$scope.idSelektovaneAnalitike = $scope.analitike[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.analitike.length; i++) { 
		    if(angular.equals($scope.analitike[i].id, $scope.idSelektovaneAnalitike)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.analitike.length-1 & temp!=-1){
			return;
		}
		
		
		$scope.idSelektovaneAnalitike = $scope.analitike[temp+1].id;
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		$scope.idSelektovaneAnalitike = $scope.analitike[$scope.analitike.length-1].id;
	};
	
	this.setSelected = function(r){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovaneAnalitike = r.id;
			
		}
	};
	
	
	
	
});

administrator.directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Da li ste sigurni?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if ( window.confirm(msg) ) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
}]);

administrator.filter('stringRezima', function() {
    return function(input) {
    	
    	if(angular.equals(input, 0)){
    		return "pregled/izmenu.";
    	}
    	else if(angular.equals(input, 1)){
    		return "dodavanje.";
    	}
    	else{
    		return "pretragu.";
    	}
       
    }
});