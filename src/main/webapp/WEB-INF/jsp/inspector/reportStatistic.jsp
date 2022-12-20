<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="mylib2" uri="http://com.my/mylib2" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Statistic by report types" scope="page"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>

<form>
    <table>
        <tr>
            <td>
                <label for="typeFilter">
                    <fmt:message key="label.filter_by_type"/>:</label>
            </td>
            <td>
                <input class="filterInput" id="typeFilter" type="text" value="${typeFilter}" name="typeFilter"
                       onchange="this.form.submit()">
            </td>
            <td><mylib2:recordsPerPageChooserTag pageCommand="reportStatistic"
                                                 pageQuantity="1,2,10,20"
                                                 recordsPerPage="${recordsPerPage}"
                                                 localeName="${sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session']}"
            />
            </td>
        </tr>
    </table>
</form>
<table>
    <thead>
    <tr class="header">
        <td>№</td>
        <td><fmt:message key="header.report_type"/></td>
        <c:forEach var="status" items="${statusMap}" varStatus="loop">
            <td><fmt:message key='report.status.${status.value.name}'/></td>
        </c:forEach>
    </tr>
    </thead>
    <mylib:statisticTableData commandLink="controller?command=reportList&typeFilter"/>
</table>

<mylib:paginationFooter pageCommand="statistic"/>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>