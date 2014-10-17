<%--@elvariable id="ticketForm" type="com.wrox.site.TicketController.Form"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<spring:message code="title.ticketAdd" var="addTitle" />
<template:basic htmlTitle="${addTitle}" bodyTitle="${addTitle}">
    <c:if test="${validationErrors != null}"><div class="errors">
        <ul>
            <c:forEach items="${validationErrors}" var="error">
                <li><c:out value="${error.message}" /></li>
            </c:forEach>
        </ul>
    </div></c:if>
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="ticketForm">
        <form:label path="subject"><spring:message code="field.ticket.subject" /></form:label><br />
        <form:input path="subject"/><br />
        <form:errors path="subject" cssClass="errors" /><br />
        <form:label path="body"><spring:message code="field.ticket.body" /></form:label><br />
        <form:textarea path="body" rows="5" cols="30" /><br />
        <form:errors path="body" cssClass="errors" /><br />
        <b><spring:message code="field.ticket.attachments" /></b><br />
        <input type="file" name="attachments" multiple="multiple"/><br />
        <form:errors path="attachments" cssClass="errors" /><br />
        <input type="submit" value="<spring:message code="button.ticket.submit" />"/>
    </form:form>
</template:basic>
