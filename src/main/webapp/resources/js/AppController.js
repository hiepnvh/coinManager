var app = angular.module('coinman', []);
app.controller('AppCtrl', function($scope, $http) {
    $scope.firstName = "John";
    $scope.lastName = "Doe";
    $scope.coinbots = [];
    $scope.showCBDetail = false;
    $scope.showCBList = true;
    $scope.showLog = false;
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
    	$scope.showCBList = false;
    	$scope.showLog = false;
    }
    
    $scope.showLogFunc = function(){
    	$scope.showLog = true;
    	$scope.showCBDetail = false;
    	$scope.showCBList = false;
    }
    
    $scope.showCBListFunc = function(){
    	$scope.currCB = {};
    	$scope.showCBDetail = false;
    	$scope.showCBList = true;
    	$scope.showLog = false;
    	$scope.loadAllCoinBots();
    	$('.ccc-chart').remove();
    }
    
    $scope.saveCB = function(currCB){
//    	console.log(currCB);
    	var url = 'coinbots/save';
    	
    	$http.post(url, currCB)
    	.then(function(response) {
			alert('Saved!');
			$scope.showCBListFunc();
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
    
    $scope.viewLog = function(cb){
    	if(cb != null){
    		var url = "coinbots/viewlog?coincode=" + cb.coinCode;
        	$http.get(url)
        	.then(function(response) {
    			console.log(response.data);
    			$scope.logs = response.data;
            });
    	}
    	
    }
    
    $scope.round = function(num){
    	return Math.round(num * 1000000) / 1000000;
    }
    
    $scope.loadAllCoinBots();
});