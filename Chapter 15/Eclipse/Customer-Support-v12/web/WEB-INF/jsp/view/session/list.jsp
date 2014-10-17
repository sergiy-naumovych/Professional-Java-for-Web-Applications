<%--@elvariable id="timestamp" type="long"--%>
<%--@elvariable id="numberOfSessions" type="int"--%>
<%--@elvariable id="sessionList" type="java.util.List<javax.servlet.http.HttpSession>"--%>
<spring:message code="title.sessionList" var="sessionTitle" />
<template:basic htmlTitle="${sessionTitle}" bodyTitle="${sessionTitle}">
    <spring:message code="message.sessionList.instruction">
        <spring:argument value="${numberOfSessions}" />
    </spring:message><br /><br />
    <c:forEach items="${sessionList}" var="s">
        <c:out value="${s.id} - ${s.getAttribute('com.wrox.user.principal')}" />
        <c:if test="${s.id == pageContext.session.id}">
            (<spring:message code="message.sessionList.you" />)</c:if>
        - <spring:message code="message.sessionList.lastActive" />
        ${wrox:timeIntervalToString(timestamp - s.lastAccessedTime)} ago<br />
    </c:forEach>
</template:basic>
