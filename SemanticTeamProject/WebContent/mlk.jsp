<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Example of Twitter Bootstrap Tooltip</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="bootstrap/3.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="bootstrap/3.0.0/css/bootstrap.min.css">
<script type="text/javascript">
$(function () {
    $(".tooltip-examples a").tooltip({
        placement : 'top'
    });
});
</script>
<style type="text/css">
	.bs-example{
    	margin: 60px;
    }
</style>
</head>
<body>
<div class="bs-example"> 
    <ul class="tooltip-examples inline">
        <li><a href="#" data-toggle="tooltip" data-original-title="Default tooltip">Tooltip</a></li>
        <li><a href="#" data-toggle="tooltip" data-original-title="Another tooltip">Another tooltip</a></li>
        <li><a href="#" data-toggle="tooltip" data-original-title="A much longer tooltip to demonstrate the max-width of the Bootstrp tooltip.">Large tooltip</a></li>
        <li><a href="#" data-toggle="tooltip" data-original-title="The last tip!">Last tooltip</a></li>
    </ul>
</div>
</body>
</html>                                		