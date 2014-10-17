<%--@elvariable id="persons" type="java.util.List<com.wrox.site.entities.Person>"--%>
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
                <th>Phone Number</th>
                <th>Address</th>
            </tr>
            <c:forEach items="${persons}" var="person">
                <tr>
                    <td>${person.id}</td>
                    <td>${person.firstName}</td>
                    <td>${person.lastName}</td>
                    <td>+${person.phoneNumber.countryCode}.${person.phoneNumber.number}</td>
                    <td>${person.address.street}<br />
                        ${person.address.city}, ${person.address.state}, ${person.address.country}&nbsp;
                        ${person.address.postalCode.code}-${person.address.postalCode.suffix}</td>
                </tr>
            </c:forEach>
        </table>
        <br />
        <b>Add a Person</b>
        <form:form method="post" modelAttribute="personForm">
            <form:label path="firstName">First Name: </form:label>
            <form:input path="firstName" /><br />
            <form:label path="lastName">Last Name: </form:label>
            <form:input path="lastName" /><br />
            <form:label path="phoneCountryCode">Phone Number: </form:label>
            +<form:input path="phoneCountryCode" size="2" />.<form:input path="phoneNumber" /><br />
            <br />
            <form:label path="addressStreet">Address: </form:label>
            <form:input path="addressStreet" size="50" /><br />
            <form:label path="addressCity">City: </form:label>
            <form:input path="addressCity" />
            <form:label path="addressState">State: </form:label>
            <form:input path="addressState" size="2" />
            <form:label path="addressCountry">Country: </form:label>
            <form:input path="addressCountry" size="2" />
            <form:label path="postalCode">Postal Code: </form:label>
            <form:input path="postalCode" size="6" />-<form:input path="postalCodeSuffix" size="5" /><br />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
