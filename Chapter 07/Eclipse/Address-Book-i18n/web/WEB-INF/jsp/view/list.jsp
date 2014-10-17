<%--@elvariable id="contacts" type="java.util.Set<com.wrox.Contact>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title><fmt:message key="title.browser" /></title>
    </head>
    <body>
        <h2><fmt:message key="title.page" /></h2>
        <c:choose>
            <c:when test="${fn:length(contacts) == 0}">
                <i><fmt:message key="message.noContacts" /></i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${contacts}" var="contact">
                    <b>
                        <c:out value="${contact.lastName}, ${contact.firstName}" />
                    </b><br />
                    <c:out value="${contact.address}" /><br />
                    <c:out value="${contact.phoneNumber}" /><br />
                    <c:if test="${contact.birthday != null}">
                        <fmt:message key="label.birthday" />:
                        ${contact.birthday.month.getDisplayName(
                            'FULL', pageContext.response.locale
                        )}&nbsp;${contact.birthday.dayOfMonth}<br />
                    </c:if>
                    <fmt:message key="label.creationDate" />:
                    <fmt:formatDate value="${contact.oldDateCreated}" type="both"
                                    dateStyle="long" timeStyle="long" />
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>
