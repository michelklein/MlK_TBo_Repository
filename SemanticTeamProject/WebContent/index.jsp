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
				$("#attr_hometown_user").html(json.formattedHometown);
				console.debug(data);
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
				<div class="col-md-5 col-md-offset-1">
					<div id="fbUser">
						<div class="panel-header-own">
							<img id="fbUserImage" src="images/defaultProfile.png"
								class="img-rounded"> <span id="userFirstname"
								class="header-name header-bold"></span> <span id="userLastname"
								class="header-name"></span>
						</div>
					</div>
				</div>
				<div class="col-md-5">
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
				<div id="attr_caption" class="col-md-1">Age:</div>
				<div id="attr_age_user" class="col-md-2"></div>
				<div class="col-md-6">

					<div class="progress progress_age" data-toggle="tooltip"
						data-html="true">
						<div id="progress_age" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_cent" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_dec" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_year" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_month" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_week" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_dayOfWeek" class="progress-bar">
							<span class="sr-only"></span>
						</div>
						<div id="progress_day" class="progress-bar">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_age_celebrity" class="col-md-2 textAlignRight"></div>
			</div>
			<div class="row">
				<div id="attr_caption" class="col-md-1">Hometown:</div>
				<div id="attr_hometown_user" class="col-md-2"></div>
				<div class="col-md-6">
					<div class="progress progress_hometown" data-toggle="tooltip"
						data-html="true" data-original-title="Default tooltip">
						<div id="progress_hometown" class="progress-bar"
							role="progressbar" aria-valuenow="60" aria-valuemin="0"
							aria-valuemax="100" style="width: 0%;">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_hometown_celebrity" class="col-md-2 textAlignRight"></div>
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
							//AgeProgress
							$("#progress_age").width(
									json.ageCompResult.subresults[0].value - 2
											+ "%");
							$("#progress_age > .sr-only").html(
									json.ageCompResult.subresults[0].value - 2
											+ "%");
							if (json.ageCompResult.subresults[0].value == 0)
								$("#progress_age").remove();
							$("#progress_cent").width(
									json.ageCompResult.subresults[1].value - 2
											+ "%");
							$("#progress_cent > .sr-only").html(
									json.ageCompResult.subresults[1].value - 2
											+ "%");
							if (json.ageCompResult.subresults[1].value == 0)
								$("#progress_cent").remove();
							$("#progress_dec").width(
									json.ageCompResult.subresults[2].value - 2
											+ "%");
							$("#progress_dec > .sr-only").html(
									json.ageCompResult.subresults[2].value - 2
											+ "%");
							if (json.ageCompResult.subresults[2].value == 0)
								$("#progress_dec").remove();
							$("#progress_year").width(
									json.ageCompResult.subresults[3].value - 2
											+ "%");
							$("#progress_year > .sr-only").html(
									json.ageCompResult.subresults[3].value - 2
											+ "%");
							if (json.ageCompResult.subresults[3].value == 0)
								$("#progress_year").remove();
							$("#progress_month").width(
									json.ageCompResult.subresults[4].value - 2
											+ "%");
							$("#progress_month > .sr-only").html(
									json.ageCompResult.subresults[4].value - 2
											+ "%");
							if (json.ageCompResult.subresults[4].value == 0)
								$("#progress_month").remove();
							$("#progress_week").width(
									json.ageCompResult.subresults[5].value - 2
											+ "%");
							$("#progress_week > .sr-only").html(
									json.ageCompResult.subresults[5].value - 2
											+ "%");
							if (json.ageCompResult.subresults[5].value == 0)
								$("#progress_week").remove();
							$("#progress_dayOfWeek").width(
									json.ageCompResult.subresults[6].value - 2
											+ "%");
							$("#progress_dayOfWeek > .sr-only").html(
									json.ageCompResult.subresults[6].value - 2
											+ "%");
							if (json.ageCompResult.subresults[6].value == 0)
								$("#progress_dayOfWeek").remove();
							$("#progress_day").width(
									json.ageCompResult.subresults[7].value - 2
											+ "%");
							$("#progress_day > .sr-only").html(
									json.ageCompResult.subresults[7].value - 2
											+ "%");
							if (json.ageCompResult.subresults[7].value == 0)
								$("#progress_day").remove();

							$(".progress_age").attr('data-original-title',
									json.ageCompResult.HTML);
							$("#attr_hometown_celebrity").html(
									json.celebrity.formattedHometown);
							$("#progress_hometown").width(
									json.hometownResult.value + "%");
							$("#progress_hometown > .sr-only").html(
									json.hometownResult.value + "%");
							$(".progress_hometown").attr('data-original-title',
									json.hometownResult.HTML);

							if (json.ageCompResult.value == 0) {
								$("#progress_age > .sr-only").addClass(
										"noResult");
							} else {
								$("#progress_age > .sr-only").removeClass(
										"noResult");
							}

						});

						$("[data-toggle='tooltip']").tooltip();
					});
		</script>

	</tag:loggedin>
	<div class="loadingModal">
		<!-- Place at bottom of page -->
	</div>
</body>
</html>
