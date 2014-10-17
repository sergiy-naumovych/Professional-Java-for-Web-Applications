<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="title.create.employee" /></title>
        <style type="text/css">
            .errors {
                color:#CC0000;
                display: block;
            }
        </style>
    </head>
    <body>
        <h2><spring:message code="title.create.employee" /></h2>
        <c:if test="${validationErrors != null}"><div class="errors">
            <ul>
                <c:forEach items="${validationErrors}" var="error">
                    <li><c:out value="${error.message}" /></li>
                </c:forEach>
            </ul>
        </div></c:if>
        <form:form method="post" modelAttribute="employeeForm">
            <form:label path="firstName"><spring:message code="form.first.name" />
            </form:label><br />
            <form:input path="firstName" /><br />
            <form:errors path="firstName" cssClass="errors" /><br />

            <form:label path="middleName">
                <spring:message code="form.middle.name" />
            </form:label><br />
            <form:input path="middleName" /><br />
            <form:errors path="middleName" cssClass="errors" /><br />

            <form:label path="lastName"><spring:message code="form.last.name" />
            </form:label><br />
            <form:input path="lastName" /><br />
            <form:errors path="lastName" cssClass="errors" /><br />

            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
