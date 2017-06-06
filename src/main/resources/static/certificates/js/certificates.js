var app = angular.module('certificates', []);

app.controller("TabController", function(){
    this.tab = 1;

    this.isSet = function(checkTab) {
      return this.tab === checkTab;
    };

    this.setTab = function(setTab) {
      this.tab = setTab;
    };
});

app.controller("CertificateController", function($http,$scope, $log){
	var control = this;
	$scope.revokeRequest={};
	control.certificate = {};
	control.certificateRequest = {};
	control.certReqs = [];
	control.certificate.validFrom = new Date();
	control.result = "";
	$scope.alias="";
	$scope.certificateDTO={};
	$scope.found=false;
	$scope.aliasForRevoke="";
	$scope.aliasForCheck="";
	
	this.generate = function(){
		if(control.certificate.validFrom < control.certificate.validTo){
			$http.post('/certificates/genCertificate', control.certificate).then(function success(response) {
				if(response.data === 'ok'){
					toastr.success('Certificate successfully generated!');
					control.certificate = {};
				}else{
					toastr.error(response.data);
				}
			}, function error(response) {
				control.result = "Unknown error ocurred."
			});
		}else{
			toastr.error('Invalid date entries!');
		}
	};
	
	this.generateRequest = function(){
		$http.post('/certificates/genCertificateRequest', control.certificateRequest).then(function success(response) {
			if(response.data === 'ok'){
				toastr.success('Certificate request successfully generated!');
				control.certificateRequest = {};
			}else{
				toastr.error(response.data);
			}
		}, function error(response) {
			control.result = "Unknown error ocurred."
		});
	};
	
	this.resetIssuer = function(){
		if(control.certificate.selfSigned){
			control.certificate.issuerAlias = "";
		}
	}
	
	this.readCertificate=function(){
		$log.log("Read certificate");
		var path='/certificates/certificate/'+$scope.alias;
		$log.log("Putanja "+ path);
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				$log.log("Uspijesno pronalazenje sertifikata");
				$scope.certificateDTO=response.data;
				$scope.alias="";
				$scope.found=true;
			}, 
			function errorCallback(response){
				$scope.found=false;
			}
		);
	}
	
	this.revokeCertificate=function(){
		$log.log("Revoke certificate.");
		var path='/certificates/revokeCertificate/'+$scope.aliasForRevoke;
		$log.log("Path "+path);
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				$log.log("SuccessCallback");
				$log.log("ResponseStatus: "+response.status);
				$log.log("ResponseData: "+response.data);
				if(response.status===200){
					toastr.success(response.data);
				}else{
					toastr.error("Tekst neki");
				}
			},
			function errorCallback(response){
				$log.log("ËRROR");
			}
		);
	}
	
	this.checkCertificateStatus=function(){
			$log.log("Check sertificate status");
			var path='/certificates/status/'+ $scope.aliasForCheck;
			$log.log("path "+ path);
			$http({
				method:'GET',
				url: path
			}).then(
			function successCallback(response){
				$log.log("Success callback");
				if(response.status===200){
					toastr.success(response.data);
				}else{
					toastr.error(response.data);
				}
			}, function errorCallback(response){
				$log.log("Error");
			}
			);
	}
	
	this.loadRequests = function() {
		
		$http({
			method: 'GET',
			url: '/certificates/getCertificateRequests'
		}).then(function success(response) {
			control.certReqs = response.data;
		});
	}
			
	this.loadRequests();
	
	this.approveCert = function(certReq){
		$http.get('/certificates/makeCertificate/' + certReq.id).then(function success(response) {
			if(response.data === 'ok'){
				toastr.success('Certificate successfully generated!');
				
				var index = -1;		
				var certReqArr = eval( control.certReqs );
				for( var i = 0; i < certReqArr.length; i++ ) {
					if( certReqArr[i].id === certReq.id ) {
						index = i;
						break;
					}
				}
				if( index === -1 ) {
					toastr["error"]('Something gone wrong.');
				}
				control.certReqs.splice( index, 1 );
			}else{
				toastr.error(response.data);
			}
		}, function error(response) {
			control.result = "Unknown error ocurred."
		});
	}
	
});


