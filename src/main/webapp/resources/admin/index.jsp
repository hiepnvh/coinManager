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
        <h3 class="page-title">Guest</h3>
    </c:if>
    </div>
    
    <div class="main-container" ng-app="coinman" ng-controller="AppCtrl">
		<!-- cb list -->
		<div ng-if="!showCBDetail">
			<h4>Coin Bot List</h4>
			<table class="table">
				<th>Coin</th>
		        <th>Volume</th>
		        <th>Profit/Loss</th>
				<tr ng-repeat="cb in coinbots" ng-click="showCBDetailFunc(cb)">
					
				    <td>{{ cb.coinCode }}</td>
				    <td>{{ cb.volume }}</td>
				    <td>500</td>
				</tr>
			</table>
			<button type="button" class="btn" ng-click="showCBDetailFunc()">Add</button>
		</div>
		<!--  cb list -->
		
		<!-- cb detail -->
		<div ng-if="showCBDetail">
			<h4>Coin Bot Detail</h4>
			<form class="form-group">
				<label>Coin code</label>
				<input class="form-control" type="text" ng-model="currCB.coinCode" >
				<label>Platform</label>
				<input class="form-control" type="text" ng-model="currCB.platform" >
				<label>Volume</label>
				<input class="form-control" type="text" ng-model="currCB.volume" >
				<label>Sell limit</label>
				<input class="form-control" type="text" ng-model="currCB.sellLimit" >
				<label>Buy limit</label>
				<input class="form-control" type="text" ng-model="currCB.buyLimit" >
				<label>Interval time</label>
				<input class="form-control" type="text" ng-model="currCB.intervalTime" >
				<label class="switch">
				  <input type="checkbox" ng-model="currCB.active">
				  <span class="slider"></span>
				</label>
			</form>
			<button type="button" class="btn" ng-click="saveCB(currCB)">Save</button>
			<button type="button" class="btn" ng-click="showCBList()">Back to List</button>
		</div>
		<!-- cb detail -->
	</div>

</div>
<!-- /container -->

</body>
</html>
