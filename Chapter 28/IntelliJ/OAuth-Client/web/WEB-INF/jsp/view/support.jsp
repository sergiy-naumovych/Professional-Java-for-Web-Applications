<%--@elvariable id="tickets" type="com.wrox.site.TicketWebServiceList"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>View Tickets</title>
    </head>
    <body>
        <h2>Tickets from Web Service</h2>
        <c:forEach items="${tickets.value}" var="ticket">
            ${ticket.subject}<br />
        </c:forEach>
    </body>
</html>
