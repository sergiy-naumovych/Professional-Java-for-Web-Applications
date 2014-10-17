<%--@elvariable id="searchForm" type="com.wrox.site.MainController.SearchForm"--%>
<%--@elvariable id="searchPerformed" type="boolean"--%>
<%--@elvariable id="results" type="org.springframework.data.domain.Page<com.wrox.site.repositories.SearchResult<com.wrox.site.entities.ForumPost>>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Search</title>
    </head>
    <body>
        <h2>Search</h2>
        <form:form method="get" modelAttribute="searchForm">
            <form:input path="query" size="40" /><input type="submit" name="Search" />
        </form:form><br />
        <br />

        <c:if test="${searchPerformed}">
            <c:choose>
                <c:when test="${results.numberOfElements == 0}">
                    <i>No search results.</i>
                </c:when>
                <c:otherwise>
                    Page&nbsp;::&nbsp;<c:forEach begin="1" end="${results.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${(i - 1) == results.number}">${i}</c:when>
                            <c:otherwise><a href="<c:url value="/search">
                                <c:param name="query" value="${searchForm.query}" />
                                <c:param name="paging.page" value="${i}" />
                                <c:param name="paging.size" value="10" />
                            </c:url>">${i}</a></c:otherwise>
                        </c:choose>&nbsp;
                    </c:forEach><br />
                    <c:forEach items="${results.content}" var="result">
                        ${result.entity.id}: <c:out value="${result.entity.title}"/><br />
                        Relevance: ${result.relevance}<br />
                        Author: ${result.entity.user.username} / Keywords: ${result.entity.keywords}<br />
                        <br />
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
    </body>
</html>
