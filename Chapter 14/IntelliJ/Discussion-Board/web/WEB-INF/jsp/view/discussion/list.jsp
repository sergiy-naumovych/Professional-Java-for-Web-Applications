<%--@elvariable id="discussions" type="java.util.List<com.wrox.site.entity.Discussion>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Discussion List</title>
    </head>
    <body>
        <h2>Discussions</h2>
        [<a href="<c:url value="/discussion/create" />">new discussion</a>]<br />
        <br />
        <c:choose>
            <c:when test="${fn:length(discussions) > 0}">
                <c:forEach items="${discussions}" var="discussion">
                    <a href="<c:url value="/discussion/${discussion.id}/${discussion.uriSafeSubject}" />">
                        <c:out value="${discussion.subject}" /></a><br />
                    (<c:out value="${discussion.user}" />)<br/>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <i>There are no discussions yet.</i>
            </c:otherwise>
        </c:choose>
    </body>
</html>
