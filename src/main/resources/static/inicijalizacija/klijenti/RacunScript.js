administrator.controller('RukovanjeRacunima', function($scope, $http, $compile){
	
	$scope.rezim = 0;
	//0 za pregled, 1 za dodavanje, 2 za pretragu
	
	$scope.racun = {};
	$scope.racuni = {};
	
	$scope.odabir = "Zatvaranja";
	
	$scope.sakrijBrowse = false;
	
	$scope.zaZatvaranje = {};
	$scope.nulaNaStanju = false;
	$scope.brojRacunaZaPrenos = "";
	
	
	$scope.$on('filterPoKlijentuRacun', function (event, id) {
	    console.log(id); // Index naseljenog mesta
	    
	    $http.get('/nadjiRacune/'+id).
        then(function(response) {
        	$scope.racuni = response.data;
        	$scope.sakrijBrowse = true;
        });
	    
	  });
	
	$scope.$on('dodavanjeNovogRacuna', function(event){
		$http.get('/sviRacuni').
        then(function(response) {
        	$scope.racuni = response.data;
        	
    		for (var i = 0; i < response.data.length; i++) { 
    			$scope.racuni[i].datumOtvaranja = new Date(response.data[i].datumOtvaranja);
    			if(new Date(response.data[i].datumOtvaranja) < new Date(response.data[i].datumDeaktivacije)){
    				$scope.racuni[i].datumDeaktivacije = new Date(response.data[i].datumDeaktivacije);
    			}
    		}
        });
	});
	
	$scope.idSelektovanogRacuna = null;
	
	$scope.init = function(){
		
		$http.get('/sviRacuni').
        then(function(response) {
        	$scope.racuni = response.data;
        	
    		for (var i = 0; i < response.data.length; i++) { 
    			$scope.racuni[i].datumOtvaranja = new Date(response.data[i].datumOtvaranja);
    			if(new Date(response.data[i].datumOtvaranja) < new Date(response.data[i].datumDeaktivacije)){
    				$scope.racuni[i].datumDeaktivacije = new Date(response.data[i].datumDeaktivacije);
    			}
    		}
        });
		
		
	};
	
	this.getStatusRacuna = function(status){
		if(status){
			return 'Aktivan';
		}else{
			return 'Zatvoren';
		}
	};
	
	this.prazanR = function(){
		if(angular.equals($scope.racun, {})){
			return true;
		}else{
			return false;
		}
	}
	
	this.checkStatus = function(racun){
		if(racun.status){
			return true;
		}else{
			return false;
		}
	};
	
		this.nadjiRacun = function(rac){
		
			$scope.zaZatvaranje = rac;
	
		$http.get('/nadjiRacun/'+rac.id).
        then(function(response) {
        	
        	if(angular.equals(response.data.id, -1)){
        		return;
        	}
        	
        	if(angular.equals(response.data.stanje, 0)){
        		$scope.nulaNaStanju = true;
        		$scope.zaZatvaranje = response.data;
        	}
        	
        });
		
	};
	
	this.closeClick = function(){
		
		if(!$scope.nulaNaStanju){
			if(angular.equals($scope.brojRacunaZaPrenos, "")){
				toastr.error('Stanje na racunu nije 0!Broj racuna za prenos sredstava mora biti unesen!');
				return;
			}
		}
		
		var objekat = {};
		objekat.id = $scope.zaZatvaranje.id;
		objekat.racun = $scope.brojRacunaZaPrenos;
		
		$http({
		    method: 'POST',
		    url: '/zatvoriRacun',
		    data: objekat
		}).
		then(function mySucces(response) {
			
			if(angular.equals(response.data.id, -1)){
				toastr.error(response.data.brojRacuna);
				return;
			}else{
				toastr.success('Uspesno zatvaranje racuna!');
			}
			
			
			var temp = -1;
			for (var i = 0; i < $scope.racuni.length; i++) { 
			    if(angular.equals($scope.racuni[i].id, response.data.id)){
			    	temp = i;
			    	break;
			    }
			}
			

    		if(!angular.equals(temp, -1)){
    			$scope.racuni[temp] = response.data;
    			
    			
    		}
			
			$scope.zaZatvaranje = {};
			$scope.nulaNaRacunu = false;
			$scope.brojRacunaZaPrenos = "";
			$scope.$parent.$parent.opsti.novoZatvaranje();

			
		});
		
	};
	
	this.dismisClick = function(){
		$scope.zaZatvaranje = {};
		$scope.nulaNaRacunu = false;
		
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
		
		$http.get('/sviRacuni').
        then(function(response) {
        	$scope.racuni = response.data;
        	
        });
	}
	
	this.addClick = function(){
		
	};
	
	this.searchClick = function(){
		$scope.rezim =2;
		$scope.racun = {};
	};
	
	this.commitClick = function(){
		
	
		if(angular.equals($scope.rezim, 1)){
      
		}else if(angular.equals($scope.rezim, 0) & !angular.equals($scope.klijent, {}) & !angular.equals($scope.idSelektovanogKlijenta, null)){
			
		}else if(angular.equals($scope.rezim, 2)){
			
			$http.get('/filtrirajRacune/'+$scope.racun.brojRacuna+'/'+$scope.racun.status+'/'+$scope.racun.datumOtvaranja+'/'+$scope.racun.datumDeaktivacije+'/'+$scope.racun.klijent.ime+'/'+$scope.racun.klijent.prezime).
	        then(function(response) {
	        	
	        	$scope.racuni = response.data;
	        	
	        });
			return;
		}
		
		
	};
	
	this.commitClickSearch = function(){
		
		$http({
		    method: 'POST',
		    url: '/filtrirajRacune',
		    data: $scope.racunSearch
		}).
		then(function mySucces(response) {
			$scope.racunSearch = {};
			$scope.rezim = 0;
			
			$scope.racuni = response.data;
		});
	}
	
	this.rollbackClick = function(){
		if(angular.equals($scope.rezim, 1) || angular.equals($scope.rezim, 2)){
			$scope.rezim = 0;
			$scope.racun = {};
		}else{
			$scope.rezim = 0;
			
			$scope.racun = {};
		}
		
		$scope.idSelektovanogRacuna = null;
	};
	
	
	
	this.deleteClick= function(){
	
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
		
		$scope.racun = angular.copy($scope.racuni[0]);
		$scope.idSelektovanogRacuna = $scope.racuni[0].id;
	};

	this.prevClick = function(){
		
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.racuni.length; i++) { 
		    if(angular.equals($scope.racuni[i].id, $scope.racun.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == 0 & temp!=-1){
			return;
		}
		
		$scope.racun = angular.copy($scope.racuni[temp-1]);
		$scope.idSelektovanogRacuna = $scope.racuni[temp-1].id;
	};
	

	this.nextClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		
		var temp = -1;
		for (var i = 0; i < $scope.racuni.length; i++) { 
		    if(angular.equals($scope.racuni[i].id, $scope.racun.id)){
		    	temp = i;
		    	break;
		    }
		}
		
		if(temp == $scope.racuni.length-1 & temp!=-1){
			return;
		}
		
		$scope.racun = angular.copy($scope.racuni[temp+1]);
		$scope.idSelektovanogRacuna = $scope.racuni[temp+1].id;
	};
	

	this.lastClick = function(){
		if(!$scope.nultoStanje()){
			return;
		}
		$scope.racun = angular.copy($scope.racuni[$scope.racuni.length-1]);
		$scope.idSelektovanogRacuna = $scope.racuni[$scope.racuni.length-1].id;
	};
	
	this.setSelected = function(r){
		if(angular.equals($scope.rezim, 0)){
			$scope.idSelektovanogRacuna = r.id;
			$scope.racun = angular.copy(r);
		}
	};
	
	this.nextFormClick = function(){
		
		if(angular.equals($scope.odabir, "Zatvaranja")){
			$scope.$parent.$parent.opsti.tabClick8(8, $scope.idSelektovanogRacuna);
		}else{
			$scope.$parent.$parent.opsti.tabClick10(10, $scope.idSelektovanogRacuna);
		}

	};
	
	this.conf = function(){
		if(angular.equals($scope.rezim, 0)){
			
		}else if(angular.equals($scope.rezim, 1)){
			
			
			
		}else if(angular.equals($scope.rezim, 2)){
			//$scope.selektovanoNaseljenoMesto = {};
		}
	}
	
	this.dismis = function(){
		 
		if(angular.equals($scope.rezim, 0)){
			
			if(!angular.equals($scope.racun, {})){
				
			}
			
		}
		
	}
	
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