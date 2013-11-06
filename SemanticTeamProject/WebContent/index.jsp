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
				$("#content").show();
				$("#fbUserImage").attr('src', data.imageURL);
				$("#userFirstname").html(data.firstname);
				$("#userLastname").html(data.lastname);
				$("#attr_age_user").html(data.formattedBirthday);
				$("#attr_hometown_user").html(data.formattedHometown);
				
				
				
				$.each(data.allGenres, function() {
					$("#movies").append("<div class='row'><div class='col-md-1'>Movies:</div><div class='col-md-2'>"+this+"</div><div class='col-md-6'><div class='progress' data-toggle='tooltip'	data-html='true' data-original-title='Default tooltip'></div>");
					$("#movies").append("<div id='attr_loc4_celebrity' class='col-md-2 textAlignRight'></div>");
				});
				
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
						data-html="true"></div>
				</div>
				<div id="attr_age_celebrity" class="col-md-2 textAlignRight"></div>
			</div>


			<!-- Location -->
			<div class="row">
				<div id="attr_caption" class="col-md-1">Location:</div>
				<div id="attr_loc1_user" class="col-md-2"></div>
				<div class="col-md-6">
					<div class="progress progress_loc1" data-toggle="tooltip"
						data-html="true" data-original-title="Default tooltip">
						<div id="progress_loc1" class="progress-bar">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_loc1_celebrity" class="col-md-2 textAlignRight"></div>
			</div>
			<div class="row">
				<div id="attr_loc2_user" class="col-md-3"></div>
				<div class="col-md-6">
					<div class="progress progress_loc2" data-toggle="tooltip"
						data-html="true" data-original-title="Default tooltip">
						<div id="progress_loc2" class="progress-bar">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_loc2_celebrity" class="col-md-2 textAlignRight"></div>
			</div>
			<div class="row">
				<div id="attr_loc3_user" class="col-md-3"></div>
				<div class="col-md-6">
					<div class="progress progress_loc3" data-toggle="tooltip"
						data-html="true" data-original-title="Default tooltip">
						<div id="progress_loc3" class="progress-bar">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_loc3_celebrity" class="col-md-2 textAlignRight"></div>
			</div>
			<div class="row">
				<div id="attr_loc_user" class="col-md-3"></div>
				<div class="col-md-6">
					<div class="progress progress_loc4" data-toggle="tooltip"
						data-html="true" data-original-title="Default tooltip">
						<div id="progress_loc4" class="progress-bar">
							<span class="sr-only"></span>
						</div>
					</div>
				</div>
				<div id="attr_loc4_celebrity" class="col-md-2 textAlignRight"></div>
			</div>








			<!-- Movies -->
			<div id="movies"></div>
			<div class="row">
				<div id="attr_caption" class="col-md-1">Movies:</div>
				<div id="attr_movie1_user" class="col-md-2"></div>
				<div class="col-md-6" id="progress_movie">
				</div>
			</div>
			<div id="attr_movie1_celebrity" class="col-md-2 textAlignRight"></div>
		</div>
		</div>

		<script type="text/javascript">
		  $.ajaxSetup({ cache: false });
		  
		  
			$('#search-celebrity').typeahead({
				name : 'celebrities',
				prefetch : 'fetchData?op=celebrityList&' + new Date().getTime(),
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

				$("#attr_loc1_celebrity").html(json.celebrity.formattedloc);
				$("#progress_loc1").width(json.locResult.value + "%");
				$("#progress_loc1 > .sr-only").html(json.locResult.value + "%");
				$(".progress_loc1").attr('data-original-title',
						json.locResult.HTML);

				
				
				
				
			

				
				$.each(json.movieResult, function() {
					$("#progress_movie").append("<div class='progress' data-toggle='tooltip' data-html='true' data-original-title='Default tooltip'></div>");
				$.each(this.subresults, function() {
					if (this.value != 0){
						$("#progress_movie").children().last().append(
								"<div class='progress-bar' style='width:0%'><span class='sr-only'>"
										+ this.value + "%" + "</span></div>");
					$("#progress_movie").children().last().children().last().width(this.value + "%");}
				});
				});

				$(".progress_movie1").attr('data-original-title',
						json.movieResult.HTML);

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
