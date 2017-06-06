administrator.controller('RukovanjeDnevnimStanjima', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	
	$scope.stanjeSearch = {};
	$scope.stanja = {};
	
	$scope.idSelektovanogStanja = null;
	
	$scope.$on('novaStanja', function (event) {
		$http.get('/svaDnevnaStanja').
        then(function(response) {
        	$scope.stanja = response.data;
        	
        	$scope.idSelektovanogStanja = null;
        });
        
	  });
	
	
	
	$scope.$on('filterDnevnihStanja', function (event, obj) {
		
		if(obj == null){
			$http.get('/svaDnevnaStanja').
	        then(function(response) {
	        	$scope.stanja = response.data;
	        	
	        	$scope.idSelektovanogStanja = null;
	        });
			
			return;
		}
		
	     $http.get('/filtrirajStanjaPoRacunu/'+obj).
        then(function(response) {
        	$scope.stanjeSearch = {};
			$scope.stanja = response.data;
        	$scope.rezim = 0;
        	$scope.idSelektovanogStanja = null;
        });
	    
	   
        
	  });
	
	
	
	$scope.init = function(){
		
		$http.get('/svaDnevnaStanja').
        then(function(response) {
        	$scope.stanja = response.data;
        	
    		
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
	
		
		$http.get('/svaDnevnaStanja').
        then(function(response) {
        	$scope.stanja = response.data;
        	
        });
	}
	
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.stanjeSearch = {};
	};

	this.commitClickSearch = function(){
		
		$http({
		    method: 'POST',
		    url: '/filtrirajStanja',
		    data: $scope.stanjeSearch
		}).
		then(function mySucces(response) {
			$scope.stanjeSearch = {};
			
			$scope.stanja = response.data;
		});
	}
	
	this.rollbackClick = function(){
		
			$scope.rezim = 0;
			
			$scope.idSelektovanogStanja = {};
		
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
		
		
		$scope.idSelektovanogStanja = $scope.stanja[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.stanja.length; i++) { 
		    if(angular.equals($scope.stanja[i].id, $scope.idSelektovanogStanja)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.stanje = angular.copy($scope.stanja[temp-1]);
		$scope.idSelektovanogStanja = $scope.stanja[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.stanja.length; i++) { 
		    if(angular.equals($scope.stanja[i].id, $scope.idSelektovanogStanja)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.stanja.length-1 & temp!=-1){
			return;
		}
		
		
		$scope.idSelektovanogStanja = $scope.stanja[temp+1].id;
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		$scope.idSelektovanogStanja = $scope.stanja[$scope.stanja.length-1].id;
	};
	
	this.setSelected = function(r){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanogStanja = r.id;
			
		}
	};
	
	this.nextFormClick = function(){
		
		
		$scope.$parent.$parent.opsti.tabClick11(11, $scope.idSelektovanogStanja);
		
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