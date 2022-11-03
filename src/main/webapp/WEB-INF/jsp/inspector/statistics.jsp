<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="mylib2" uri="http://com.my/mylib2" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="All users" scope="page"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>

<form>
    <label for="userFilter">Filter by company name:</label>
    <input id="userFilter" type="text" value="${userFilter}" name="userFilter" onchange="this.form.submit()">
    <mylib2:recordsPerPageChooserTag pageCommand="statistic"
                                     pageQuantity="1,2,10,20"
                                     recordsPerPage="${recordsPerPage}"
    />
</form>
<table>
    <thead>
    <tr class="header">
        <td>№</td>
        <td><fmt:message key="user.company_name"/></td>
        <c:forEach var="status" items="${statusMap}" varStatus="loop">
            <td><fmt:message key='report.status.${status.value.name}'/></td>
        </c:forEach>
    </tr>
    </thead>
    <c:forEach var="userItem" items="${paginationList}">
        <tr>
            <td>${userItem.userId}</td>
            <td><a href="controller?command=reportList&userFilter=${userItem.companyName}">${userItem.companyName}</a>
            </td>
            <c:forEach var="status" items="${statusMap}" varStatus="loop">
                <td>
                    <c:forEach var="statusValue" items="${userItem.statistic}" varStatus="loop">
                        <c:choose>
                            <c:when test="${statusValue.key==status.value}">
                                ${statusValue.value}
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

<mylib:paginationFooter pageCommand="statistics"/>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>