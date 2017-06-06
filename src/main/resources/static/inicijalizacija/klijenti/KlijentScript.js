administrator.controller('RukovanjeKlijentima', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.klijent = {};
	$scope.klijenti = {};
	$scope.svaNaseljenaMesta = {};
	
	$scope.$on('filterPoNaseljenomMestuKlijent', function (event, id) {
	    console.log(id); // Index naseljenog mesta
	    
	    $http.get('/nadjiKlijente/'+id).
        then(function(response) {
        	$scope.klijenti = response.data;
        	$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijenti[0].naseljenoMesto);
        });
	    
	  });
	
	$scope.idSelektovanogKlijenta = null;
	
	$scope.selektovanoNaseljenoMesto = {};
	
	$scope.init = function(){
		
		$http.get('/svaNaseljenaMesta').
        then(function(response) {
        	$scope.svaNaseljenaMesta = response.data;
        	
        });
		
		$http.get('/sviKlijenti').
        then(function(response) {
        	$scope.klijenti = response.data;
        	
        });
		
		
	};
	
	
	this.refresh = function(){
		
		$http.get('/sviKlijenti').
        then(function(response) {
        	$scope.klijenti = response.data;
        	
        });
	}
	
	this.addClick = function(){
		
		$scope.rezim =1;
		$scope.klijent = {};
		$scope.selektovanoNaseljenoMesto = {};
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.klijent = {};
		//if(!sakrijBrowse){
			$scope.selektovanoNaseljenoMesto = {};
		//}
	};
	
	this.prazanK = function(){
		if(angular.equals($scope.klijent, {})){
			return true;
		}else{
			return false;
		}
	}
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
			
			$scope.rezim = 1;
			
			if(angular.equals($scope.klijent, {})){
				
				return;
			}else if(angular.isUndefined($scope.klijent.jmbg)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(!angular.equals($scope.klijent.jmbg.trim().length, 13)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(angular.isUndefined($scope.klijent.ime)){
				toastr.error('Ime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.klijent.prezime)){
				toastr.error('Prezime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.klijent.adresa)){
				toastr.error('Adresa mora biti zadata!');
				return;
			}else if(angular.isUndefined($scope.klijent.telefon)){
				toastr.error('Telefon mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.klijent.email)){
				toastr.error('Email mora biti zadat!');
				return;
			}else if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				toastr.error('Naseljeno mesto mora biti zadato!');
				return;
			}
			
			$scope.klijent.naseljenoMesto = angular.copy($scope.selektovanoNaseljenoMesto);
			
			$http({
    		    method: 'POST',
    		    url: '/noviKlijent',
    		    data: $scope.klijent
    		}).
    		then(function mySucces(response) {
    				
    				if(response.data.id == -1){
    					toastr.error('Neuspesan unos!');
    					return;
    				}
    			
    				$scope.klijenti.push(response.data);
    				$scope.klijent = {};
    				$scope.selektovanoNaseljenoMesto = {};
    		});
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.klijent, {}) & !angular.equals($scope.idSelektovanogKlijenta, null)){
			
			$scope.rezim = 0;
			

			if(angular.equals($scope.klijent, {})){
				
				return;
			}else if(angular.isUndefined($scope.klijent.jmbg)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(!angular.equals($scope.klijent.jmbg.trim().length, 13)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(angular.isUndefined($scope.klijent.ime)){
				toastr.error('Ime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.klijent.prezime)){
				toastr.error('Prezime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.klijent.adresa)){
				toastr.error('Adresa mora biti zadata!');
				return;
			}else if(angular.isUndefined($scope.klijent.telefon)){
				toastr.error('Telefon mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.klijent.email)){
				toastr.error('Email mora biti zadat!');
				return;
			}else if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				toastr.error('Naseljeno mesto mora biti zadato!');
				return;
			}
			
			$scope.klijent.naseljenoMesto = angular.copy($scope.selektovanoNaseljenoMesto);
			
			$http({
    		    method: 'POST',
    		    url: '/azurirajKlijenta',
    		    data: $scope.klijent
    		}).
    		then(function mySucces(response) {
    			
	    			if(response.data.id == -1){
						toastr.error('Neuspesan unos!');
						return;
					}
    			
    				var temp = -1;
	    			for (var i = 0; i < $scope.klijenti.length; i++) { 
					    if(angular.equals($scope.klijenti[i].id, response.data.id)){
					    	temp = i;
					    	break;
					    }
					}
					
    				if(!angular.equals(temp, -1)){
    					$scope.klijenti[i] = response.data;
    				}
    				
    				$scope.klijent = {};
    				$scope.selektovanoNaseljenoMesto = {};
    		});
			
		}else if(angular.equals($scope.rezim, 2)){ //& !angular.equals($scope.klijent, {})){

			var filter = {};
			
			if(angular.isUndefined($scope.klijent.jmbg)){
				filter.jmbg ="";
			}else{
				filter.jmbg = $scope.klijent.jmbg;
			}
			
			if(angular.isUndefined($scope.klijent.ime)){
				filter.ime ="";
			}else{
				filter.ime = $scope.klijent.ime;
			}
			
			if(angular.isUndefined($scope.klijent.prezime)){
				filter.prezime ="";
			}else{
				filter.prezime = $scope.klijent.prezime;
			}
			
			if(angular.isUndefined($scope.klijent.adresa)){
				filter.adresa ="";
			}else{
				filter.adresa = $scope.klijent.adresa;
			}
			
			if(angular.isUndefined($scope.klijent.telefon)){
				filter.telefon ="";
			}else{
				filter.telefon = $scope.klijent.telefon;
			}
			
			if(angular.isUndefined($scope.klijent.email)){
				filter.email ="";
			}else{
				filter.email = $scope.klijent.email;
			}
			
			if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				filter.mesto = 0;
			}else{
				filter.mesto = $scope.selektovanoNaseljenoMesto.id;
			}

			$http({
    		    method: 'POST',
    		    url: '/filtrirajKlijenteZaNaseljenoMesto',
    		    data: filter
    		}).
	        then(function(response) {
	        	
	        	$scope.klijenti = response.data;
	        	
	        });
			return;
		}
		
		
	};
	
	
	
	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.klijent = {};
			$scope.selektovanoNaseljenoMesto = {};
		}else{
			$scope.rezim = 0;
			
			$scope.klijent = {};
			$scope.selektovanoNaseljenoMesto = {};
		}
	};
	
	
	
	this.deleteClick= function(){
		
		if(angular.equals($scope.klijent, {})){
			
			return;
		}
		
		$http.delete('/obrisiKlijenta/'+$scope.klijent.id).
        then(function(response) {
        	
        	if(response.data.id == -1){
				toastr.error('Neuspesan unos!');
				return;
			}
        	
        	var temp = -1;
    		for (var i = 0; i < $scope.klijenti.length; i++) { 
    		    if(angular.equals($scope.klijenti[i].id, $scope.klijent.id)){
    		    	temp = i;
    		    	break;
    		    }
    		}
    		
    		if(!angular.equals(temp, -1)){
    			$scope.klijenti.splice(temp, 1);
    		}
    		
    		$scope.klijent = {};
    		$scope.selektovanoNaseljenoMesto = {};
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
		
		$scope.klijent = angular.copy($scope.klijenti[0]);
		$scope.idSelektovanogKlijenta = $scope.klijenti[0].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijenti[0].naseljenoMesto);
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.klijenti.length; i++) { 
		    if(angular.equals($scope.klijenti[i].id, $scope.klijent.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.klijent = angular.copy($scope.klijenti[temp-1]);
		$scope.idSelektovanogKlijenta = $scope.klijenti[temp-1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijenti[temp-1].naseljenoMesto);
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.klijenti.length; i++) { 
		    if(angular.equals($scope.klijenti[i].id, $scope.klijent.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.klijenti.length-1 & temp!=-1){
			return;
		}
		
		$scope.klijent = angular.copy($scope.klijenti[temp+1]);
		$scope.idSelektovanogKlijenta = $scope.klijenti[temp+1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijenti[temp+1].naseljenoMesto);
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.klijent = angular.copy($scope.klijenti[$scope.klijenti.length-1]);
		$scope.idSelektovanogKlijenta = $scope.klijenti[$scope.klijenti.length-1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijenti[$scope.klijenti.length-1].naseljenoMesto);
	};
	
	this.aktivirajRacun = function(klijent){
		if(confirm("Da li ste sigurni da zelite da otvorite racun?")){
			$http({
			    method: 'POST',
			    url: '/noviRacun',
			    data: klijent
			}).
			then(function mySucces(response) {
				toastr.success("Racun uspesno otvoren!");
				$scope.$parent.$parent.opsti.addRacun();
			});
		}
	}
	
	this.setSelected = function(nm){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanogKlijenta = nm.id;
			$scope.klijent = angular.copy(nm);
			$scope.selektovanoNaseljenoMesto = angular.copy(nm.naseljenoMesto);
		}
	};
	
	this.setSelectedPlace = function(nm){
		//if(angular.equals($scope.rezim, 0) || angular.equals($scope.rezim, 1)){
			$scope.selektovanoNaseljenoMesto = angular.copy(nm);
		//}
	};
	
	
	this.conf = function(){
		if(angular.equals($scope.rezim, 0)){
					
			if(angular.equals($scope.klijent, {})){
				
				$scope.selektovanoNaseljenoMesto = {};
			}
			
			
			
		}else if(angular.equals($scope.rezim, 1)){
			
			
			
		}else if(angular.equals($scope.rezim, 2)){
			//$scope.selektovanoNaseljenoMesto = {};
		}
	}
	
	this.dismis = function(){
		 
		if(angular.equals($scope.rezim, 0)){
			
			if(!angular.equals($scope.klijent, {})){
				
				$scope.selektovanoNaseljenoMesto = angular.copy($scope.klijent.naseljenoMesto);
			}
			
		}
		
		$scope.selektovanoNaseljenoMesto = {};
		
	}
	
	this.nextFormClick = function(){
			
			$scope.$parent.$parent.opsti.tabClick7(7, $scope.klijent);
			
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