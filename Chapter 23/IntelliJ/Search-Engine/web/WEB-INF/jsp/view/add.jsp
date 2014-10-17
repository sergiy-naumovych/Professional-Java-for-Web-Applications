<%--@elvariable id="added" type="java.lang.Long"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Forum Posts</title>
    </head>
    <body>
        <h2>Add Forum Posts</h2>
        [<a href="<c:url value="/search" />">search</a>]<br /><br />
        <c:if test="${added != null}">Added post with ID ${added}.<br /><br /></c:if>
        <form:form method="post" modelAttribute="addForm">
            User<br />
            <form:input path="user" /><br /><br />
            Title<br />
            <form:input path="title" size="50" /><br /><br />
            Body<br />
            <form:textarea path="body" cols="50" rows="6" /><br /><br />
            Keywords<br />
            <form:input path="keywords" size="50" /><br /><br />
            <input type="submit" value="Add Post" />
        </form:form>
    </body>
</html>
