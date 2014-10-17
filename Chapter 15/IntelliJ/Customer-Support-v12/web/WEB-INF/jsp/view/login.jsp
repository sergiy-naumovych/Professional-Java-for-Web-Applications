<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="com.wrox.site.AuthenticationController.Form"--%>
<spring:message code="title.login" var="loginTitle" />
<template:loggedOut htmlTitle="${loginTitle}" bodyTitle="${loginTitle}">
    <spring:message code="message.login.instruction" /><br /><br />
    <c:if test="${loginFailed}">
        <b><spring:message code="error.login.failed" /></b><br /><br />
    </c:if>
    <form:form method="post" modelAttribute="loginForm">
        <form:label path="username"><spring:message code="field.login.username" /></form:label><br />
        <form:input path="username" /><br /><br />
        <form:label path="password"><spring:message code="field.login.password" /></form:label><br />
        <form:password path="password" /><br /><br />
        <input type="submit" value="<spring:message code="field.login.submit" />" />
    </form:form>
</template:loggedOut>
