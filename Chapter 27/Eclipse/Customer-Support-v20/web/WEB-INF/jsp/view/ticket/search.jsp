<%--@elvariable id="searchForm" type="com.wrox.site.TicketController.SearchForm"--%>
<%--@elvariable id="searchPerformed" type="boolean"--%>
<%--@elvariable id="results" type="org.springframework.data.domain.Page<com.wrox.site.repositories.SearchResult<com.wrox.site.entities.Ticket>>"--%>
<spring:message code="title.searchTickets" var="searchTitle" />
<template:basic htmlTitle="${searchTitle}" bodyTitle="${searchTitle}">
    <form:form method="get" modelAttribute="searchForm">
        <form:input path="query" size="40" />
        <input type="submit" value="<spring:message code="button.ticket.search" />" /><br />
        <form:label path="useBooleanMode">
            <form:checkbox path="useBooleanMode" />&nbsp;
            <spring:message code="field.ticket.search.boolean" />
        </form:label>
    </form:form><br /><br />

    <c:if test="${searchPerformed}">
        <c:choose>
            <c:when test="${results.numberOfElements == 0}">
                <i><spring:message code="message.ticketSearch.none" /></i>
            </c:when>
            <c:otherwise>
                <spring:message code="message.ticketSearch.page" />
                &nbsp;::&nbsp;<c:forEach begin="1" end="${results.totalPages}" var="i">
                <c:choose>
                    <c:when test="${(i - 1) == results.number}">${i}</c:when>
                    <c:otherwise><a href="<c:url value="/ticket/search">
                            <c:param name="query" value="${searchForm.query}" />
                            <c:param name="_useBooleanMode" value="on" />
                            <c:if test="${searchForm.useBooleanMode}">
                                <c:param name="useBooleanMode" value="true" />
                            </c:if>
                            <c:param name="paging.page" value="${i}" />
                            <c:param name="paging.size" value="10" />
                        </c:url>">${i}</a></c:otherwise>
                </c:choose>&nbsp;
                </c:forEach><br />
                <c:forEach items="${results.content}" var="result">
                    <spring:message code="message.ticketList.ticket" />&nbsp;${result.entity.id}:
                    <a href="<c:url value="/ticket/view/${result.entity.id}" />">
                        <c:out value="${wrox:abbreviateString(result.entity.subject, 60)}"/>
                    </a><br />
                    <spring:message code="message.ticketSearch.relevance" />: ${result.relevance}<br />
                    <c:out value="${result.entity.customer.username}" />&nbsp;
                    <spring:message code="message.ticketList.created" />&nbsp;
                    <wrox:formatDate value="${result.entity.dateCreated}" type="both"
                                     timeStyle="short" dateStyle="medium" /><br />
                    <br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </c:if>
</template:basic>
