administrator.controller('HandleActivities', function($scope, $http, $compile,$log){
	$scope.activities=[];
	$scope.activity={};
	$scope.valutaId=-1;
	var states=[];
	
	var  State={
		VIEW_EDIT: 0,
		ADD : 1,
	    SEARCH : 2
	}
	$scope.state=State.VIEW_EDIT;
	this.addClick=function(){
		$scope.state=State.ADD;
		if(states.length==2){
			states.splice(0,1);
		}
		states.push($scope.state);
		$scope.activity={};
		
	}
	this.deleteClick=function(){
		var path='/activity/'+ $scope.activity.id;

		$http({
			method: 'DELETE',
			url: path
		}).then(
			function successCallback(response){
				var index=findIndexOfValuta(response.data.id);
				$scope.activities.splice(index,1);
				$scope.activity={};
				
			}, 
			function errorCallback(response){
			}
		);
		
	}
	this.init=function(){
		
		$scope.state=State.VIEW_EDIT;
		states.push($scope.state);
		this.refresh();
		
		

	}
	this.searchClick=function(){
		$scope.state=State.SEARCH;
		saveState();
	}
	this.firstClick=function(){
		if($scope.state===State.VIEW_EDIT){
			$log.log("First click");
			$scope.activity=$scope.activities[0];
			//$scope.valutaId=selectedValuta.id;
		//	$scope.valuta=selectedValuta;
		
		
		}
	}
	this.refresh=function(){
		var path="/activities";
		
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				
				$scope.activities=response.data;
			},function errorCallback(response){
				
			}
		);
	}
	/*
		Selektovanje prve ispred stavke u odnosu na tenutno selektovanu.
	*/
	this.prevClick=function(){
		if($scope.state===State.VIEW_EDIT){
			var temp=findIndexOfValuta($scope.activity.id);
			$log.log("Index selektovane stavke "+ temp);

			if(temp!=-1 && temp!=0){
				$scope.activity=$scope.activities[temp-1];
			
			}
		}
	}
	/*
		Selektovanje prve sledece stavke u tabeli. Ako je posljednja stavka selektovana 
		nista se ne desava.
	*/
	this.nextClick=function(){
		if($scope.state===State.VIEW_EDIT){
			$log.log("Next valuta");
			$log.log("Oznaka djelatnost "+ $scope.activity+" "+ $scope.activity.name+" id "+$scope.activity.id);
			
			var temp=findIndexOfValuta($scope.activity.id);
			$log.log("Index selektovane stavke "+ temp);
			if(temp!=-1 && temp!=$scope.activities.length){
				$scope.activity=$scope.activities[temp+1];
			
			}
		}
	}
	/*
		Pronalazenje indeksa valute u kolekciji stavki koje se prikazuju 
		na osnovu vrijednosti id.
	*/
	findIndexOfValuta=function(id){
		var temp=-1;
		for (var i = 0; i < $scope.activities.length; i++) { 
				if(angular.equals($scope.activities[i].id, id)){
					temp = i;
					return temp;
				}
			}
		return temp;
	}
	this.lastClick=function(){
		if($scope.state===State.VIEW_EDIT){
		
			$scope.activity=$scope.activities[$scope.activities.length-1];
			
			
		}
	}
	this.setSelected=function(activity){
		
		
		$scope.activity=activity;
	}
	this.commitClick=function(){
		
		
		$log.log("Stanje "+ $scope.state);
		$log.log("Activity "+$scope.activity.code +" name "+$scope.activity.name);
		if($scope.state==State.ADD && check()){
			$log.log("Stanje dodavanja");
			var path="/activity";
			$http({
				method: 'PUT',
				url: path,
				data: $scope.activity
			}).then(
			function successCallback(response){
				
				$scope.activities.push(response.data);
				$scope.activity={};
				
			},
			function errorCallback(response){
				$log.log("Greska-error");
			}
			);
		}else if($scope.state==State.VIEW_EDIT && check() ){
			$log.log("stAanje izmjene")
			var path="/activity";
			
			$http({
				method: 'POST',
				url: path,
				data: $scope.activity
			}).then(
			function successCallback(response){
				$log.log("Success");
				var index=findIndexOfValuta($scope.activity.id);
				$scope.activities[index]=response.data;
				
			}, 
			function errorCallback(response){
				$log.log("Error");
			});
		}else if($scope.state==State.SEARCH){
			var path="/activities/"+$scope.activity.code+"/"+$scope.activity.name;
			$http({
				method: 'GET',
				url: path
			}).then(
				function successCallback(response){
					
					$scope.activities=response.data;
					$scope.activity={};
					
				}, 
				function errorCallback(response){
				}
			);
		}
		else {
			$log.log("Nije obradjeno");
		}
	}
	/*
		Metoda koja provjerava da li su polja za unos ispravno popunjena. Poziva se iz metode commit
		u slucaju kada se unosi nova valuta i u slucaju kada se mjenja neka postojeca.
		Metova vraca boolean vrijednost koja nam govori da li je unos ispravan.
	*/
	var check=function(){
			$log.log("Vrijednosti koje se provjeravaju "+ $scope.activity.code +" "+ $scope.activity.name);
			if(angular.equals($scope.activity, {})){
				toastr.error('Ne mozete ostaviti polja prazna');
				return false;
			}else if(angular.isUndefined($scope.activity.code)){
				
				toastr.error('Oznaka  mora sadrzati tacno 5 karaktera');
				return false;
			}else if(!angular.equals($scope.activity.code.trim().length, 5)){
				toastr.error('Oznaka mora da sadrzi 5 karaktera!');
				return false;
			}else if(angular.isUndefined($scope.activity.name)){
				toastr.error('Naziv mora biti zadat!');
				return  false;
			}
		$log.log("TRUE");
		return true;
	}
	/*
		Vracanje u state za pregled i izmjenu (defaultni state).
	*/
	this.rollbackClick=function(){
		$log.log("Ponisti stanje");
		$scope.state=State.VIEW_EDIT;
	}
	/* Metoda koja prati state rada i koja implicitno omogucava vracanje u prethodno stanje iz 
		stanja za brisanje. Prate se samo poslednja dva stanja.
	*/
	var saveState=function(){
		if(states.length==2){
			states.splice(0,1);
		}
		states.push($scope.state);
	}
});