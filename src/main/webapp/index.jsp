
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Login page</title>
</head>
<body>

<form method="post"  action="main">
  <h2>Sign In:</h2>
   <input type="hidden" name="sessionId" id="sessionId" value="0"/>
  <input type="text" id="login-input" name="login" /></br>
  <input type="password" id="pass-input" name="pass" /></br>
  <input type="submit" id="say-hello-button" value="Sign In" />

</form>
</body>
</html>