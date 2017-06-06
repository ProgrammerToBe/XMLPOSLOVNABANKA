administrator.controller('RukovanjePravnimLicima', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.pravnoLice = {};
	$scope.pravnaLica = {};
	$scope.svaNaseljenaMesta = {};
	$scope.sveRadneDelatnosti = {};

	
	$scope.$on('filterPoNaseljenomMestuPravnoLice', function (event, id) {
	    console.log(id); // Index naseljenog mesta
	    
	    $http.get('/nadjiPravnaLica/'+id).
        then(function(response) {
        	$scope.pravnaLica = response.data;
        	$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnaLica[0].naseljenoMesto);
        	$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnaLica[0].activity);
        });
	    
	  });
	
	$scope.idSelektovanogPravnogLica = null;
	
	$scope.selektovanoNaseljenoMesto = {};
	
	$scope.selektovanaRadnaDelatnost = {};
	
	$scope.init = function(){
		
		$http.get('/svaNaseljenaMesta').
        then(function(response) {
        	$scope.svaNaseljenaMesta = response.data;
        	
        });
		
		$http.get('/svaPravnaLica').
        then(function(response) {
        	$scope.pravnaLica = response.data;
        	
        });
		
		$http.get('/activities').
        then(function(response) {
        	$scope.sveRadneDelatnosti = response.data;
        	
        });
	};
	
	
	this.refresh = function(){
		
		$http.get('/svaPravnaLica').
        then(function(response) {
        	$scope.pravnaLica = response.data;
        	
        });
	}
	
	this.addClick = function(){
		
		$scope.rezim =1;
		$scope.pravnoLice = {};
		$scope.selektovanoNaseljenoMesto = {};
		$scope.selektovanaRadnaDelatnost = {};
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.pravnoLice = {};
		//if(!sakrijBrowse){
			$scope.selektovanoNaseljenoMesto = {};
			$scope.selektovanaRadnaDelatnost = {};
		//}
	};
	
	this.praznoPL = function(){
		if(angular.equals($scope.pravnoLice, {})){
			return true;
		}else{
			return false;
		}
	}
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
			
			$scope.rezim = 1;
			
			if(angular.equals($scope.pravnoLice, {})){
				
				return;
			}else if(angular.isUndefined($scope.pravnoLice.jmbg)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(!angular.equals($scope.pravnoLice.jmbg.trim().length, 13)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.ime)){
				toastr.error('Ime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.prezime)){
				toastr.error('Prezime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.adresa)){
				toastr.error('Adresa mora biti zadata!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.telefon)){
				toastr.error('Telefon mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.email)){
				toastr.error('Email mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.pib)){
				toastr.error('Pib mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.fax)){
				toastr.error('Fax mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.odobrio)){
				toastr.error('Polje "Odobrio" mora biti zadato!');
				return;
			}else if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				toastr.error('Naseljeno mesto mora biti zadato!');
				return;
			}else if(angular.equals($scope.selektovanaRadnaDelatnost, {})){
				toastr.error('Radna delatnost mora biti zadata!');
				return;
			}
			
			$scope.pravnoLice.naseljenoMesto = angular.copy($scope.selektovanoNaseljenoMesto);
			$scope.pravnoLice.activity = angular.copy($scope.selektovanaRadnaDelatnost);
			
			$http({
    		    method: 'POST',
    		    url: '/novoPravnoLice',
    		    data: $scope.pravnoLice
    		}).
    		then(function mySucces(response) {
    				
    				if(response.data.id == -1){
    					toastr.error('Neuspesan unos!');
    					return;
    				}
    			
    				$scope.pravnaLica.push(response.data);
    				$scope.pravnoLice = {};
    				$scope.selektovanoNaseljenoMesto = {};
    				$scope.selektovanaRadnaDelatnost = {};
    		});
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.pravnoLice, {}) & !angular.equals($scope.idSelektovanogPravnogLica, null)){
			
			$scope.rezim = 0;
			

			if(angular.equals($scope.pravnoLice, {})){
				
				return;
			}else if(angular.isUndefined($scope.pravnoLice.jmbg)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(!angular.equals($scope.pravnoLice.jmbg.trim().length, 13)){
				toastr.error('Jmbg mora da ima tacno 13 karaktera!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.ime)){
				toastr.error('Ime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.prezime)){
				toastr.error('Prezime mora biti zadato!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.adresa)){
				toastr.error('Adresa mora biti zadata!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.telefon)){
				toastr.error('Telefon mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.email)){
				toastr.error('Email mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.pib)){
				toastr.error('Pib mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.fax)){
				toastr.error('Fax mora biti zadat!');
				return;
			}else if(angular.isUndefined($scope.pravnoLice.odobrio)){
				toastr.error('Polje "Odobrio" mora biti zadato!');
				return;
			}else if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				toastr.error('Naseljeno mesto mora biti zadato!');
				return;
			}else if(angular.equals($scope.selektovanaRadnaDelatnost, {})){
				toastr.error('Radna delatnost mora biti zadata!');
				return;
			}
			
			$scope.pravnoLice.naseljenoMesto = angular.copy($scope.selektovanoNaseljenoMesto);
			$scope.pravnoLice.activity = angular.copy($scope.selektovanaRadnaDelatnost);
			
			$http({
    		    method: 'POST',
    		    url: '/azurirajPravnoLice',
    		    data: $scope.pravnoLice
    		}).
    		then(function mySucces(response) {
    			
	    			if(response.data.id == -1){
						toastr.error('Neuspesan unos!');
						return;
					}
    			
    				var temp = -1;
	    			for (var i = 0; i < $scope.pravnaLica.length; i++) { 
					    if(angular.equals($scope.pravnaLica[i].id, response.data.id)){
					    	temp = i;
					    	break;
					    }
					}
					
    				if(!angular.equals(temp, -1)){
    					$scope.pravnaLica[i] = response.data;
    				}
    				
    				$scope.pravnoLice = {};
    				$scope.selektovanoNaseljenoMesto = {};
    				$scope.selektovanaRadnaDelatnost = {};
    		});
			
		}else if(angular.equals($scope.rezim, 2)){
			var filter = {};
			
			if(angular.isUndefined($scope.pravnoLice.jmbg)){
				filter.jmbg ="";
			}else{
				filter.jmbg = $scope.pravnoLice.jmbg;
			}
			
			if(angular.isUndefined($scope.pravnoLice.ime)){
				filter.ime ="";
			}else{
				filter.ime = $scope.pravnoLice.ime;
			}
			
			if(angular.isUndefined($scope.pravnoLice.prezime)){
				filter.prezime ="";
			}else{
				filter.prezime = $scope.pravnoLice.prezime;
			}
			
			if(angular.isUndefined($scope.pravnoLice.adresa)){
				filter.adresa ="";
			}else{
				filter.adresa = $scope.pravnoLice.adresa;
			}
			
			if(angular.isUndefined($scope.pravnoLice.telefon)){
				filter.telefon ="";
			}else{
				filter.telefon = $scope.pravnoLice.telefon;
			}
			
			if(angular.isUndefined($scope.pravnoLice.email)){
				filter.email ="";
			}else{
				filter.email = $scope.pravnoLice.email;
			}
			
			if(angular.isUndefined($scope.pravnoLice.pib)){
				filter.pib ="";
			}else{
				filter.pib = $scope.pravnoLice.pib;
			}
			
			if(angular.isUndefined($scope.pravnoLice.fax)){
				filter.fax ="";
			}else{
				filter.fax = $scope.pravnoLice.fax;
			}
			
			if(angular.isUndefined($scope.pravnoLice.odobrio)){
				filter.odobrio ="";
			}else{
				filter.odobrio = $scope.pravnoLice.odobrio;
			}
			
			if(angular.equals($scope.selektovanoNaseljenoMesto, {})){
				filter.mesto = 0;
			}else{
				filter.mesto = $scope.selektovanoNaseljenoMesto.id;
			}
			
			if(angular.equals($scope.selektovanaRadnaDelatnost, {})){
				filter.delatnost = 0;
			}else{
				filter.delatnost = $scope.selektovanaRadnaDelatnost.id;
			}

			$http({
    		    method: 'POST',
    		    url: '/filtrirajPravnaLicaZaNaseljenoMesto',
    		    data: filter
    		}).
	        then(function(response) {
	        	
	        	$scope.pravnaLica = response.data;
	        	
	        });
			return;
		}
		
		
	};
	
	
	
	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.pravnoLice = {};
			$scope.selektovanoNaseljenoMesto = {};
			$scope.selektovanaRadnaDelatnost = {};
		}else{
			$scope.rezim = 0;
			
			$scope.pravnoLice = {};
			$scope.selektovanoNaseljenoMesto = {};
			$scope.selektovanaRadnaDelatnost = {};
		}
	};
	
	
	
	this.deleteClick= function(){
		
		if(angular.equals($scope.pravnoLice, {})){
			
			return;
		}
		
		$http.delete('/obrisiPravnoLice/'+$scope.pravnoLice.id).
        then(function(response) {
        	
        	if(response.data.id == -1){
				toastr.error('Neuspesan unos!');
				return;
			}
        	
        	var temp = -1;
    		for (var i = 0; i < $scope.pravnaLica.length; i++) { 
    		    if(angular.equals($scope.pravnaLica[i].id, $scope.pravnoLice.id)){
    		    	temp = i;
    		    	break;
    		    }
    		}
    		
    		if(!angular.equals(temp, -1)){
    			$scope.pravnaLica.splice(temp, 1);
    		}
    		
    		$scope.pravnoLice = {};
    		$scope.selektovanoNaseljenoMesto = {};
    		$scope.selektovanaRadnaDelatnost = {};
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
		
		$scope.pravnoLice = angular.copy($scope.pravnaLica[0]);
		$scope.idSelektovanogPravnogLica = $scope.pravnaLica[0].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnaLica[0].naseljenoMesto);
		$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnaLica[0].activity);
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.pravnaLica.length; i++) { 
		    if(angular.equals($scope.pravnaLica[i].id, $scope.pravnoLice.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.pravnoLice = angular.copy($scope.pravnaLica[temp-1]);
		$scope.idSelektovanogPravnogLica = $scope.pravnaLica[temp-1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnaLica[temp-1].naseljenoMesto);
		$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnaLica[temp-1].activity);
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.pravnaLica.length; i++) { 
		    if(angular.equals($scope.pravnaLica[i].id, $scope.pravnoLice.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.pravnaLica.length-1 & temp!=-1){
			return;
		}
		
		$scope.pravnoLice = angular.copy($scope.pravnaLica[temp+1]);
		$scope.idSelektovanogPravnogLica = $scope.pravnaLica[temp+1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnaLica[temp+1].naseljenoMesto);
		$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnaLica[temp+1].activity);
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.pravnoLice = angular.copy($scope.pravnaLica[$scope.pravnaLica.length-1]);
		$scope.idSelektovanogPravnogLica = $scope.pravnaLica[$scope.pravnaLica.length-1].id;
		$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnaLica[$scope.pravnaLica.length-1].naseljenoMesto);
		$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnaLica[$scope.pravnaLica.length-1].activity);
	};
	
	this.aktivirajRacun = function(pravnoLice){
		if(confirm("Da li ste sigurni da zelite da otvorite racun?")){
			var klijent = {};
			klijent.id = pravnoLice.id;
			klijent.jmbg = pravnoLice.jmbg;
			klijent.ime = pravnoLice.ime;
			klijent.prezime = pravnoLice.prezime;
			klijent.adresa = pravnoLice.adresa;
			klijent.telefon = pravnoLice.telefon;
			klijent.email = pravnoLice.email;
			klijent.naseljenoMesto = pravnoLice.naseljenoMesto;
			
			$http({
			    method: 'POST',
			    url: '/noviRacun',
			    data: klijent
			}).
			then(function mySucces(response) {
				toastr.success("Racun uspesno aktiviran!");
				$scope.$parent.$parent.opsti.addRacun();
			});
		}
	}
	
	this.setSelected = function(nm){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanogPravnogLica = nm.id;
			$scope.pravnoLice = angular.copy(nm);
			$scope.selektovanoNaseljenoMesto = angular.copy(nm.naseljenoMesto);
			$scope.selektovanaRadnaDelatnost = angular.copy(nm.activity);
		}
	};
	
	this.setSelectedPlace = function(nm){
		//if(angular.equals($scope.rezim, 0) || angular.equals($scope.rezim, 1)){
			
			$scope.selektovanoNaseljenoMesto = angular.copy(nm);
		//}
	};
	
	this.setSelectedActivity = function(rd){
		//if(angular.equals($scope.rezim, 0) || angular.equals($scope.rezim, 1)){
			
			$scope.selektovanaRadnaDelatnost = angular.copy(rd);
		//}
	};
	
	this.conf = function(){
		if(angular.equals($scope.rezim, 0)){
					
			if(angular.equals($scope.pravnoLice, {})){
				
				$scope.selektovanoNaseljenoMesto = {};
			}
			
			
			
		}else if(angular.equals($scope.rezim, 1)){
			
			
			
		}else if(angular.equals($scope.rezim, 2)){
			//$scope.selektovanoNaseljenoMesto = {};
		}
	}
	
	this.dismis = function(){
		 
		if(angular.equals($scope.rezim, 0)){
			
			if(!angular.equals($scope.pravnoLice, {})){
				
				$scope.selektovanoNaseljenoMesto = angular.copy($scope.pravnoLice.naseljenoMesto);
			}
			
		}
		
		$scope.selektovanoNaseljenoMesto = {};
		
	}
	
	this.confActivity = function(){
		if(angular.equals($scope.rezim, 0)){
					
			if(angular.equals($scope.pravnoLice, {})){
				
				$scope.selektovanaRadnaDelatnost = {};
			}
			
			
			
		}else if(angular.equals($scope.rezim, 1)){
			
			
			
		}else if(angular.equals($scope.rezim, 2)){
			//$scope.selektovanoNaseljenoMesto = {};
		}
	}
	
	this.dismisActivity = function(){
		 
		if(angular.equals($scope.rezim, 0)){
			
			if(!angular.equals($scope.pravnoLice, {})){
				
				$scope.selektovanaRadnaDelatnost = angular.copy($scope.pravnoLice.activity);
			}
			
		}
		
		$scope.selektovanaRadnaDelatnost = {};
		
	}
	
	this.nextFormClick = function(){
		
		$scope.$parent.$parent.opsti.tabClick7(7, $scope.pravnoLice);
		
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