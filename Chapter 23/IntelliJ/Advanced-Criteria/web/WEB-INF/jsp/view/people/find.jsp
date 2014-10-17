<%--@elvariable id="searchForm" type="com.wrox.site.MainController.SearchForm"--%>
<%--@elvariable id="results" type="org.springframework.data.domain.Page<com.wrox.site.entities.Person>"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Person</title>
    </head>
    <body>
        <h2>Find People</h2>
        [<a href="<c:url value="/people/add" />">add a person</a>]<br /><br />
        <form:form modelAttribute="searchForm" id="searchForm" method="get">
            <input type="hidden" name="search" value="true" />
            <input type="hidden" name="paging.page" value="1" />
            <input type="hidden" name="paging.size" value="10" />
            <table>
                <tr>
                    <td colspan="2"><form:checkbox path="includeFirstName" label=" First Name" /></td>
                    <td colspan="1"><form:checkbox path="includeMiddleInitial" label=" MI" /></td>
                    <td colspan="2"><form:checkbox path="includeLastName" label=" Last Name" /></td>
                </tr>
                <tr>
                    <td colspan="2"><form:input path="firstName" size="30" maxlength="100" /></td>
                    <td colspan="1" style="width:30px;"><form:input path="middleInitial" size="2" maxlength="1" /></td>
                    <td colspan="2"><form:input path="lastName" size="30" maxlength="100" /></td>
                </tr>
                <tr>
                    <td colspan="2"><form:checkbox path="includeState" label=" State or Province" /></td>
                    <td colspan="3"><form:checkbox path="includeCountry" label=" Country" /></td>
                </tr>
                <tr>
                    <td colspan="2"><form:input path="state" size="30" maxlength="100" /></td>
                    <td colspan="3"><form:input path="country" size="40" maxlength="100" /></td>
                </tr>
                <tr>
                    <td colspan="1"><form:checkbox path="includeBirthDate" label=" Birth Date" /></td>
                    <td colspan="1"><form:checkbox path="includeGender" label=" Gender" /></td>
                    <td colspan="2"><form:checkbox path="includeRace" label=" Race" /></td>
                    <td colspan="1"><form:checkbox path="includeEthnicity" label=" Ethnicity" /></td>
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
                        <input type="submit" value="Search" />
                    </td>
                </tr>
            </table>
        </form:form>
        <script type="text/javascript" language="javascript">
            var searchForm = document.getElementById('searchForm');
        </script>
        <c:if test="${results != null}">
            <br />
            <h3>Search Results</h3>
            <c:choose>
                <c:when test="${results.totalElements == 0}">
                    <i>Nothing matches your search.</i><br /><br />
                </c:when>
                <c:otherwise>
                    Page&nbsp;::&nbsp;<c:forEach begin="1" end="${results.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${(i - 1) == results.number}">${i}</c:when>
                            <c:otherwise><a href="javascript:void 0;" onclick="searchForm.elements['paging.page'].value = '${i}'; searchForm.submit();">${i}</a></c:otherwise>
                        </c:choose>&nbsp;
                    </c:forEach><br />
                    <c:forEach items="${results.content}" var="person">
                        <i>${person.firstName+=' '+=person.middleInitial+=' '+=person.lastName}</i><br />
                        <span style="font-size:11px;">
                            ${person.state}, ${person.country}<br />
                            ${person.race+=' '+=person.ethnicity+=' '+=person.gender.displayName} born ${person.birthDate}
                        </span><br /><br />
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
    </body>
</html>
