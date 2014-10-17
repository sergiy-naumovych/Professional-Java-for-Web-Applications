<%
    application.setAttribute("appAttribute", "foo");
    pageContext.setAttribute("pageAttribute", "bar");
    session.setAttribute("sessionAttribute", "sand");
    request.setAttribute("requestAttribute", "castle");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Information</title>
    </head>
    <body>
        Remote Address: ${pageContext.request.remoteAddr}<br />
        Request URL: ${pageContext.request.requestURL}<br />
        Session ID: ${pageContext.request.session.id}<br />
        Application Scope: ${applicationScope["appAttribute"]}<br />
        Page Scope: ${pageScope["pageAttribute"]}<br />
        Session Scope: ${sessionScope["sessionAttribute"]}<br />
        Request Scope: ${requestScope["requestAttribute"]}<br />
        User Parameter: ${param["user"]}<br />
        Color Multi-Param: ${fn:join(paramValues["colors"], ', ')}<br />
        Accept Header: ${header["Accept"]}<br />
        Session ID Cookie Value: ${cookie["JSESSIONID"].value}<br />
    </body>
</html>
