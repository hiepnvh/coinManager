<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>admin</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    <link href="${contextPath}/resources/css/bootstrap-switch.css" rel="stylesheet">
	<script src="${contextPath}/resources/js/bootstrap-switch.js"></script>
    
    <script src="${contextPath}/resources/js/AppController.js"></script>
</head>
<body>
<div class="container">

    <div class="header">
    	<c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h4 class="userNav">Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h4>
        <h3 class="page-title">Coin Manager</h3>
    </c:if>
    </div>
    
    <div class="main-container" ng-app="coinman" ng-controller="AppCtrl">
		<!-- cb list -->
		<div ng-if="showCBList">
			<h4>Coin Bot List</h4>
			<table class="table">
				<th>Coin</th>
		        <th>Volume</th>
		        <th>Profit/Loss</th>
				<tr class="cbRow" ng-repeat="cb in coinbots" ng-click="showCBDetailFunc(cb)" onclick="changeText()">
					
				    <td>{{ cb.coinCode }}</td>
				    <td>{{ cb.volume }}</td>
				    <td ng-if="!cb.isBought">{{ round((cb.yourMoney)*100/ (cb.firstVolume*cb.firstPrice) - 100)}}%</td>
				    <td ng-if="cb.isBought">---(Buying)%</td>
				</tr>
			</table>
			<button type="button" class="btn" ng-click="showCBDetailFunc()">Add</button>
			<button type="button" class="btn" ng-click="showLogFunc()">Log</button>
		</div>
		<!--  cb list -->
		
		<!-- cb detail -->
		<div class="CBDetail" ng-if="showCBDetail">
			<h4>Coin Bot Detail</h4>
			<form class="form-group">
				<label>Coin code</label>
				<input class="form-control" type="text" ng-model="currCB.coinCode" >
				<label>Platform</label>
				<input class="form-control" type="text" ng-model="currCB.platform" ng-disabled="true">
				<label>Volume</label>
				<input class="form-control" type="text" ng-model="currCB.volume" >
				<label>Sell limit</label>
				<input class="form-control" type="text" ng-model="currCB.sellLimit" >
				<label>Buy limit</label>
				<input class="form-control" type="text" ng-model="currCB.buyLimit" >
				<label>Account name</label>
				<input class="form-control" type="text" ng-model="currCB.accountName" ng-disabled="true">
				<label>Account password</label>
				<input class="form-control" type="text" ng-model="currCB.accountPassword" ng-disabled="true">
				<label class="switch">
				  <input type="checkbox" ng-model="currCB.active">
				  <span class="slider"></span>
				</label>
			</form>
			<button type="button" class="btn" ng-click="saveCB(currCB)">Save</button>
			<button type="button" class="btn" ng-click="showCBListFunc()">Back to List</button>
		</div>
		<!-- cb detail -->
		<label ng-if="showCBDetail">Chart for 1 month...</label>
		
		<!-- log -->
		<div ng-if="showLog">
		<h4>Log for: {{cbSelect.coinCode}}</h4>
			<br>
			<select ng-model="cbSelect" ng-options="cb.coinCode for cb in coinbots" ng-change="viewLog(cbSelect)">
				<option value="">Select coin Code to see log</option>
			</select>
			<br>
			<table class="table">
				<th>Buy/Sell</th>
		        <th>Volume</th>
		        <th>Price</th>
		        <th>Date</th>
				<tr class="cbRow" ng-repeat="log in logs" ng-if="log.action">
					
				    <td>{{ log.isBought==true?'Buy':'Sell' }}</td>
				    <td>{{ log.volume }}</td>
				    <td>{{ log.lastPrice}}</td>
				    <td>{{log.lastModDate}}</td>
				</tr>
			</table>
			<button type="button" class="btn" ng-click="showCBListFunc()">Back to List</button>
		</div>
		<!-- log -->
		
	</div>
	<script type="text/javascript">
	function getScope(ctrlName) {
	    var sel = 'div[ng-controller="' + ctrlName + '"]';
	    return angular.element(sel).scope();
	}
	
	var baseUrl = "https://widgets.cryptocompare.com/";
	var scripts = document.getElementsByTagName("script");
	var embedder = scripts[ scripts.length - 1 ];
	
	
	function changeText() {
		setTimeout(addChart, 1);
	}   
	
	function addChart(){
		var $scope = getScope('AppCtrl');
	    var currCoinCode = $scope.currCB.coinCode;
	    console.log(currCoinCode);
	    var appName = encodeURIComponent(window.location.hostname);
		if(appName==""){appName="local";}
		var s = document.createElement("script");
		s.type = "text/javascript";
		s.async = true;
		var theUrl = baseUrl+'serve/v1/coin/chart?fsym=' + currCoinCode + '&tsym=USDT&period=1M';
		s.src = theUrl + ( theUrl.indexOf("?") >= 0 ? "&" : "?") + "app=" + appName;
		
		console.log(embedder.parentNode);
		embedder.parentNode.appendChild(s);
		
	    $scope.$apply();
	}
	
	
	
			</script>
</div>
<!-- /container -->

</body>
</html>
