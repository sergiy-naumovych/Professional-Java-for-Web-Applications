<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>
    <jsp:attribute name="navigationContent">
        <security:authorize access="hasAuthority('VIEW_TICKETS')">
            <a href="<c:url value="/ticket/list" />"><spring:message code="nav.item.list.tickets" /></a><br />
            <a href="<c:url value="/ticket/search" />"><spring:message code="nav.item.search.tickets" /></a><br />
        </security:authorize>
        <security:authorize access="hasAuthority('CREATE_TICKET')">
            <a href="<c:url value="/ticket/create" />"><spring:message code="nav.item.create.ticket" /></a><br />
        </security:authorize>
        <security:authorize access="hasAuthority('CREATE_CHAT_REQUEST')">
            <a href="javascript:void 0;" onclick="newChat();"><spring:message code="nav.item.chat.support" /></a><br />
        </security:authorize>
        <security:authorize access="hasAuthority('VIEW_CHAT_REQUESTS')">
            <a href="<c:url value="/chat/list" />"><spring:message code="nav.item.view.chat" /></a><br />
        </security:authorize>
        <security:authorize access="hasAuthority('VIEW_USER_SESSIONS')">
            <a href="<c:url value="/session/list" />"><spring:message code="nav.item.list.session" /></a><br />
        </security:authorize>
        <a href="javascript:void 0;"
           onclick="postInvisibleForm('<c:url value="/logout" />', { });"><spring:message code="nav.item.logout" /></a><br />
        <jsp:invoke fragment="extraNavigationContent" />
        <br />Welcome, <security:authentication property="principal.username" />!
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>
