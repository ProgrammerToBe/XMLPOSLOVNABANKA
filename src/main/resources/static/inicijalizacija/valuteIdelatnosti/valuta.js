administrator.controller('RukovanjeSifrarnikomValuta', function($scope, $http, $compile,$log){
	$scope.sifrarnikValuta=[];
	$scope.valuta={};
	$scope.valutaId=-1;
	var states=[];
	
	var  State={
		VIEW_EDIT: 0,
		ADD : 1,
	    SEARCH : 2
	}
	$scope.rezim=State.VIEW;
	this.addClick=function(){
		$scope.rezim=State.ADD;
		if(states.length==2){
			states.splice(0,1);
		}
		states.push($scope.rezim);
		$scope.valuta={};
		
	}
	this.deleteClick=function(){
		var path='/deleteValuta/'+ $scope.valutaId;
		$log.log("Putanja je "+ path);
		$http({
			method: 'DELETE',
			url: path
		}).then(
			function successCallback(response){
				var index=findIndexOfValuta(response.data.id);
				$scope.sifrarnikValuta.splice(index,1);
				
			}, 
			function errorCallback(response){
			}
		);
		
	}
	this.init=function(){
		//var path="/findAllValuta";
		$scope.rezim=State.VIEW_EDIT;
		states.push($scope.rezim);
		this.refresh();

	}
	this.searchClick=function(){
		$scope.rezim=State.SEARCH;
		saveState();
	}
	this.firstClick=function(){
		if($scope.rezim===State.VIEW_EDIT){
			$log.log("First click");
			var selectedValuta=$scope.sifrarnikValuta[0];
			$scope.valutaId=selectedValuta.id;
			$scope.valuta=selectedValuta;
		
		}
	}
	this.refresh=function(){
		var path="/findAllValuta";
	
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				
				$scope.sifrarnikValuta=response.data;
			},function errorCallback(response){
				
			}
		);
	}
	/*
		Selektovanje prve ispred stavke u odnosu na tenutno selektovanu.
	*/
	this.prevClick=function(){
		if($scope.rezim===State.VIEW_EDIT){
			var temp=0;
			$log.log("Previous valuta");
			for (var i = 0; i < $scope.sifrarnikValuta.length; i++) { 
				if(angular.equals($scope.sifrarnikValuta[i].id, $scope.valutaId)){
					temp = i;
					break;
				}
			}
			if(temp>0){
				$log.log("Temp "+ temp);
				$log.log("10 "+ 3);
				$log.log("Valuta naziv "+ $scope.sifrarnikValuta[temp-1].name);
				var selectedValuta=$scope.sifrarnikValuta[temp-1];
				$scope.valutaId=selectedValuta.id;
				$scope.valuta=selectedValuta;
			
			}
		}
	}
	/*
		Selektovanje prve sledece stavke u tabeli. Ako je posljednja stavka selektovana 
		nista se ne desava.
	*/
	this.nextClick=function(){
		if($scope.rezim===State.VIEW_EDIT){
			$log.log("Next valuta");
			var temp=$scope.sifrarnikValuta.length;
			for (var i = 0; i < $scope.sifrarnikValuta.length; i++) { 
				if(angular.equals($scope.sifrarnikValuta[i].id, $scope.valutaId)){
					temp = i;
					break;
				}
			}
			if(temp<$scope.sifrarnikValuta.length-1){
				var selectedValuta=$scope.sifrarnikValuta[temp+1];
				$scope.valutaId=selectedValuta.id;
				$scope.valuta=selectedValuta;
			
			}
		}
	}
	/*
		Pronalazenje indeksa valute u kolekciji stavki koje se prikazuju 
		na osnovu vrijednosti id.
	*/
	findIndexOfValuta=function(id){
		var temp=-1;
		for (var i = 0; i < $scope.sifrarnikValuta.length; i++) { 
				if(angular.equals($scope.sifrarnikValuta[i].id, id)){
					temp = i;
					return temp;
				}
			}
		return temp;
	}
	this.lastClick=function(){
		if($scope.rezim===State.VIEW_EDIT){
		
			var selectedValuta=$scope.sifrarnikValuta[$scope.sifrarnikValuta.length-1];
			$scope.valutaId=selectedValuta.id;
			$scope.valuta=selectedValuta;
			
		}
	}
	this.setSelected=function(valuta){
		
		$scope.valutaId=valuta.id;
		$scope.valuta=valuta;
	}
	this.commitClick=function(){
		$log.log("*****Valuta "+$scope.valuta.code+" name "+ $scope.valuta.name);
		//$log.log("*****Djelatnost "+$scope.activity+" name "+ $scope.activity.name);
		if($scope.rezim==State.ADD && check()){
			
			var path="/createNewValuta";
			$http({
				method: 'POST',
				url: path,
				data: $scope.valuta
			}).then(
			function successCallback(response){
				
				$scope.sifrarnikValuta.push(response.data);
				$scope.valuta={};
				
			},
			function errorCallback(response){
				$log.log("Greska-error");
			}
			);
		}else if($scope.rezim==State.VIEW_EDIT && check() ){
			
			var path="/updateValuta";
			
			$http({
				method: 'POST',
				url: path,
				data: $scope.valuta
			}).then(
			function successCallback(response){
				$log.log("Success");
				var index=findIndexOfValuta($scope.valuta.id);
				$scope.sifrarnikValuta[index]=response.data;
				
			}, 
			function errorCallback(response){
				$log.log("Error");
			});
		}else if($scope.rezim==State.SEARCH ){
			var path="/valute/"+$scope.valuta.code+"/"+$scope.valuta.name;
			$http({
				method: 'GET',
				url: path
			}).then(
				function successCallback(response){
					
					$scope.sifrarnikValuta=response.data;
					$scope.valuta={};
					
				}, 
				function errorCallback(response){
				}
			);
		}else{
			$log.log("Nije obradjeno");
		}
	}
	/*
		Metoda koja provjerava da li su polja za unos ispravno popunjena. Poziva se iz metode commit
		u slucaju kada se unosi nova valuta i u slucaju kada se mjenja neka postojeca.
		Metova vraca boolean vrijednost koja nam govori da li je unos ispravan.
	*/
	var check=function(){
			$log.log("Valuta "+ $scope.valuta.code+ " name: "+$scope.valuta.name);
			if(angular.equals($scope.valuta, {})){
				
				return false;
			}else if(angular.isUndefined($scope.valuta.code)){
				
				toastr.error('Oznaka  mora sadrzati tacno 3 karaktera');
				return false;
			}else if(!angular.equals($scope.valuta.code.trim().length, 3)){
				toastr.error('Oznaka mora da sadrzi 3 karaktera!');
				return false;
			}else if(angular.isUndefined($scope.valuta.name)){
				toastr.error('Naziv mora biti zadat!');
				return  false;
			}
		$log.log("TRUE");
		return true;
	}
	/*
		Vracanje u rezim za pregled i izmjenu (defaultni rezim).
	*/
	this.rollbackClick=function(){
		$log.log("Ponisti stanje");
		$scope.rezim=State.VIEW_EDIT;
	}
	/* Metoda koja prati rezim rada i koja implicitno omogucava vracanje u prethodno stanje iz 
		stanja za brisanje. Prate se samo poslednja dva stanja.
	*/
	var saveState=function(){
		if(states.length==2){
			states.splice(0,1);
		}
		states.push($scope.rezim);
	}
});