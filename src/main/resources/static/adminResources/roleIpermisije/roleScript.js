administratorBanke.controller('RukovanjeRolama', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.rola = {};
	$scope.role = {};
	$scope.svePermisije = {};

	
	$scope.sakrijBrowse = false;
	
	$scope.idPermisije = -1;
	
	$scope.idSelektovaneRole = null;
	
	$scope.selektovanaPermisija = {};
	
	$scope.permisijeOdabraneRole = [];
	
	$scope.init = function(){
		
		$http.get('/allPermissions').
        then(function(response) {
        	$scope.svePermisije = response.data;
        	
        });
		
		$http.get('/allRoles').
        then(function(response) {
        	$scope.role = response.data;
        	
        });
		
		
	};
	
	
	this.refresh = function(){
		
		$scope.sakrijBrowse = false;
		
		$http.get('/allRoles').
        then(function(response) {
        	$scope.role = response.data;
        	
        });
		
		$http.get('/svePermisije').
        then(function(response) {
        	$scope.svePermisije = response.data;
        	
        });
	}
	
	this.addClick = function(){
		$scope.sakrijBrowse = false;
		$scope.rezim =1;
		$scope.rola = {};
		$scope.selektovanaPermisija = {};
		$scope.permisijeOdabraneRole = [];
		$scope.idPermisije = -1;
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.rola = {};
		if(!$scope.sakrijBrowse){
			$scope.selektovanaPermisija = {};
		}
	};
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
			
			$scope.rezim = 1;
			
			if(angular.equals($scope.rola, {})){
				
				return;
			}else if(angular.isUndefined($scope.rola.name)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			$scope.rola.permissions = angular.copy($scope.permisijeOdabraneRole);
			
			$http({
    		    method: 'POST',
    		    url: '/newRole',
    		    data: $scope.rola
    		}).
    		then(function mySucces(response) {
    				
    				if(response.data.id == -1){
    					toastr.error('Neuspesan unos!');
    					return;
    				}
    			
    				if(!angular.isArray($scope.role)){
    					$scope.role = [];
    				}
    				
    				$scope.role.push(response.data);
    				$scope.rola = {};
    				$scope.selektovanaPermisija = {};
    				$scope.permisijeOdabraneRole = [];
    				$scope.idPermisije = -1;
    		});
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.rola, {}) & !angular.equals($scope.idSelektovaneRole, null)){
			
			$scope.rezim = 0;
			

			if(angular.equals($scope.rola, {})){
				
				return;
			}else if(angular.isUndefined($scope.rola.name)){
				toastr.error('Naziv mora biti zadat!');
				return;
			}
			
			$scope.rola.permissions = angular.copy($scope.permisijeOdabraneRole);
			
			$http({
    		    method: 'POST',
    		    url: '/updateRole',
    		    data: $scope.rola
    		}).
    		then(function mySucces(response) {
    			
	    			if(response.data.id == -1){
						toastr.error('Neuspesan unos!');
						return;
					}
    			
    				var temp = -1;
	    			for (var i = 0; i < $scope.role.length; i++) { 
					    if(angular.equals($scope.role[i].id, response.data.id)){
					    	temp = i;
					    	break;
					    }
					}
					
    				if(!angular.equals(temp, -1)){
    					$scope.role[i] = response.data;
    				}
    				
    				$scope.rola = {};
    				$scope.selektovanaPermisija = {};
    				$scope.permisijeOdabraneRole = [];
    				$scope.idPermisije = -1;
    		});
			
		}
	};
	
	
	
	this.rollbackClick = function(){
		$scope.sakrijBrowse = false;
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.rola = {};
			$scope.selektovanaPermisija = {};
			$scope.permisijeOdabraneRole = [];
			$scope.idPermisije = -1;
		}else{
			$scope.rezim = 0;
			
			$scope.rola = {};
			$scope.selektovanaPermisija = {};
			$scope.permisijeOdabraneRole = [];
			$scope.idPermisije = -1;
		}
	};
	
	this.removePermissionClick = function(){
		if(angular.equals($scope.rola, {}) || angular.equals($scope.idPermisije, -1)){
			
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.permisijeOdabraneRole.length; i++) { 
		    if(angular.equals($scope.permisijeOdabraneRole[i].id, $scope.idPermisije)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(!angular.equals(temp, -1)){
			$scope.permisijeOdabraneRole.splice(temp, 1);
		}
		
		
	};
	
	this.deleteClick= function(){
		
		if(angular.equals($scope.rola, {})){
			
			return;
		}
		
		$http.delete('/deleteRole/'+$scope.rola.id).
        then(function(response) {
        	
        	if(response.data.id == -1){
				toastr.error('Neuspesan unos!');
				return;
			}
        	
        	var temp = -1;
    		for (var i = 0; i < $scope.role.length; i++) { 
    		    if(angular.equals($scope.role[i].id, $scope.rola.id)){
    		    	temp = i;
    		    	break;
    		    }
    		}
    		
    		if(!angular.equals(temp, -1)){
    			$scope.role.splice(temp, 1);
    		}
    		
    		$scope.rola = {};
    		$scope.selektovanaPermisija = {};
    		$scope.permisijeOdabraneRole = [];
    		$scope.idPermisije = -1;
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
		
		$scope.rola = angular.copy($scope.role[0]);
		$scope.idSelektovaneRole = $scope.role[0].id;
		//$scope.selektovanaPermisija = angular.copy($scope.role[0].drzava);
		$scope.permisijeOdabraneRole = angular.copy($scope.rola.permissions);
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.role.length; i++) { 
		    if(angular.equals($scope.role[i].id, $scope.rola.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.rola = angular.copy($scope.role[temp-1]);
		$scope.idSelektovaneRole = $scope.role[temp-1].id;
		//$scope.selektovanaPermisija = angular.copy($scope.role[temp-1].drzava);
		$scope.permisijeOdabraneRole = angular.copy($scope.rola.permissions);
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.role.length; i++) { 
		    if(angular.equals($scope.role[i].id, $scope.rola.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.role.length-1 & temp!=-1){
			return;
		}
		
		$scope.rola = angular.copy($scope.role[temp+1]);
		$scope.idSelektovaneRole = $scope.role[temp+1].id;
		//$scope.selektovanaPermisija = angular.copy($scope.role[temp+1].drzava);
		$scope.permisijeOdabraneRole = angular.copy($scope.rola.permissions);
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.rola = angular.copy($scope.role[$scope.role.length-1]);
		$scope.idSelektovaneRole = $scope.role[$scope.role.length-1].id;
		//$scope.selektovanaPermisija = angular.copy($scope.role[$scope.role.length-1].drzava);
		$scope.permisijeOdabraneRole = angular.copy($scope.rola.permissions);
	};
	
	
	this.setSelected = function(d){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovaneRole = d.id;
			$scope.rola = angular.copy(d);
			//$scope.selektovanaPermisija = angular.copy(d.drzava);
			$scope.permisijeOdabraneRole = angular.copy($scope.rola.permissions);
		}
	};
	
	this.setSelectedPermission = function(d){
		//if(angular.equals($scope.rezim, 0) || angular.equals($scope.rezim, 1)){
			
			$scope.selektovanaPermisija = angular.copy(d);
		//}
	};
	
	this.setujSelektovanuPermisiju = function(d){
		$scope.idPermisije = d.id;
	};
	
	
	this.browse = function(){
		
		$scope.svePermisije = $scope.$parent.$parent.permisijeIzPermisija;
		
	};
	
	this.conf = function(){

		if(angular.isUndefined($scope.rola.permissions)){
			$scope.rola.permissions = [];
			$scope.permisijeOdabraneRole.push($scope.selektovanaPermisija);
			$scope.selektovanaPermisija = {};
			return;
			
		}
		
		var temp = -1;
		
		
		for (var i = 0; i < $scope.rola.permissions.length; i++) { 
		    if(angular.equals($scope.rola.permissions[i].id, $scope.selektovanaPermisija.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp==-1){
			$scope.permisijeOdabraneRole.push($scope.selektovanaPermisija);
		}
		
	}
	
	this.dismis = function(){
		 

		
		$scope.selektovanaPermisija = {};
		
	}
	
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