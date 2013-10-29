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

		<script type="text/javascript" language="javascript">
			$.getJSON("fetchData?method=fetchFacebookPerson", function(data) {
				var json = data;
				$("#content").show();
				$("#fbUserImage").attr('src', json.imageURL);
				$("#userFirstname").html(json.firstname);
				$("#userLastname").html(json.lastname);
				console.debug(data);
			});
			$( document ).ajaxStart(function() {
				$("#loading").show();
			});

			$( document ).ajaxComplete(function() {
				$("#loading").hide();
			});
		</script>

		<div id="header">


			<div id="search-bar">
				<div class="input-group">
					<input id="search-celebrity" type="text" class="form-control"
						data-provide="typeahead" data-items="4"
						placeholder="Type in your celebrity ..."> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-search"></span>
					</span><span class="input-group-addon"> <span
						class="glyphicon glyphicon-refresh"
						onclick="location.href='./magic'"></span>
					</span><span class="input-group-addon"> <span
						class="glyphicon glyphicon-off" onclick="location.href='./logout'"></span>
					</span>
				</div>
			</div>

			<h1>
				Celebritie comparison <small> You and your favourites</small>
			</h1>
		</div>
	<div id="loading">loading...</div>
		<div id="content">
			<div class="row">
				<div class="col-md-3 col-md-offset-2">
					<div id="fbUser">
						<div class="panel-header-own">
							<img id="fbUserImage" src="images/defaultProfile.png"
								class="img-rounded"> <span id="userFirstname"
								class="header-name header-bold">Test</span> <span
								id="userLastname" class="header-name">Lastname</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-md-offset-4">
					<div id="fbUser">
						<div class="panel-header-own">
							<img id="celebrity" src="images/defaultProfile.png"
								class="img-rounded"> <span id="celebrityFirstname"
								class="header-name header-bold"></span> <span
								id="celebrityLastname" class="header-name"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">attrbitues</div>
				<div class="col-md-4 col-md-offset-3">Comparison</div>
			</div>
		</div>

		<script type="text/javascript">
			var celebrities = $
			{
				c.getCelebritiesAsJson()
			};
			$('#search-celebrity').typeahead({
				name : 'celebrities',
				local : celebrities,
				limit : 10,
			}).on('typeahead:selected', function($e) {
				var $typeahead = $(this);
				document.location.href = './magic?celname=' + $typeahead.val();
			});
		</script>


	</tag:loggedin>

</body>
</html>
