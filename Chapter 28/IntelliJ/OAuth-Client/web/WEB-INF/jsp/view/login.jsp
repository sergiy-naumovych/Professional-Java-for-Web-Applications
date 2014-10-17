<!DOCTYPE html>
<html>
    <head>
        <title>Log In</title>
    </head>
    <body>
        <c:if test="${param.containsKey('loginFailed')}">
            <b class="errors">The username or password is not correct.</b><br />
        </c:if><c:if test="${param.containsKey('loggedOut')}">
            <i>You are now logged out.</i><br /><br />
        </c:if>
        Log In<br /><br />
        <c:url value="/login/submit" var="action" />
        <form:form method="post" modelAttribute="loginForm" autocomplete="off"
                   action="${action}">
            <form:label path="username">Username</form:label><br />
            <form:input path="username" autocomplete="off" /><br />
            <form:label path="password">Password</form:label><br />
            <form:password path="password" autocomplete="off" /><br />
            <input type="submit" value="Log In" />
        </form:form>
    </body>
</html>
