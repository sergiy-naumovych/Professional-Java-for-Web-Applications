<%--@elvariable id="ticketForm" type="com.wrox.site.TicketController.Form"--%>
<spring:message code="title.ticketAdd" var="addTitle" />
<template:basic htmlTitle="${addTitle}" bodyTitle="${addTitle}">
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="ticketForm">
        <form:label path="subject"><spring:message code="field.ticket.subject" /></form:label><br />
        <form:input path="subject"/><br /><br />
        <form:label path="body"><spring:message code="field.ticket.body" /></form:label><br />
        <form:textarea path="body" rows="5" cols="30" /><br /><br />
        <b><spring:message code="field.ticket.attachments" /></b><br />
        <input type="file" name="attachments" multiple="multiple"/><br /><br />
        <input type="submit" value="<spring:message code="button.ticket.submit" />"/>
    </form:form>
</template:basic>
