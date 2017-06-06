administrator.controller('MessagesController', function($scope, $http, $compile,$log){
	$scope.message={};
	$scope.messages=[];
	var  State={
		VIEW_EDIT: 0,
		ADD : 1,
	    SEARCH : 2
	}
	
	$scope.state=State.VIEW_EDIT;
	this.addClick=function(){
		$log.log("ADD");
		$scope.state=State.ADD;
		$scope.message={};
	}
	
	this.searchClick=function(){
		$scope.state=State.SEARCH;
		
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
		var path="/messages";
		
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				
				$scope.messages=response.data;
			},function errorCallback(response){
				
			}
		);
	}
	
	this.init=function(){
		$scope.state=State.VIEW_EDIT;
		this.refresh();
	}
	this.searchClick=function(){
		$scope.state=State.SEARCH;
	//	saveState();
	}
	this.firstClick=function(){
		if($scope.state===State.VIEW_EDIT){
			$log.log("First click");
			$scope.message=$scope.messages[0];
			//$scope.valutaId=selectedValuta.id;
		//	$scope.valuta=selectedValuta;
		
		
		}
	}
	this.refresh=function(){
		var path="/messages";
		
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				$log.log("ucitavanje svih zabiljezenih poruka");
				$scope.messages=response.data;
			},function errorCallback(response){
				
			}
		);
	}
	/*
		Selektovanje prve ispred stavke u odnosu na tenutno selektovanu.
	*/
	this.prevClick=function(){
		if($scope.state===State.VIEW_EDIT){
			var temp=findIndexOfValuta($scope.message.id);
			$log.log("Index selektovane stavke "+ temp);

			if(temp!=-1 && temp!=0){
				$scope.message=$scope.messages[temp-1];
			
			}
		}
	}
	/*
		Selektovanje prve sledece stavke u tabeli. Ako je posljednja stavka selektovana 
		nista se ne desava.
	*/
	
	findIndexOfValuta=function(id){
		var temp=-1;
		for (var i = 0; i < $scope.messages.length; i++) { 
				if(angular.equals($scope.messages[i].id, id)){
					temp = i;
					return temp;
				}
			}
		return temp;
	}
	this.lastClick=function(){
		if($scope.state===State.VIEW_EDIT){
		
			$scope.message=$scope.messages[$scope.messages.length-1];
			
			
		}
	}
	this.nextClick=function(){
		if($scope.state===State.VIEW_EDIT){
			$log.log("Next valuta");
		//	$log.log("Oznaka djelatnost "+ $scope.message+" "+ $scope.activity.name+" id "+$scope.activity.id);
			
			var temp=findIndexOfValuta($scope.message.id);
			$log.log("Index selektovane stavke "+ temp);
			if(temp!=-1 && temp!=$scope.messages.length){
				$scope.message=$scope.messages[temp+1];
			
			}
		}
	}
	this.commitClick=function(){
		
		
		$log.log("Stanje "+ $scope.state);
		$log.log("Activity "+$scope.message.code);
		if($scope.state==State.ADD && check()){
			$log.log("Stanje dodavanja");
			var path="/message";
			$http({
				method: 'PUT',
				url: path,
				data: $scope.message
			}).then(
			function successCallback(response){
				
				$scope.messages.push(response.data);
				$scope.message={};
				
			},
			function errorCallback(response){
				$log.log("Greska-error");
			}
			);
		}else if($scope.state==State.VIEW_EDIT && check() ){
			$log.log("stAanje izmjene")
			var path="/message";
			
			$http({
				method: 'POST',
				url: path,
				data: $scope.message
			}).then(
			function successCallback(response){
				$log.log("Success");
				var index=findIndexOfValuta($scope.message.id);
				$scope.messages[index]=response.data;
				
			}, 
			function errorCallback(response){
				$log.log("Error");
			});
		}else if($scope.state==State.SEARCH){
			var path="/messageByCode/"+$scope.message.code;
			$http({
				method: 'GET',
				url: path
			}).then(
				function successCallback(response){
					
					$scope.messages=response.data;
					$scope.message={};
					
				}, 
				function errorCallback(response){
				}
			);
		} 
		else {
			$log.log("Nije obradjeno");
		}
	}
	
	var check=function(){
			$log.log("Vrijednosti koje se provjeravaju "+ $scope.message.code );
			if(angular.equals($scope.message, {})){
				toastr.error('Ne mozete ostaviti polja prazna');
				return false;
			}else if(angular.isUndefined($scope.message.code)){
				
				toastr.error('Oznaka  mora sadrzati tacno 5 karaktera');
				return false;
			}else if(!angular.equals($scope.message.code.trim().length, 5)){
				toastr.error('Oznaka mora da sadrzi 5 karaktera!');
				return false;
			}
		$log.log("TRUE");
		return true;
	} 
	
	this.rollbackClick=function(){
		$log.log("Ponisti stanje");
		$scope.state=State.VIEW_EDIT;
	}
	
	this.setSelected=function(message){
		
		
		$scope.message=message;
	}
	
	
	this.deleteClick=function(){
		var path='/message/'+ $scope.message.id;
		$log.log("Path "+ path);
		$http({
			method: 'DELETE',
			url: path
		}).then(
			function successCallback(response){
				var index=findIndexOfValuta(response.data.id);
				$log.log("Index stavke koja se brise je "+ index);
				$scope.messages.splice(index,1);
				$scope.message={};
				
			}, 
			function errorCallback(response){
			}
		);
		
	}
});