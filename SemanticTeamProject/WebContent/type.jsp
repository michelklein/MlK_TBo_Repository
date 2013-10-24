<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Bootstrap typeahead example by w3resource</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="bootstrap/js/jquery-1.10.2.min.js"></script>
<script src="bootstrap/js/typeahead.js"></script>
</head>
<body>
	<div class="well">
		<input  type="text" data-provide="typeahead" data-items="4">
	</div>
	
	<script type="text/javascript">
	$('input').typeahead({
		name: 'States',
		local: ["Ahmedabad","Akola","Asansol","Aurangabad","Bangaluru","Baroda","Belgaon","Berhumpur","Calicut","Chennai","Chapra","Cherapunji"]
		});
	</script>
</body>
</html>
