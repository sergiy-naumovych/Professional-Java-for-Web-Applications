<%--@elvariable id="ticketForm" type="com.wrox.site.TicketController.Form"--%>
<template:basic htmlTitle="Create a Ticket" bodyTitle="Create a Ticket">
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="ticketForm">
        <form:label path="subject">Subject</form:label><br />
        <form:input path="subject"/><br /><br />
        <form:label path="body">Body</form:label><br />
        <form:textarea path="body" rows="5" cols="30" /><br /><br />
        <b>Attachments</b><br />
        <input type="file" name="attachments" multiple="multiple"/><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>
