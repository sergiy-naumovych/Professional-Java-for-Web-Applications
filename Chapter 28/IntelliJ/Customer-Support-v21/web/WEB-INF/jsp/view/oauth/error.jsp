<%--@elvariable id="error" type="org.springframework.security.oauth2.common.exceptions.OAuth2Exception"--%>
<spring:message code="title.oauthError" var="oauthError" />
<template:basic htmlTitle="${oauthError}" bodyTitle="${oauthError}">
    <spring:message code="error.oauth.clientAuthorizationError" /><br />
    <br />
    ${error.summary}
</template:basic>
