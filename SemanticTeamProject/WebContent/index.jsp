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
			if (navigator.geolocation) {
				$("body").addClass("loading");
				navigator.geolocation.getCurrentPosition(success, error);
			} else {
				alert("Not Supported!");
			}

			function success(position) {
				fetchUserData(position.coords.latitude,
						position.coords.longitude);
			}
			function error(msg) {
				console.log(typeof msg == 'string' ? msg : "error");
			}
			$(document).ajaxStart(function() {
				console.log("ajaxStart");
				$("body").addClass("loading");
			});
			$(document).ajaxComplete(function() {
				$("body").removeClass("loading");
			});

			function fetchUserData(lati, longi) {
				$.getJSON("fetchData?op=facebook&lati=" + lati + "&longi="
						+ longi, function(data) {
					console.log("Get FB Data");
					$("#content").show();
					$("#fbUserImage").attr('src', data.imageURL);
					$("#userFirstname").html(data.firstname);
					$("#userLastname").html(data.lastname);

					console.debug(data);
				});

			}
		</script>

		<!-- <div id="header">




			<h1 class="headerText">
				Celebritie comparison <small> You and your favourites</small>
			</h1>
		</div>
 -->
		<div id="content" class="preUser">
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

							<span id="celebrityFirstname"
								class="header-name header-show header-bold">First</span> <span
								id="celebrityLastname" class="header-name header-show">Last</span>

							<input id="search-celebrity" type="text" class="form-control"
								data-provide="typeahead" data-items="4"
								placeholder="Type in your celebrity ..."> <img
								id="celebrityImage" src="images/defaultProfile.png"
								class="img-rounded imageRight">
						</div>
					</div>
				</div>
				<div class="col-md-1"></div>
			</div>



			<div id="date"></div>
			<div id="locations"></div>
			<div id="movies"></div>



		</div>


		<script type="text/javascript">
			$.ajaxSetup({
				cache : false
			});
			$(".header-show").click(function() {
				$(".header-show").hide();
				$(".twitter-typeahead").fadeIn(1000);

			});

			$('#search-celebrity').typeahead(
					{
						name : 'celebrities',
						prefetch : 'fetchData?op=celebrityList&'
								+ new Date().getTime(),
						limit : 20,
					}).on(
					'typeahead:selected',
					function($e) {

						$(".twitter-typeahead").hide();
						var $typeahead = $(this);
						$.getJSON("fetchData?op=celebrity&name="
								+ $typeahead.val(), function(data) {
							constructMatching(data);
						});

					});

			function constructMatching(data) {
				$(".preUser").removeClass("preUser");
				var json = data;
				console.debug(data);
				$("#celebrityImage").attr('src', json.celebrity.imageURL);
				$("#celebrityFirstname").html(json.celebrity.firstname);
				$("#celebrityLastname").html(json.celebrity.lastname);

				$(".header-show").show();

				$("#attr_age_celebrity").html(json.celebrity.formattedBirthday);

				createDynMatch("#locations", json.locationResult, "Locations",
						false);
				createDynMatch("#movies", json.movieResult, "Interests", true);

				createDynMatch("#date", json.ageCompResult, "Birthdate", false);

				function createDynMatch(id, baseData, title, onebar) {

					$(id).empty();
					$(id).append(
							"<div class='row'><div class='col-md-1 attr_caption'>"
									+ title + ":</div></div>");
					$
							.each(
									baseData,
									function() {
										if (this.sum != 0) {
											var tt = "";
											if (this.HTMLo1)
												tt = this.HTMLo1;

											$(id)
													.append(
															"<div class='row'><div class='col-md-1 attr_caption_small'>"
																	+ this.desc1
																	+ "</div><div class='col-md-2 attr' data-toggle='tooltip' data-html='true' data-original-title='"+tt+"'>"
																	+ this.o1
																	+ "</div><div class='col-md-6'><div class='progress' data-toggle='tooltip' data-html='true' data-original-title='"+this.HTML+"'></div>");
											if (!onebar) {

												$
														.each(
																this.subresults,
																function() {
																	if (this.value != 0) {
																		$(id)
																				.children()
																				.last()
																				.children()
																				.last()
																				.children()
																				.last()
																				.append(
																						"<div class='progress-bar' style='width:0%'><span class='sr-only'>"
																								+ this.value
																								+ "%"
																								+ "</span></div>");
																		$(id)
																				.children()
																				.last()
																				.children()
																				.last()
																				.children()
																				.last()
																				.children()
																				.last()
																				.width(
																						this.value
																								+ "%");

																	}
																});
											} else {
												if (this.value != 0) {
													$(id)
															.children()
															.last()
															.children()
															.last()
															.children()
															.last()
															.append(
																	"<div class='progress-bar' style='width:0%'><span class='sr-only'>"
																			+ this.value
																			+ "%"
																			+ "</span></div>");
													$(id)
															.children()
															.last()
															.children()
															.last()
															.children()
															.last()
															.children()
															.last()
															.width(
																	this.value
																			+ "%");
												}
											}
											$(id)
													.children()
													.last()
													.append(
															"<div class='col-md-2 textAlignRight attr' data-toggle='tooltip' data-html='true' data-original-title='"+this.HTMLo2+"'>"
																	+ this.o2
																	+ "</div><div class='col-md-1 attr_caption_small leftAlign'>"
																	+ this.desc2
																	+ "</div></div>");
										}
									});
				}

				if (json.ageCompResult.value == 0) {
					$("#progress_age > .sr-only").addClass("noResult");
				} else {
					$("#progress_age > .sr-only").removeClass("noResult");
				}
				$("[data-toggle='tooltip']").tooltip();

				$(".progress-bar").each(function() {
					$(this).removeClass('progress-bar').fadeIn(500, function() {
						$(this).addClass('progress-bar');
					});

				});
			}
		</script>

	</tag:loggedin>
	<div class="loadingModal">
		<span class="glyphicon glyphicon-refresh centBig"></span>
	</div>
</body>
</html>
