<%--@elvariable id="publishers" type="java.util.List<com.wrox.site.entities.Publisher>"--%>
<%--@elvariable id="authors" type="java.util.List<com.wrox.site.entities.Author>"--%>
<%--@elvariable id="books" type="java.util.List<com.wrox.site.entities.Book>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Entities</title>
    </head>
    <body>

        <b>Publishers</b>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Address</th>
            </tr>
            <c:forEach items="${publishers}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td><c:out value="${p.name}" /></td>
                    <td><c:out value="${p.address}" /></td>
                </tr>
            </c:forEach>
        </table><br />

        <b>Authors</b>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email Address</th>
            </tr>
            <c:forEach items="${authors}" var="a">
                <tr>
                    <td>${a.id}</td>
                    <td><c:out value="${a.name}" /></td>
                    <td><c:out value="${a.emailAddress}" /></td>
                </tr>
            </c:forEach>
        </table><br />

        <b>Books</b>
        <table>
            <tr>
                <th>ID</th>
                <th>ISBN</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Price</th>
            </tr>
            <c:forEach items="${books}" var="b">
                <tr>
                    <td>${b.id}</td>
                    <td><c:out value="${b.isbn}" /></td>
                    <td><c:out value="${b.title}" /></td>
                    <td><c:out value="${b.author}" /></td>
                    <td><c:out value="${b.publisher}" /></td>
                    <td><c:out value="${b.price}" /></td>
                </tr>
            </c:forEach>
        </table><br />

        <form method="post" action="<c:url value="/" />">
            <input type="submit" value="Add More Entities" />
        </form>
    </body>
</html>
