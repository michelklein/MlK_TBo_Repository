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
	
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Sign in with Facebook example</title>
</head>
<body>

	<tag:notloggedin>
		<a href="signin">sign in with Facebook</a>
	</tag:notloggedin>
	<tag:loggedin>
		<a href="./logout">logout</a>
		<h1>You and ...</h1>

		<table class="table table-striped">
			<tr>
				<th>Attribute</th>
				<th>Facebook</th>
				<th>OpenData</th>
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
				<td>Location</td>
				<td>${c.getFbp().getCurrLocation().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Firstname</td>
				<td>${c.getFbp().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Firstname</td>
				<td>${c.getFbp().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Firstname</td>
				<td>${c.getFbp().getName()}</td>
				<td>?</td>
			</tr>
			<tr>
				<td>Firstname</td>
				<td>${c.getFbp().getFirstname()}</td>
				<td>?</td>
			</tr>
		</table>


		<div class="well">
		 <input id="search" type="text"	data-provide="typeahead" data-items="4"> 
		</div>

							

		<!-- <span class="tt-dropdown-menu">
			<div class="tt-dataset-what">
				<span class="tt-suggestions">
					<div class="tt-suggestion">
						<input id="search" class="typeahead" type="text"
							data-provide="typeahead" data-items="4">
					</div>
				</span>
			</div>
		</span> -->

			<script type="text/javascript">
			var celebrities = ${c.getCelebritiesAsJson()};
			console.debug(celebrities);
			$('#search').typeahead({
				name : 'celebrities',
				local : celebrities
			});
		</script>

	</tag:loggedin>

</body>
</html>
