<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="com.wrox.site.Ticket"--%>
<%--@elvariable id="comments" type="org.springframework.data.domain.Page<com.wrox.site.TicketComment>"--%>
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

    <h3><spring:message code="title.ticketView.comments" /></h3>
    <c:choose>
        <c:when test="${comments.totalElements == 0}">
            <i><spring:message code="message.ticketView.noComments" /></i><br /><br />
        </c:when>
        <c:otherwise>
            <spring:message code="message.ticketView.comments.page" />
            &nbsp;::&nbsp;<c:forEach begin="1" end="${comments.totalPages}" var="i">
                <c:choose>
                    <c:when test="${(i - 1) == comments.number}">${i}</c:when>
                    <c:otherwise><a href="<c:url value="/ticket/view/${ticket.id}">
                            <c:param name="paging.page" value="${i}" />
                            <c:param name="paging.size" value="10" />
                        </c:url>">${i}</a></c:otherwise>
                </c:choose>&nbsp;
            </c:forEach><br />
            <c:forEach items="${comments.content}" var="comment">
                <i><c:out value="${comment.customerName}" />&nbsp;</i>
                (<wrox:formatDate value="${comment.dateCreated}" type="both"
                                 timeStyle="short" dateStyle="medium" />)<br />
                <c:out value="${comment.body}" /><br /><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <b><spring:message code="message.ticketView.addComment" /></b><br />
    <c:url value="/ticket/comment/${ticketId}" var="formAction" />
    <form:form action="${formAction}" method="post" modelAttribute="commentForm">
        <form:label path="body"><spring:message code="field.ticket.comment.body" /></form:label>
        <form:textarea path="body" rows="5" cols="30" /><br />
        <form:errors path="body" cssClass="errors" /><br />
        <input type="submit" value="<spring:message code="button.ticket.submit" />"/>
    </form:form>
</template:basic>
