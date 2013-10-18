<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
<head>
 <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
 
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="bootstrap/js/jquery-1.10.2.min.js"></script>
 
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
  
 

</tag:loggedin>
</body>
</html>