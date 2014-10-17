<%--@elvariable id="accounts" type="java.util.List<com.wrox.site.Account>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Accounts</title>
    </head>
    <body>
        <h2>Accounts</h2>
        <c:forEach items="${accounts}" var="account">
            <c:out value="${account.name}" /><br />
        </c:forEach>
    </body>
</html>
