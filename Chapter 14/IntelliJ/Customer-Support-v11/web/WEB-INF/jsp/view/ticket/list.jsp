<%--@elvariable id="tickets" type="java.util.List<com.wrox.site.Ticket>"--%>
<template:basic htmlTitle="Tickets" bodyTitle="Tickets">
    <c:choose>
        <c:when test="${fn:length(tickets) == 0}">
            <i>There are no tickets in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${tickets}" var="ticket">
                Ticket ${ticket.id}:
                <a href="<c:url value="/ticket/view/${ticket.id}" />">
                <c:out value="${wrox:abbreviateString(ticket.subject, 60)}"/>
                </a><br />
                <c:out value="${ticket.customerName}" /> created ticket
                <wrox:formatDate value="${ticket.dateCreated}" type="both"
                                 timeStyle="short" dateStyle="medium" /><br />
                <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</template:basic>
