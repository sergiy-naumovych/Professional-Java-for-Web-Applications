<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="com.wrox.site.Ticket"--%>
<spring:message code="title.ticketView" var="viewTitle" />
<template:basic htmlTitle="${ticket.subject}"
                bodyTitle="${viewTitle} #${ticketId}: ${ticket.subject}">
    <i><spring:message code="message.ticketView.customerName" /> -
        <c:out value="${ticket.customerName}" /><br />
    <spring:message code="message.ticketView.created" />&nbsp;
        <wrox:formatDate value="${ticket.dateCreated}" type="both"
                         timeStyle="long" dateStyle="full" /></i><br /><br />
    <c:out value="${ticket.body}" /><br /><br />
    <c:if test="${ticket.numberOfAttachments > 0}">
        <spring:message code="message.ticketView.attachments" />:
        <c:forEach items="${ticket.attachments}" var="attachment"
                   varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/ticket/${ticketId}/attachment/${attachment.name}" />"><c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
</template:basic>