app.controller("CertificateRevokeAndGetStatus", function($http,$scope, $log){
	$scope.banks=[];
	$scope.bank={};
	$scope.revokeReques={};
	$scope.revokeRequests=[];
	this.nesto="Neki string probe radi.";
	$scope.nesto1="tako nesto";
	this.init=function(){
		
		
		var path='/allBanks';
		
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				
				angular.forEach(response.data, function (element, index) {
			
					$scope.banks.push(element);
					
				});
			
				
			}, function errorCallback(response){
				$log.log("Error callback");
			}
		);
		/*$log.log("*************************");
		$log.log("Citanje zahtjeva iz baze");
		$log.log("*************************"); */
		var path="/certificates/revokeRequest";
		$http({
			method:'GET',
			url: path
			
		}).then(
			function successCallback(response){
				angular.forEach(response.data, function (element, index) {
					$log.log("Komentar "+ element.comment);
					$scope.revokeRequests.push(element);
					
				});
				//$log.log("Broj zabiljezenih zahtjeva banke "+$scope.revokeRequests.length)
			}, function errorCallback(response){
				
			}
		);
	};
	
	this.sendRevocationRequest=function(){
		$log.log("Send revoke request ");
		$log.log("Revoke request alias: "+ $scope.revokeRequest.alias);
		//$log.log("Selektovana banka je "+ $scope.revokeReques.bank.name);
		$log.log("Selected bank "+ $scope.bank.name);
		//$scope.revokeRequest.bank=$scope.bank;
		var path="/certificates/revokeRequest";
		$http({
			method: 'PUT',
			url: path,
			data: $scope.revokeRequest
		}).then(
			function successCallback(response){
				$log.log(response.data);
			}, 
			function errorCallback(response){
				
			}
		);
	}
	
	this.acceptRequest=function(id){
		var path="/certificates/revokeRequest/"+id+"/accept";
		$http({
			method: 'DELETE',
			url:path
		}).then(
			function successCallback(response){
				$log.log(response.data);
				var index=getIndex(id,$scope.revokeRequests);
				$log.log("index "+index);
				$scope.revokeRequests.splice(index,1);
			},
			function errorCallback(response){
				$log.log(response.data);
			}
		);
	}
	
	this.declineRequest=function(id){
		var path="/certificates/revokeRequest/"+id+"/decline";
		$http({
			method: 'DELETE',
			url: path
		}).then(
			function successCallback(response){
				$log.log(response.data);
				var index=getIndex(id,$scope.revokeRequests);
				$scope.revokeRequests.splice(index,1);
			}, 
			function errorCallback(response){
				$log.log(response.data);
			}
		);
	}
	this.getCertificateStatus=function(bank,id){
		var path="/ocspRespone/"+id;
		$log.log("Putanja koja se gadja "+ path);
		
	}
	getIndex=function(id, collection){
		index=-1;
		 for( var i = 0; i < collection.length; i++ ) {
                if( collection[i].id === id ) {
                    index = i;
					//return index;
                    break;
                 }
          }
		  return index;
	}
});

app.controller("CertificateStatus", function($http,$scope, $log){
	this.init=function(){
		$log.log("CertificateStatus");
		$scope.banks=[];
		$scope.bank={};
		var path='/allBanks';
		
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				$log.log("Broj banki "+ response.data.length);
				angular.forEach(response.data, function (element, index) {
					
					$scope.banks.push(element);
					
				});
			
				
			}, function errorCallback(response){
				$log.log("Error callback");
			}
		);
		$log.log("*************************");
		$log.log("Citanje zahtjeva iz baze");
		$log.log("*************************");
	}
	this.getCertificateStatus=function(bank,alias){
		$log.log("Bank "+ bank.name);
		$log.log("Potvrda unosa");
		var path="/ocspResponse/"+alias;
		$log.log("Putanja koja se gadja "+ path);
		$http({
			method: 'POST',
			url: path,
			data: bank
		}).then(
		function successCallback(response){
			$log.log("Response OK");
			$log.log(response.data.status);
			if(response.data.status=="MALFORMEDREQUEST"){
				$log.log("Prepoznaje statuse");
				toastr.error("Status: "+ response.data.status);
			}
			if(response.data.status=="SUCCESSFUL"){
				var responseBytes=response.data.respnseBytes;
				var nekiOdgovori=responseBytes.responseData;
				angular.forEach(nekiOdgovori.responses, function (element, index) {
					toastr.success("Status: "+ element.certStatus);
					//$scope.banks.push(element);
					$log.log("Status: "+ element.certStatus);
					
				});
				//response.data.responseBytes.responseData.responses
			}
		}, function errorCallback(response){
			$log.log("fail");
		}
		);
	}
});
