<!DOCTYPE html>
<html>
    <head>
        <title>Log In</title>
    </head>
    <body>
        <h2>Log In</h2>
        <c:if test="${param.containsKey('error')}">
            <b>Login failed. Please try again.</b><br /><br />
        </c:if>
        <c:if test="${param.containsKey('loggedOut')}">
            <b>You are now logged out.</b><br /><br />
        </c:if>
        <form method="post" action="<c:url value="/login" />">
            Username<br />
            <input type="text" name="username" /><br /><br />

            Password<br />
            <input type="password" name="password" /><br /><br />

            <input type="submit" name="Log In" />
        </form>
    </body>
</html>
