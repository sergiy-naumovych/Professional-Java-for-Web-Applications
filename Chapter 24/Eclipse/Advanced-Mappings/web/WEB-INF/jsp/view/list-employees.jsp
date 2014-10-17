<%--@elvariable id="employees" type="java.util.List<com.wrox.site.entities.Employee>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Basic Collections and Maps - Employees</title>
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
                <th>Phone Numbers</th>
                <th>Extra Properties</th>
            </tr>
            <c:forEach items="${employees}" var="employee">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    <td>${employee.phoneNumbers}</td>
                    <td>${employee.extraProperties}</td>
                </tr>
            </c:forEach>
        </table>
        <br />
        <b>Add an Employee</b>
        <form:form method="post" modelAttribute="employeeForm">
            <form:label path="firstName">First Name: </form:label>
            <form:input path="firstName" /><br />
            <form:label path="lastName">Last Name: </form:label>
            <form:input path="lastName" /><br />
            <input type="submit" value="Submit" />
        </form:form>
        <br />
        <b>Add an Extra Property to an Employee</b>
        <form method="post" action="<c:url value="/employee/property" />">
            <label>Employee ID:
                <input type="text" name="employeeId" size="2" /></label><br />
            <label>Property Name:
                <input type="text" name="name" size="20" /></label><br />
            <label>Property Value:
                <input type="text" name="value" size="30" /></label><br />
            <input type="submit" value="Submit" />
        </form>
        <br />
        <b>Add a Phone Number to an Employee</b>
        <form method="post" action="<c:url value="/employee/phone" />">
            <label>Employee ID:
                <input type="text" name="employeeId" size="2" /></label><br />
            <label>Number:
                <input type="text" name="number" /></label><br />
            <input type="submit" value="Submit" />
        </form>
        <br />
        <b>Add an Address to an Employee</b><br />
        <i>Since you are listing employees but addresses are lazy loaded and
            not fetched for every employee in the service, you will need to query
            the database to see any addresses you add.</i>
        <form:form method="post" modelAttribute="addressForm"
                   servletRelativeAction="/employee/address">
            <form:label path="employeeId">Employee ID: </form:label>
            <form:input path="employeeId" size="2" /><br />
            <form:label path="street">Address: </form:label>
            <form:input path="street" size="50" /><br />
            <form:label path="city">City: </form:label>
            <form:input path="city" />
            <form:label path="state">State: </form:label>
            <form:input path="state" size="2" />
            <form:label path="country">Country: </form:label>
            <form:input path="country" size="2" />
            <form:label path="postalCode">Postal Code: </form:label>
            <form:input path="postalCode" size="6" />-<form:input path="postalCodeSuffix" size="5" /><br />
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
