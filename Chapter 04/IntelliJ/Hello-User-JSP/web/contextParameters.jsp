<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello User Application</title>
    </head>
    <body>
        settingOne: <%= application.getInitParameter("settingOne") %>,
        settingTwo: <%= application.getInitParameter("settingTwo") %>
    </body>
</html>
