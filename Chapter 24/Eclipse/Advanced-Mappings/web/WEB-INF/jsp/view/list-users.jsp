<%--@elvariable id="users" type="java.util.List<com.wrox.site.entities.User>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Attribute Converters - Users</title>
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
                <th>Username</th>
                <th>Date Joined</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.dateJoined}</td>
                </tr>
            </c:forEach>
        </table>
        <br />
        <b>Add a User</b>
        <form method="post">
            <label>Username<br />
                <input type="text" name="username" /></label>
            <br />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
