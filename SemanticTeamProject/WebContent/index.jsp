<%@ page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<!-- Optional theme -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="bootstrap/3.0.0/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="bootstrap/font-awesome-4.0.3/css/font-awesome.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="bootstrap/3.0.0/js/jquery-1.10.2.min.js"></script>
<script src="bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="bootstrap/3.0.0/js/typeahead.min.js"></script>
<script src="bootstrap/3.0.0/js/jquery.animateNumber.js"></script>
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap-mod.css">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Sign in with Facebook example</title>
</head>
<%
	if (request.getParameter("debug") != null) {
		session.setAttribute("debug", true);
	} else {
		session.removeAttribute("debug");
	}
%>
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
			<div class="row" style="padding-bottom: 50px;">
				<div class="col-md-2 text-right">
					<img id="fbUserImage" src="images/defaultProfile.png"
						class="img-rounded">
				</div>
				<div class="col-md-3" style="margin-top: 4%;">
					<span id="userFirstname" class="header-name header-bold"></span> <span
						id="userLastname" class="header-name"></span>
				</div>
				<div class="col-md-2" style="margin-top: 2%;">
					<div id="total" style="display: none"></div>
				</div>

				<div class="col-md-3 text-right" style="margin-top: 4%;">
					<span id="celebrityFirstname"
						class="header-name header-show header-bold">First</span> <span
						id="celebrityLastname" class="header-name header-show">Last</span>
					<input id="search-celebrity" type="text" class="form-control"
						data-provide="typeahead" data-items="4"
						placeholder="Type in Celebrity ...">
				</div>
				<div class="col-md-2">
					<img id="celebrityImage" src="images/defaultProfile.png"
						class="img-rounded imageRight">
				</div>

			</div>



			<div id="date" style="display: none"></div>
			<div id="locations" style="display: none"></div>
			<div id="movies" style="display: none"></div>



		</div>


		<script type="text/javascript">
		
		
		var finaldata;
			$.ajaxSetup({
				cache : false
			});
			$(".header-show").click(function() {
				$(".header-show").hide();
				$(".twitter-typeahead").fadeIn(1000);

			});

			$('#search-celebrity').typeahead({
				name : 'celebrities',
				prefetch : 'fetchData?op=celebrityList',
				limit : 10,
			}).on(
					'typeahead:selected',
					function($e) {

						$(".twitter-typeahead").hide();
						var $typeahead = $(this);
						$.getJSON("fetchData?op=celebrity&comp=none&name="
								+ $typeahead.val(), function(data) {
							constructCel(data);
							$.getJSON("fetchData?op=celebrity&comp=date&name="
									+ $typeahead.val(), function(data) {
								createDynMatch("#date", data.ageCompResult,
										"Significant Dates", false);
								if (data.total != 0)
									finallyTotal1(data);
							});
							$.getJSON("fetchData?op=celebrity&comp=loc&name="
									+ $typeahead.val(), function(data) {
								createDynMatch("#locations",
										data.locationResult,
										"Visited Locations", false);
								if (data.total != 0)
									finallyTotal1(data);
							});
							$.getJSON("fetchData?op=celebrity&comp=genre&name="
									+ $typeahead.val(), function(data) {
								createDynMatch("#movies", data.movieResult,
										"Movie Genres", true);
								if (data.total != 0)
									finallyTotal1(data);
							});

						});
						$( "#content" ).click(function() {
							finallyTotal2(finaldata);
							$( "#content").unbind( "click" );
							});
					});
			function constructCel(data) {
				$(".preUser").removeClass("preUser");
				$("#celebrityImage").attr('src', data.celebrity.imageURL);
				$("#celebrityFirstname").html(data.celebrity.firstname);
				$("#celebrityLastname").html(data.celebrity.lastname);
				$(".header-show").show();
			}

			function createDynMatch(id, baseData, title, onebar) {

				$(id).empty();
				$(id).append(
						"<div class='row'><div class='col-md-2 attr_caption'>"
								+ title + ":</div></div>");
				$
						.each(
								baseData,
								function() {
									if (this.sum != 0) {
										var tt1 = "";
										if (this.HTMLo1)
											tt1 = this.HTMLo1;

										var tt2 = "";
										if (this.HTMLo2)
											tt2 = this.HTMLo2;

										$(id)
												.append(
														"<div class='row'><div class='col-md-1 attr_caption_small'>"
																+ this.desc1
																+ "</div><div class='col-md-2 attr' data-toggle='tooltip' data-html='true' data-original-title='"+tt1+"'>"
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
																					"<div class='eb progress-bar' style='width:0%'><span class='sr-only'>"
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
																"<div class='eb progress-bar' style='width:0%'><span class='sr-only'>"
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
														.width(this.value + "%");
											}
										}
										$(id)
												.children()
												.last()
												.append(
														"<div class='col-md-2 textAlignRight attr' data-toggle='tooltip' data-html='true' data-original-title='"+tt2+"'>"
																+ this.o2
																+ "</div><div class='col-md-1 attr_caption_small leftAlign'>"
																+ this.desc2
																+ "</div></div>");

									}
								});
			}
			function finallyTotal1(data) {
				finaldata=data;
				$("#total").empty();
				$("#total").append(
						"<span id='totalnumber' class='header-name header-bold bigger'>"
								+ 0 + "</span>");
				$("#total").append("<span class='header-name bigger'>%</span>");
				$("[data-toggle='tooltip']").tooltip();

				$(".progress-bar").removeClass('progress-bar').fadeIn(500,
						function() {
							$(this).addClass('progress-bar');
						});

				$("#date").slideDown(1000, function() {
					$("#locations").delay(500).slideDown(1000, function() {
						$("#movies").delay(500).slideDown(1000, function() {
							
						});
					});
				});

			}
			function finallyTotal2(data) {
				$(".progress-bar").removeClass('eb');
				$("#total").fadeIn(3000);
				$("#totalnumber").animateNumber(data.total);
				window.scrollTo(0, 0);
			}
			
			
			
			
			
		</script>

	</tag:loggedin>
	<div class="loadingModal">
		<i class="fa fa-spinner fa-spin fa-5x cent"></i>

	</div>
</body>
</html>
