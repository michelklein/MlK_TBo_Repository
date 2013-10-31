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
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="bootstrap/3.0.0/js/typeahead.js"></script>

<link rel="stylesheet"
	href="bootstrap/3.0.0/css/typeahead.js-bootstrap.css">
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap-mod.css">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Sign in with Facebook example</title>
</head>
<body>

	<tag:notloggedin>
		<div class="loginScreen">
			<button type="button" class="btn btn-default btn-lg loginButton"
				onclick="location.href='./signin'">
				<span class="glyphicon glyphicon-thumbs-up"></span> sign in with
				Facebook
			</button>
		</div>
	</tag:notloggedin>

	<tag:loggedin>

		<script type="text/javascript" language="javascript">
			$(document).ajaxStart(function() {
				console.log("ajaxStart");
				$("body").addClass("loading");
			});

			$(document).ajaxComplete(function() {
				console.log("ajaxComplete");
				$("body").removeClass("loading");
			});
			$.getJSON("fetchData?op=facebook", function(data) {
				console.log("Get FB Data");
				var json = data;
				$("#content").show();
				$("#fbUserImage").attr('src', json.imageURL);
				$("#userFirstname").html(json.firstname);
				$("#userLastname").html(json.lastname);
				$("#attr_age_user").html(json.formattedBirthday);
				console.debug(data);
			});
			$(function() {
				$("[data-toggle='tooltip']").tooltip();
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

			<h1 class="headerText">
				Celebritie comparison <small> You and your favourites</small>
			</h1>
		</div>

		<div id="content">
			<div class="row">
				<div class="col-md-4 col-md-offset-1">
					<div id="fbUser">
						<div class="panel-header-own">
							<img id="fbUserImage" src="images/defaultProfile.png"
								class="img-rounded"> <span id="userFirstname"
								class="header-name header-bold"></span> <span id="userLastname"
								class="header-name"></span>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-md-offset-2">
					<div id="fbUser">
						<div class="panel-header-own floatRight">
							<span id="celebrityFirstname" class="header-name header-bold"></span>
							<span id="celebrityLastname" class="header-name"></span> <img
								id="celebrityImage" src="images/defaultProfile.png"
								class="img-rounded imageRight">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div id="attr_age_caption" class="col-md-1">Age:</div>
				<div id="attr_age_user" class="col-md-2"></div>
				<div class="col-md-6">
					<div class="progress">
						<div id="progress_age" class="progress-bar" role="progressbar"
							aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
							style="width: 0%;" data-toggle="tooltip" data-html="true"
							data-original-title="Default tooltip">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_age_celebrity" class="col-md-2 textAlignRight"></div>
			</div>
		</div>

		<script type="text/javascript">
			$('#search-celebrity').typeahead({
				name : 'celebrities',
				prefetch : 'fetchData?op=celebrityList',
				limit : 10,
			}).on(
					'typeahead:selected',
					function($e) {
						var $typeahead = $(this);
						$.getJSON("fetchData?op=celebrity&name="
								+ $typeahead.val(), function(data) {
							var json = data;
							console.debug(data);
							$("#celebrityImage").attr('src',
									json.celebrity.imageURL);
							$("#celebrityFirstname").html(
									json.celebrity.firstname);
							$("#celebrityLastname").html(
									json.celebrity.lastname);
							$("#attr_age_celebrity").html(
									json.celebrity.formattedBirthday);
							$("#progress_age").width(
									json.ageCompResult.value + "%");
							$("#progress_age > .sr-only").html(
									json.ageCompResult.value + "%");
							$("#progress_age").attr('data-original-title',
									json.ageCompResult.HTML);
							if(json.ageCompResult.value == 0) {
								$("#progress_age > .sr-only").addClass("noResult");
							}
						});
					});
		</script>

	</tag:loggedin>
	<div class="loadingModal">
		<!-- Place at bottom of page -->
	</div>
</body>
</html>
