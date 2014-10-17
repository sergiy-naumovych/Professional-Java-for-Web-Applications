<%--@elvariable id="applicants" type="java.util.List<com.wrox.site.entities.Applicant>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Embeddable Types - People, Addresses, Postal Codes, and Phone Numbers</title>
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
                <th>First Name</th>
                <th>Last Name</th>
                <th>Citizen?</th>
            </tr>
            <c:forEach items="${applicants}" var="applicant">
                <tr>
                    <td>${applicant.id}</td>
                    <td>${applicant.firstName}</td>
                    <td>${applicant.lastName}</td>
                    <td>${applicant.citizen}</td>
                </tr>
            </c:forEach>
        </table>
        <br />
        <b>Add an Applicant</b>
        <form:form method="post" modelAttribute="applicantForm">
            <form:label path="firstName">First Name: </form:label>
            <form:input path="firstName" /><br />
            <form:label path="lastName">Last Name: </form:label>
            <form:input path="lastName" /><br />
            <form:checkbox path="citizen" label="Is he/she a citizen?" /><br />
            <input type="submit" value="Submit" />
        </form:form>
        <br />
        <b>Add a Résumé to an Applicant</b><br />
        <i>Since you are listing applicants but résumés are lazy loaded and not
            fetched for every applicant in the service, you will need to query
            the database to see any résumés you add.</i>
        <form:form method="post" modelAttribute="résuméForm"
                   servletRelativeAction="/applicant/resume">
            <form:label path="applicantId">Applicant ID: </form:label>
            <form:input path="applicantId" size="2" /><br />
            <form:label path="title">Title: </form:label>
            <form:input path="title" size="40" /><br />
            <form:label path="content">Content:</form:label><br />
            <form:textarea path="content" rows="5" cols="40" /><br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
