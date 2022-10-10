<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Info"/>
<%--<%@ include file="/WEB-INF/jspf/head.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<head>
    <link rel="stylesheet" type="text/css" href="style/stylesheet.css"/>
</head>
<body>
<script src="js/reportList.js"/>
<script>
    $(function () {
        // $("#newReportType").option();//selbutton();
        $("#newReportBtn").button({});//selbutton();
        $("#newReportType").button({});
        $("input[type=submit], button").button();
        // $("#sort").selectmenu({
        //     change: function (event, ui) {
        //         $("#filterForm").submit();
        //     }
        // });
    });
</script>

<div id="main">
    <span id="newReport">
    <c:if test="${userRole.id!=1}">
        <form class="cmxform">
            <select name="reportTypeId" id="newReportType" class="select">
                <c:forEach var="reportType" items="${reportTypeList}">
                    <option value="${reportType.id}">
                        <fmt:message key='report.type.${reportType.id}'/></option>
                </c:forEach>
            </select>
            <input type="hidden" name="command" value="editReport">
            <input type="submit" id="newReportBtn" name="newReport" value=<fmt:message
                    key='reportList_jsp.button.new_report'/>>
        </form>
    </c:if>
    </span>
    <span id="filter">
    <form id="filterForm" action="controller" method="get">
        <input type="hidden" name="command" value="reportList"/>
            <c:forEach var="entry" items="${chosenStatusMap}">
                ${entry.key.name}
                <c:choose>
                    <c:when test="${entry.value}">
                         <input type="checkbox" name="chosen_status_id"
                                value="${entry.key.id}" checked
                                onChange="this.form.submit();"/>
                    </c:when>
                    <c:otherwise>
                        <input type="checkbox" name="chosen_status_id"
                               value="${entry.key.id}"
                               onChange="this.form.submit();"/>
                    </c:otherwise>
            </c:choose>
        </c:forEach>
<%--        <c:forEach var="st" items="${statusFilterList}">--%>
<%--            ${st.status.name}--%>
<%--            <c:choose>--%>
<%--                <c:when test="${st.selected}">--%>
<%--                    <input type="checkbox" name="chosen_status_id"--%>
<%--                           value="${st.status.id}" checked--%>
<%--                           onChange="this.form.submit();"/>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <input type="checkbox" name="chosen_status_id"--%>
<%--                           value="${st.status.id}"--%>
<%--                           onChange="this.form.submit();"/>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
<%--        </c:forEach>--%>
        <%--        <br/>--%>
        <%--        <label for="sort">--%>
        <%--            <fmt:message key='label.sort'/>:--%>
        <%--        </label>--%>
        <%--        <select name="sort" id="sort" class="select" onChange="this.form.submit();">--%>
        <%--            <c:forEach var="sortType" items="${sortTypes}">--%>
        <%--                <c:choose>--%>
        <%--                    <c:when test="${sortType.name != sort}">--%>
        <%--                        <option value="${sortType.name}">--%>
        <%--                            <fmt:message key='button.sort.${sortType.name}'/>--%>
        <%--                        </option>--%>
        <%--                    </c:when>--%>
        <%--                    <c:otherwise>--%>
        <%--                        <option selected value="${sort}">--%>
        <%--                            <fmt:message key='button.sort.${sort}'/></option>--%>
        <%--                    </c:otherwise>--%>
        <%--                </c:choose>--%>
        <%--            </c:forEach>--%>
        <%--        </select>--%>

</span>
    <hr/>
    <table id="reportTable" style="width: 100%;">
        <tr class="header">
            <c:if test="${userRole.id!=2}">
                <td value="asc" onclick='sortTable(0,this);'>User ID</td>
                <td value="asc" onclick='sortTable(1,this);'>Report ID</td>
            </c:if>
            <td value="asc" onclick='sortTable(2,this);'>
                <%--                <a href="controller?command=reportList&reportId=${report.id}">Edit</a>--%>
                Created
            </td>
            <td value="asc" onclick='sortTable(3,this);'>Last update</td>
            <td value="asc" onclick='sortTable(4,this);'>Report type</td>
            <td value="asc" onclick='sortTable(5,this);'>Status</td>
            <td>Edit</td>

        </tr>
        <c:forEach var="entry" items="${reportsList}">
            <c:forEach var="userReport" items="${entry.value}">
                <%--                <c:forEach var="report" items="${userReport}">--%>
                <tr>
                    <c:if test="${userRole.id!=2}">
                        <td>${entry.key.email}</td>
                        <td>${userReport.report.id}</td>
                    </c:if>
                    <td>${userReport.report.date}</td>
                    <td>${userReport.report.lastUpdate}</td>
                    <td>${userReport.type.name}</td>
                    <td>${userReport.status.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${userRole.id==2 && userReport.status.id==1}">
                                <a href="controller?command=editReport&reportId=${userReport.report.id}">Edit</a>
                            </c:when>
                            <c:otherwise>
                                <a href="controller?command=editReport&reportId=${userReport.report.id}">Show</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <%--                </c:forEach>--%>
            </c:forEach>
        </c:forEach>
    </table>
    </form>
</div>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>