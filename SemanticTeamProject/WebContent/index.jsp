<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="bootstrap/3.0.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="bootstrap/3.0.0/js/jquery-1.10.2.min.js"></script>
<script src="bootstrap/3.0.0/js/typeahead.js"></script>

<link rel="stylesheet"
	href="bootstrap/3.0.0/css/typeahead.js-bootstrap.css">
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap-mod.css">

<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Sign in with Facebook example</title>
</head>
<body>

	<tag:notloggedin>
		<a href="signin">sign in with Facebook</a>
	</tag:notloggedin>

	<tag:loggedin>
		<div id="header">
			<!-- <button type="button" id="logout" class="btn btn-default btn-sm"
				onclick="location.href='./logout'">
				<span class="glyphicon glyphicon-off"></span>
			</button>
			<button type="button" id="refresh" class="btn btn-default btn-sm"
				onclick="location.href='./magic'">
				<span class="glyphicon glyphicon-refresh"></span>
			</button>
 -->

			<div id="search-bar">
				<div class="input-group">
					<input id="search-celebrity" type="text" class="form-control"
						data-provide="typeahead" data-items="4"
						placeholder="Type in your celebrity ..."> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-search"></span>
					</span><span class="input-group-addon"> <span
						class="glyphicon glyphicon-refresh" onclick="location.href='./magic'"></span>
					</span><span class="input-group-addon"> <span
						class="glyphicon glyphicon-off" onclick="location.href='./logout'"></span>
					</span>
				</div>
			</div>

			<h1>
				Celebritie comparison <small> You and your favourites</small>
			</h1>
		</div>


		<div id="content">
			<div class="content-area">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">${c.getFbp().getFirstname()} ${c.getFbp().getName()}</h3>
					</div>
					<div class="panel-body"><img src="${c.getFbp().getPicURL()}" alt="Your Picture" class="img-rounded"></div>
				</div>
				
			</div>
			<div class="content-area right">
			
			<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">${c.getFbp().getFirstname()} ${c.getFbp().getName()}</h3>
					</div>
					<div class="panel-body"><img src="${c.getFbp().getPicURL()}" alt="Your Picture" class="img-rounded"></div>
				</div>
			</div>
		</div>


		<script type="text/javascript">
			var celebrities = ${c.getCelebritiesAsJson()};
			console.debug(celebrities);
			$('#search-celebrity').typeahead({
				name : 'celebrities',
				local : celebrities,
				limit: 10
			});
		</script>

				<table class="table table-striped">
			<tr>
				<th>Attribute</th>
				<th>Facebook</th>
				<th>OpenData</th>
			</tr>
			<tr>
				<td>Image</td>
				<td>${c.getFbp().getPicURL()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Firstname</td>
				<td>${c.getFbp().getFirstname()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Lastname</td>
				<td>${c.getFbp().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Birthday</td>
				<td>${c.getFbp().getFormattedBirthday()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Home</td>
				<td>${c.getFbp().getHome().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>location</td>
				<td>${c.getFbp().getLocation().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Education</td>
				<td><c:forEach var="e" items="${c.getFbp().getEducation()}">
        ${e.getName()}</br>
					</c:forEach></td>
				<td>?</td>
			</tr>
			<tr>
				<td>Employer</td>
				<td><c:forEach var="e" items="${c.getFbp().getEmployer()}">
  ${e.getName()}</br>
					</c:forEach></td>
				<td>?</td>
			</tr>
			<tr>
				<td>Interests</td>
				<td><c:forEach var="i" items="${c.getFbp().getInterest()}">
  ${i}</br>
					</c:forEach>
				<td>?</td>
			</tr>
		</table>
		

	</tag:loggedin>

</body>
</html>
