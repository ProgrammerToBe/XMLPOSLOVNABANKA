var administrator = angular.module('administrator', ['ngRoute','LocalStorageModule','cookieModul', 'ngCookies']);


administrator.controller('RukovanjeDrzavama', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.drzava = {};
	$scope.drzave = {};

	
	$scope.idSelektovaneDrzave = null;
	
	$scope.init = function(){
		
		$http.get('/sveDrzave', {headers: {'X-XSRF-TOKEN': $scope.$parent.$parent.token}}).
        then(function(response) {
        	$scope.drzave = response.data;
        	$scope.$parent.$parent.drzaveIzDrzava = $scope.drzave;
        });
		
	};
	
	this.praznaDrzava = function(){
		if(angular.equals($scope.drzava, {})){
			return true;
		}else{
			return false;
		}
	}
	
	this.addClick = function(){
		
		$scope.rezim =1;
		$scope.drzava = {};
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.drzava = {};
	};
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
			
			$scope.rezim = 1;
			
			if(angular.equals($scope.drzava, {})){
				
				return;
			}else if(angular.isUndefined($scope.drzava.oznaka)){
				toastr.error('Oznaka mora da ima tacno 3 karaktera!');
				return;
			}else if(!angular.equals($scope.drzava.oznaka.trim().length, 3)){
				toastr.error('Oznaka mora da ima tacno 3 karaktera!');
				return;
			}else if(angular.isUndefined($scope.drzava.naziv)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			
			$http({
    		    method: 'POST',
    		    url: '/novaDrzava',
    		    data: $scope.drzava
    		}).
    		then(function mySucces(response) {
    				
    				if(response.data.id == -1){
    					toastr.error(response.data.naziv);
    					return;
    				}
    			
    				if(!angular.isArray($scope.drzave)){
    					$scope.drzave = [];
    				}
    				
    				$scope.drzave.push(response.data);
    				$scope.$parent.$parent.drzaveIzDrzava = $scope.drzave;
    				$scope.drzava = {};
    		});
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.drzava, {}) & !angular.equals($scope.idSelektovaneDrzave, null)){
			
			$scope.rezim = 0;
			

			if(angular.equals($scope.drzava, {})){
				
				return;
			}else if(angular.isUndefined($scope.drzava.oznaka)){
				toastr.error('Oznaka mora da ima tacno 3 karaktera!');
				return;
			}else if(!angular.equals($scope.drzava.oznaka.trim().length, 3)){
				toastr.error('Oznaka mora da ima tacno 3 karaktera!');
				return;
			}else if(angular.isUndefined($scope.drzava.naziv)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			
			
			$http({
    		    method: 'POST',
    		    url: '/azurirajDrzavu',
    		    data: $scope.drzava
    		}).
    		then(function mySucces(response) {
    			
	    			if(response.data.id == -1){
						toastr.error(response.data.naziv);
						return;
					}
    			
    				var temp = -1;
	    			for (var i = 0; i < $scope.drzave.length; i++) { 
					    if(angular.equals($scope.drzave[i].id, response.data.id)){
					    	temp = i;
					    	break;
					    }
					}
					
    				if(!angular.equals(temp, -1)){
    					$scope.drzave[i] = response.data;
    					$scope.$parent.$parent.drzaveIzDrzava = $scope.drzave;
    				}
    				
    				$scope.drzava = {};
    		});
			
		}else if(angular.equals($scope.rezim, 2) & !angular.equals($scope.drzava, {})){
			
			var filter = {};
			
			if(angular.isUndefined($scope.drzava.oznaka)){
				filter.oznaka = "";
			}else{
				filter.oznaka = $scope.drzava.oznaka;
			}
			
				
			if(angular.isUndefined($scope.drzava.naziv)){
				filter.naziv = "";
			}else{
				filter.naziv = $scope.drzava.naziv;
			}
			
			
			$http({
    		    method: 'POST',
    		    url: '/filtrirajDrzave',
    		    data: filter
    		}).
	        then(function(response) {
	        	
	        	$scope.drzave = response.data;
	        	
	        });
			
			
		}
		
		
	};

	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.drzava = {};
		}else{
			$scope.rezim = 0;
			
			$scope.drzava = {};
		}
	};
	
	this.refreshClick = function(){
		$http.get('/sveDrzave').
        then(function(response) {
        	$scope.drzave = response.data;
        	$scope.$parent.$parent.drzaveIzDrzava = $scope.drzave;
        });
	}
	
	this.deleteClick= function(){
		
		if(angular.equals($scope.drzava, {})){
			
			return;
		}
		
		$http.delete('/obrisiDrzavu/'+$scope.drzava.id).
        then(function(response) {
        	
        	if(response.data.id == -1){
        		toastr.error('Neuspesno brisanje! Moguce je da je odabrana stavka povezana sa drugim stavkama te ju je nemoguce obrisati.');
				return;
			}
        	
        	var temp = -1;
    		for (var i = 0; i < $scope.drzave.length; i++) { 
    		    if(angular.equals($scope.drzave[i].id, $scope.drzava.id)){
    		    	temp = i;
    		    	break;
    		    }
    		}
    		
    		if(!angular.equals(temp, -1)){
    			$scope.drzave.splice(temp, 1);
    			$scope.$parent.$parent.drzaveIzDrzava = $scope.drzave;
    		}
    		
    		$scope.drzava = {};
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
		
		$scope.drzava = angular.copy($scope.drzave[0]);
		$scope.idSelektovaneDrzave = $scope.drzave[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.drzave.length; i++) { 
		    if(angular.equals($scope.drzave[i].id, $scope.drzava.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.drzava = angular.copy($scope.drzave[temp-1]);
		$scope.idSelektovaneDrzave = $scope.drzave[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.drzave.length; i++) { 
		    if(angular.equals($scope.drzave[i].id, $scope.drzava.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.drzave.length-1 & temp!=-1){
			return;
		}
		
		$scope.drzava = angular.copy($scope.drzave[temp+1]);
		$scope.idSelektovaneDrzave = $scope.drzave[temp+1].id;
		
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.drzava = angular.copy($scope.drzave[$scope.drzave.length-1]);
		$scope.idSelektovaneDrzave = $scope.drzave[$scope.drzave.length-1].id;
	};
	
	
	this.setSelected = function(d){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovaneDrzave = d.id;
			$scope.drzava = angular.copy(d);
			
		}
	};
	
	
	this.nextFormClick = function(){
		
		$scope.$parent.$parent.opsti.tabClick2(2, $scope.drzava);
		
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
}])

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