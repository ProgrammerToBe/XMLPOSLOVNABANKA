administrator.controller('RukovanjeZatvaranjima', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.zatvaranje = {};
	$scope.zatvaranjeSearch = {};
	$scope.zatvaranja = {};
	
	$scope.sakrijBrowse = false;
	
	$scope.zaZatvaranje = {};
	$scope.nulaNaStanju = false;
	$scope.brojRacunaZaPrenos = "";
	
	$scope.$on('novoZatvaranje', function (event) {
		$http.get('/svaZatvaranja').
        then(function(response) {
        	$scope.zatvaranja = response.data;
        	
    		
        });
        
	  });
	
	$scope.$on('filterZatvaranja', function (event, obj) {
		
		if(obj == null){
			$http.get('/svaZatvaranja').
	        then(function(response) {
	        	$scope.zatvaranja = response.data;
	        	
	    		
	        });
			return;
		}
		
	     $http.get('/filtrirajZatvaranjaPoRacunu/'+obj).
        then(function(response) {
        	$scope.racunSearch = {};
			$scope.zatvaranja = response.data;
        	$scope.sakrijBrowse = true;
			$scope.rezim = 0;
        });
	    
	   
        
	  });
	
	$scope.idSelektovanogZatvaranja = null;
	
	$scope.init = function(){
		
		$http.get('/svaZatvaranja').
        then(function(response) {
        	$scope.zatvaranja = response.data;
        	
    		
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
		
		$scope.sakrijBrowse = false;
		
		$http.get('/svaZatvaranja').
        then(function(response) {
        	$scope.zatvaranja = response.data;
        	
        });
	}
	
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.zatvaranjeSearch = {};
	};

	this.commitClickSearch = function(){
		
		$http({
		    method: 'POST',
		    url: '/filtrirajZatvaranja',
		    data: $scope.zatvaranjeSearch
		}).
		then(function mySucces(response) {
			$scope.racunSearch = {};
			//$scope.rezim = 0;
			
			$scope.zatvaranja = response.data;
		});
	}
	
	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.zatvaranje = {};
		}else{
			$scope.rezim = 0;
			
			$scope.zatvaranje = {};
		}
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
		
		$scope.zatvaranje = angular.copy($scope.zatvaranja[0]);
		$scope.idSelektovanogZatvaranja = $scope.zatvaranja[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.zatvaranja.length; i++) { 
		    if(angular.equals($scope.zatvaranja[i].id, $scope.zatvaranje.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.zatvaranje = angular.copy($scope.zatvaranja[temp-1]);
		$scope.idSelektovanogZatvaranja = $scope.zatvaranja[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.zatvaranja.length; i++) { 
		    if(angular.equals($scope.zatvaranja[i].id, $scope.zatvaranje.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.zatvaranja.length-1 & temp!=-1){
			return;
		}
		
		$scope.zatvaranje = angular.copy($scope.zatvaranja[temp+1]);
		$scope.idSelektovanogZatvaranja = $scope.zatvaranja[temp+1].id;
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.zatvaranje = angular.copy($scope.zatvaranja[$scope.zatvaranja.length-1]);
		$scope.idSelektovanogZatvaranja = $scope.zatvaranja[$scope.zatvaranja.length-1].id;
	};
	
	this.setSelected = function(r){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanogZatvaranja = r.id;
			$scope.zatvaranje = angular.copy(r);
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