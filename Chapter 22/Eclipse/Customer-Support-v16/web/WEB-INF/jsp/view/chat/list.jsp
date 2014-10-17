<%--@elvariable id="sessions" type="java.util.List<com.wrox.site.chat.ChatSession>"--%>
<spring:message code="title.chatList" var="chatTitle" />
<template:basic htmlTitle="${chatTitle}" bodyTitle="${chatTitle}">
    <c:choose>
        <c:when test="${fn:length(sessions) == 0}">
            <i><spring:message code="message.chatList.none" /></i>
        </c:when>
        <c:otherwise>
            <spring:message code="message.chatList.instruction" />:<br /><br />
            <c:forEach items="${sessions}" var="s">
                <a href="javascript:void 0;"
                   onclick="join(${s.sessionId});">${s.customerUsername}</a><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript" language="javascript">
        var join = function(id) {
            postInvisibleForm(
                    '<c:url value="/chat/join/{id}" />'.replace('{id}', id), { }
            );
        };
    </script>
</template:basic>
