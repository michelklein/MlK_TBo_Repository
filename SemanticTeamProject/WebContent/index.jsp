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
			$
					.getJSON(
							"fetchData?op=facebook",
							function(data) {
								console.log("Get FB Data");
								$("#content").show();
								$("#fbUserImage").attr('src', data.imageURL);
								$("#userFirstname").html(data.firstname);
								$("#userLastname").html(data.lastname);
								$("#attr_age_user")
										.html(data.formattedBirthday);
								$("#attr_hometown_user").html(
										data.formattedHometown);

								$("#movies")
										.append(
												"<div class='row'><div class='col-md-1 attr_caption'>Interests:</div></div>");
								$.each(data.allGenres, function() {
									buildDyn("#movies", this);
								});
								$("#locations")
										.append(
												"<div class='row'><div class='col-md-1 attr_caption'>Locations:</div></div>");
								$.each(data.locations, function() {
									buildDyn("#locations",
											this.formattedLocation);
								});

								console.debug(data);
							});

			function buildDyn(id, data) {
				$(id)
						.append(
								"<div class='row'><div class='col-md-1 attr_caption'></div><div class='col-md-2'>"
										+ data
										+ "</div><div class='col-md-6'><div class='progress' data-toggle='tooltip'	data-html='true' data-original-title='Default tooltip'></div>");
				$(id)
						.append(
								"<div id='attr_loc4_celebrity' class='col-md-2 textAlignRight'></div>");

			}
		</script>

		<div id="header">


		

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
							<span id="celebrityLastname" class="header-name"></span>
							
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
							
							<img
								id="celebrityImage" src="images/defaultProfile.png"
								class="img-rounded imageRight">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1 attr_caption">Birthdate:</div>
				<div id="attr_age_user" class="col-md-2"></div>
				<div class="col-md-6">

					<div class="progress progress_age" data-toggle="tooltip"
						data-html="true"></div>
				</div>
				<div id="attr_age_celebrity" class="col-md-2 textAlignRight"></div>
			</div>



			<div id="locations"></div>
			<div id="movies"></div>



		</div>
		</div>

		<script type="text/javascript">
			$.ajaxSetup({
				cache : false
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
						var $typeahead = $(this);
						$.getJSON("fetchData?op=celebrity&name="
								+ $typeahead.val(), function(data) {
							constructMatching(data);
						});

					});

			function constructMatching(data) {
				var json = data;
				console.debug(data);
				$("#celebrityImage").attr('src', json.celebrity.imageURL);
				$("#celebrityFirstname").html(json.celebrity.firstname);
				$("#celebrityLastname").html(json.celebrity.lastname);
				$("#attr_age_celebrity").html(json.celebrity.formattedBirthday);

				$.each(json.ageCompResult.subresults, function() {
					if (this.value != 0) {
						$(".progress_age").append(
								"<div class='progress-bar'  style='width:0%'><span class='sr-only'>"
										+ this.value + "%" + "</span></div>");
						$(".progress_age div:last").width(this.value + "%");
					}
				});
				$(".progress_age").attr('data-original-title',
						json.ageCompResult.HTML);

				createDynMatch("#movies", json.movieResult);
				createDynMatch("#locations", json.locationResult);
				function createDynMatch(id, baseData) {

					$(id).empty();
					$(id)
							.append(
									"<div class='row'><div class='col-md-1 attr_caption'>Interests:</div></div>");
					$
							.each(
									baseData,
									function() {
										if (this.sum != 0) {
											$(id)
													.append(
															"<div class='row'><div class='col-md-1 attr_caption'></div><div class='col-md-2'>"
																	+ this.o1
																	+ "</div><div class='col-md-6'><div class='progress' data-toggle='tooltip' data-html='true' data-original-title='"+this.HTML+"'></div></div>");
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
		<!-- Place at bottom of page -->
	</div>
</body>
</html>
