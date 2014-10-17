<%--@elvariable id="discussion" type="com.wrox.site.entity.Discussion"--%>
<%--@elvariable id="replies" type="java.util.List<com.wrox.site.entity.Reply>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Discussion: <c:out value="${discussion.subject}" /></title>
    </head>
    <body>
        <h2>Discussion: <c:out value="${discussion.subject}" /></h2>
        [<a href="<c:url value="/discussion/list" />">return to list</a>]<br />
        <i>User: <c:out value="${discussion.user}" /> / Created: <c:out value="${discussion.created}" /></i>
        <br />
        <c:out value="${discussion.message}" /><br />
        <br />
        <c:choose>
            <c:when test="${fn:length(replies) > 0}">
                <c:forEach items="${replies}" var="reply" varStatus="i">
                    <b>Reply #${i.count}</b><i> - <c:out value="${reply.user}" /> -
                    <c:out value="${reply.created}" /></i><br />
                    <c:out value="${reply.message}" /><br />
                    <br />
                </c:forEach>
            </c:when>
            <c:otherwise>
                <i>There are no replies yet.</i><br />
                <br />
            </c:otherwise>
        </c:choose>
        <b>Post Reply</b><br />
        <br />
        <c:set var="action"><c:url value="/discussion/${discussion.id}/reply" /></c:set>
        <form:form method="post" action="${action}" modelAttribute="replyForm">
            <form:label path="user">Your Email</form:label><br />
            <form:input path="user" type="email" /><br />
            <br />
            <form:label path="message">Message</form:label><br />
            <form:textarea path="message" cols="40" rows="5" /><br />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
