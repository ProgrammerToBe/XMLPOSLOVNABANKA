var app = angular.module('certificates', []);
app.controller("CertificateRevokeAndGetStatus", function($http,$scope, $log){
	$scope.banks=[];
	this.init=function(){
		$log.log("Inicijalna funkcija kontrolera");
		
		var path='/allBanks';
		$log.log("Path je "+ path);
		$log.log("Preuzimanje registrovanih banki");
		$http({
			method: 'GET',
			url: path
		}).then(
			function successCallback(response){
				$log.log("Broj registrovanih banaka "+ response.data.length);
				$log.log("******");
				angular.forEach(response.data, function (element, index) {
			//	if (element.selected) {
					//element.selected = false;
					$scope.banks.push(element);
					//$scope.appliedObjects.splice(index, 1);
					//}
				});
			//	$scope.banks=response.data;
				$log.log("#######");
				$log.log("Broj prebacenih banaka "+ $scope.banks.length);
			}, function errorCallback(response){
				$log.log("Error callback");
			}
		);
	};
});