<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
					<div class="panel-body">
						<div class="panel-header-own">
							<img src="${c.getFbp().getPicURL()}" alt="Your Picture"
								class="img-rounded">
							<span class="header-name header-bold">${c.getFbp().getFirstname()}</span>
							<span class="header-name">${c.getFbp().getName()}</span>
						</div>

						<table class="table table-striped">
							<tr>
								<th>Attribute</th>
								<th>Value</th>
							</tr>
							<tr>
								<td>Image</td>
								<td>${c.getFbp().getPicURL()}</td>
							</tr>
							<tr>
								<td>Firstname</td>
								<td>${c.getFbp().getFirstname()}</td>
							</tr>
							<tr>
								<td>Lastname</td>
								<td>${c.getFbp().getName()}</td>
							</tr>
							<tr>
								<td>Birthday</td>
								<td>${c.getFbp().getFormattedBirthday()}</td>
							</tr>
							<tr>
								<td>Home</td>
								<td>${c.getFbp().getHome().getName()}</td>
							</tr>
							<tr>
								<td>location</td>
								<td>${c.getFbp().getLocation().getName()}</td>
							</tr>
							<tr>
								<td>Education</td>
								<td><c:forEach var="e" items="${c.getFbp().getEducation()}">
        						${e.getName()}</br>
									</c:forEach></td>
							</tr>
							<tr>
								<td>Employer</td>
								<td><c:forEach var="e" items="${c.getFbp().getEmployer()}">
  							${e.getName()}</br>
									</c:forEach></td>
							</tr>
							<tr>
								<td>Interests</td>
								<td><c:forEach var="i" items="${c.getFbp().getInterest()}">
  								${i}</br>
									</c:forEach>
							</tr>
						</table>

					</div>
				</div>

			</div>
			<div class="content-area right">
			
			<div class="panel panel-default">
					<div class="panel-body">
						<div class="panel-header-own">
							<img src="${c.getFbp().getPicURL()}" alt="Your Picture"
								class="img-rounded">
							<span class="header-name header-bold">${c.getFbp().getFirstname()}</span>
							<span class="header-name">${c.getFbp().getName()}</span>
						</div>

						<table class="table">
							<tr>
								<th>Attribute</th>
								<th>Value</th>
							</tr>
							<tr>
								<td>Image</td>
								<td>${c.getFbp().getPicURL()}</td>
							</tr>
							<tr>
								<td>Firstname</td>
								<td>${c.getFbp().getFirstname()}</td>
							</tr>
							<tr>
								<td>Lastname</td>
								<td>${c.getFbp().getName()}</td>
							</tr>
							<tr>
								<td>Birthday</td>
								<td>${c.getFbp().getFormattedBirthday()}</td>
							</tr>
							<tr>
								<td>Home</td>
								<td>${c.getFbp().getHome().getName()}</td>
							</tr>
							<tr>
								<td>location</td>
								<td>${c.getFbp().getLocation().getName()}</td>
							</tr>
							<tr>
								<td>Education</td>
								<td><c:forEach var="e" items="${c.getFbp().getEducation()}">
        						${e.getName()}</br>
									</c:forEach></td>
							</tr>
							<tr>
								<td>Employer</td>
								<td><c:forEach var="e" items="${c.getFbp().getEmployer()}">
  							${e.getName()}</br>
									</c:forEach></td>
							</tr>
							<tr>
								<td>Interests</td>
								<td><c:forEach var="i" items="${c.getFbp().getInterest()}">
  								${i}</br>
									</c:forEach>
							</tr>
						</table>

					</div>
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
			
			function bob(result) {
			    alert('hi bob, you typed: '+ result);
			}

			$('#search-celebrity').change(function(){
			    var result = $(this).val();
			    bob(result);
			});
		</script>


	</tag:loggedin>

</body>
</html>
