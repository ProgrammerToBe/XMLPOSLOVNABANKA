administratorBanke.controller('RukovanjePermisijama', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.permisija = {};
	$scope.permisije = {};

	
	$scope.idSelektovanePermisije = null;
	
	$scope.init = function(){
		
		$http.get('/allPermissions').
        then(function(response) {
        	$scope.permisije = response.data;
        	$scope.$parent.$parent.permisijeIzPermisija = $scope.permisije;
        });
		
	};
	
	this.refresh = function(){
		
		$http.get('/allPermissions').
        then(function(response) {
        	$scope.permisije = response.data;
        	
        });
		
	}
	
	this.addClick = function(){
		
		$scope.rezim =1;
		$scope.permisija = {};
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.permisija = {};
	};
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
			
			$scope.rezim = 1;
			
			if(angular.equals($scope.permisija, {})){
				
				return;
			}else if(angular.isUndefined($scope.permisija.name)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			
			$http({
    		    method: 'POST',
    		    url: '/newPermission',
    		    data: $scope.permisija
    		}).
    		then(function mySucces(response) {
    				
    				if(response.data.id == -1){
    					toastr.error('Neuspesan unos!');
    					return;
    				}
    			
    				if(!angular.isArray($scope.permisije)){
    					$scope.permisije = [];
    				}
    				
    				$scope.permisije.push(response.data);
    				$scope.$parent.$parent.permisijeIzPermisija = $scope.permisije;
    				$scope.permisija = {};
    		});
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.permisija, {}) & !angular.equals($scope.idSelektovanePermisije, null)){
			
			$scope.rezim = 0;
			

			if(angular.equals($scope.permisija, {})){
				
				return;
			}else if(angular.isUndefined($scope.permisija.name)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			
			
			$http({
    		    method: 'POST',
    		    url: '/updatePermission',
    		    data: $scope.permisija
    		}).
    		then(function mySucces(response) {
    			
	    			if(response.data.id == -1){
						toastr.error('Neuspesan unos!');
						return;
					}
    			
    				var temp = -1;
	    			for (var i = 0; i < $scope.permisije.length; i++) { 
					    if(angular.equals($scope.permisije[i].id, response.data.id)){
					    	temp = i;
					    	break;
					    }
					}
					
    				if(!angular.equals(temp, -1)){
    					$scope.permisije[i] = response.data;
    					$scope.$parent.$parent.permisijeIzPermisija = $scope.permisije;
    				}
    				
    				$scope.permisija = {};
    		});
			
		}
		
		
	};

	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.permisija = {};
		}else{
			$scope.rezim = 0;
			
			$scope.permisija = {};
		}
	};
	
	
	
	this.deleteClick= function(){
		
		if(angular.equals($scope.permisija, {})){
			
			return;
		}
		
		$http.delete('/deletePermission/'+$scope.permisija.id).
        then(function(response) {
        	
        	if(response.data.id == -1){
				toastr.error('Neuspesno brisanje! Moguce je da je odabrana stavka povezana sa drugim stavkama te ju je nemoguce obrisati.');
				return;
			}
        	
        	var temp = -1;
    		for (var i = 0; i < $scope.permisije.length; i++) { 
    		    if(angular.equals($scope.permisije[i].id, $scope.permisija.id)){
    		    	temp = i;
    		    	break;
    		    }
    		}
    		
    		if(!angular.equals(temp, -1)){
    			$scope.permisije.splice(temp, 1);
    		}
    		
    		$scope.permisija = {};
    		$scope.$parent.$parent.permisijeIzPermisija = $scope.permisije;
        });
		
		
	}
	
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
		
		$scope.permisija = angular.copy($scope.permisije[0]);
		$scope.idSelektovanePermisije = $scope.permisije[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.permisije.length; i++) { 
		    if(angular.equals($scope.permisije[i].id, $scope.permisija.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.permisija = angular.copy($scope.permisije[temp-1]);
		$scope.idSelektovanePermisije = $scope.permisije[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.permisije.length; i++) { 
		    if(angular.equals($scope.permisije[i].id, $scope.permisija.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.permisije.length-1 & temp!=-1){
			return;
		}
		
		$scope.permisija = angular.copy($scope.permisije[temp+1]);
		$scope.idSelektovanePermisije = $scope.permisije[temp+1].id;
		
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.permisija = angular.copy($scope.permisije[$scope.permisije.length-1]);
		$scope.idSelektovanePermisije = $scope.permisije[$scope.permisije.length-1].id;
	};
	
	
	this.setSelected = function(d){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanePermisije = d.id;
			$scope.permisija = angular.copy(d);
			
		}
	};
	
	
});

administratorBanke.directive('ngConfirmClick', [
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
}])

administratorBanke.filter('stringRezima', function() {
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