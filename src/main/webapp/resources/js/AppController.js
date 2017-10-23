var app = angular.module('coinman', []);
app.controller('AppCtrl', function($scope, $http) {
    $scope.firstName = "John";
    $scope.lastName = "Doe";
    $scope.coinbots = [];
    $scope.showCBDetail = false;
    $scope.currCB = {};

    $scope.loadAllCoinBots = function(){
    	$http.get("coinbots/search")
        .then(function(response) {
        	$scope.coinbots =  response.data;
        });
    }
    
    $scope.showCBDetailFunc = function(cb){
    	$scope.currCB = cb;
    	$scope.showCBDetail = true;
    }
    
    $scope.showCBList = function(){
    	$scope.currCB = {};
    	$scope.showCBDetail = false;
    	$scope.loadAllCoinBots();
    	$('.ccc-chart').remove();
    }
    
    $scope.saveCB = function(currCB){
    	console.log(currCB);
    	var url = '/coinbots/save';
    	
    	$http.post(url, currCB)
    	.then(function(response) {
			alert('Saved!');
			$scope.showCBList();
        });
//		res.success(function(data, status, headers, config) {
//			$scope.message = data;
//			alert('Saved!');
//			$scope.showCBList();
//		});
//		res.error(function(data, status, headers, config) {
//			alert( "failure message: " + JSON.stringify({data: data}));
//		});	
		
//    	$http({
//    	    method: 'POST',
//    	    url: url,
//    	    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
//    	    data: currCB
//    	});
    }
    
    $scope.round = function(num){
    	return Math.round(num * 100) / 100;
    }
    
    $scope.loadAllCoinBots();
});