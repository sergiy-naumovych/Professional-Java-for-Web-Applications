<!DOCTYPE html>
<html>
    <head>
        <title>Create Discussion</title>
    </head>
    <body>
        <h2>Create Discussion</h2>
        [<a href="<c:url value="/discussion/list" />">return to list</a>]<br />
        <br />
        <form:form method="post" modelAttribute="discussionForm">
            <form:label path="user">Your Email</form:label><br />
            <form:input path="user" type="email" /><br />
            <br />
            <form:label path="subject">Subject</form:label><br />
            <form:input path="subject" /><br />
            <br />
            <form:label path="message">Message</form:label><br />
            <form:textarea path="message" cols="40" rows="5" /><br />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
