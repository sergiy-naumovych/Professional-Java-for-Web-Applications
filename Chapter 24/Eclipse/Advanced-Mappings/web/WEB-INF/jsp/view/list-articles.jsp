<%--@elvariable id="articles" type="java.util.List<com.wrox.site.entities.NewsArticle>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Mapped Superclasses and Versioning - News Articles</title>
        <style type="text/css">
            table { border-collapse: collapse; }
            table th, table td { padding: 5px; }
        </style>
    </head>
    <body>
        <a href="<c:url value="/" />">Go Back</a><br />
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Date Created</th>
                <th>Date Modified</th>
                <th>Version</th>
                <th>Title</th>
                <th>Content Length</th>
            </tr>
            <c:forEach items="${articles}" var="article">
                <tr>
                    <td>${article.id}</td>
                    <td>${article.dateCreated}</td>
                    <td>${article.dateModified}</td>
                    <td>${article.version}</td>
                    <td>${article.title}</td>
                    <td>${fn:length(article.content)}</td>
                </tr>
            </c:forEach>
        </table>
        <br />
        <b>Add a News Article</b>
        <form:form method="post" modelAttribute="newsArticleForm">
            <form:label path="title">Title: </form:label>
            <form:input path="title" size="40" /><br />
            <form:label path="content">Content:</form:label><br />
            <form:textarea path="content" rows="5" cols="40" /><br />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
