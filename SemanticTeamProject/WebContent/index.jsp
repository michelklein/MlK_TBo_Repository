<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Sign in with Facebook example</title>
</head>
<body>
<tag:notloggedin>
  <a href="signin">Sign in with Facebook</a>
</tag:notloggedin>
<tag:loggedin>

  <h1>You and ...</h1>
  
  <table border="1">
<tr>
<th>Facebook</th>
<th>OpenData</th>
</tr>
<tr>
<td>${c.getFbp().getFirstname()}</td>
<td>?</td>
</tr>
<tr>
<td>${c.getFbp().getName()}</td>
<td>?</td>
</tr>
</table>
  
 
  <form action="./post" method="post">
    <textarea cols="80" rows="2" name="message"></textarea>
    <input type="submit" name="post" value="statuses" />
  </form>
<a href="./logout">logout</a>
</tag:loggedin>
</body>
</html>