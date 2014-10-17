<%--@elvariable id="added" type="java.lang.Long"--%>
<%--@elvariable id="personForm" type="com.wrox.site.MainController.PersonForm"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Person</title>
    </head>
    <body>
        <h2>Add a Person</h2>
        [<a href="<c:url value="/people/find" />">search people</a>]<br /><br />
        <c:if test="${added != null}">Added person with ID ${added}.<br /><br /></c:if>
        <form:form modelAttribute="personForm">
            <table>
                <tr>
                    <td colspan="2">First Name</td>
                    <td colspan="1">MI</td>
                    <td colspan="2">Last Name</td>
                </tr>
                <tr>
                    <td colspan="2"><form:input path="firstName" size="30" maxlength="100" /></td>
                    <td colspan="1" style="width:30px;"><form:input path="middleInitial" size="2" maxlength="1" /></td>
                    <td colspan="2"><form:input path="lastName" size="30" maxlength="100" /></td>
                </tr>
                <tr>
                    <td colspan="2">State or Province</td>
                    <td colspan="3">Country</td>
                </tr>
                <tr>
                    <td colspan="2"><form:input path="state" size="30" maxlength="100" /></td>
                    <td colspan="3"><form:input path="country" size="40" maxlength="100" /></td>
                </tr>
                <tr>
                    <td colspan="1">Birth Date</td>
                    <td colspan="1">Gender</td>
                    <td colspan="2">Race</td>
                    <td colspan="1">Ethnicity</td>
                </tr>
                <tr>
                    <td colspan="1"><form:input path="birthDate" size="8" /></td>
                    <td colspan="1"><form:select path="gender">
                        <form:options itemLabel="displayName" />
                    </form:select></td>
                    <td colspan="2"><form:input path="race" size="10" maxlength="30" /></td>
                    <td colspan="1"><form:input path="ethnicity" size="15" maxlength="50" /></td>
                </tr>
                <tr>
                    <td colspan="5" style="font-size:10px;">Format yyyy-mm-dd</td>
                </tr>
                <tr>
                    <td colspan="5" style="padding-top:25px;">
                        <input type="submit" value="Add Person" />
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>
