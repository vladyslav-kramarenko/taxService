<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="mylib2" uri="http://com.my/mylib2" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Reports"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<div id="main">
    <div id="newReport">
        <c:if test="${userRole.id==2}">
            <br>
            <form id="newReportForm">
                <input type="hidden" name="command" value="editReport">
                <select name="reportTypeId" id="newReportType" class="select">
                    <c:forEach var="reportType" items="${reportTypeList}">
                        <option value="${reportType.id}">
                            <fmt:message key='report.type.${reportType.id}'/>
                        </option>
                    </c:forEach>
                </select>
                <input class="aButton" type="submit" id="newReportBtn" name="newReport"
                       value=<fmt:message key='reportList_jsp.button.new_report'/>>
            </form>
        </c:if>
    </div>
    <span id="filter">

<table>
        <form id="filterForm" action="controller" method="get"> <input type="hidden" name="command" value="reportList"/>

        <input type="hidden" name="recordsPerPage" value="${recordsPerPage}">

            <c:if test="${userRole.id!=2}">
                <tr>
                    <td>
                <label class="filterLabel" for="userFilter"><fmt:message key='label.filter_by_user'/>: </label>
                    </td>
                    <td>
                <input id="userFilter" name="userFilter" value="${userFilter}" type="text"
                       onchange="this.form.submit()">
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>
                    <label class="filterLabel" for="typeFilter"><fmt:message key='label.filter_by_type'/>: </label>
                </td>
                <td>
                    <input id="typeFilter" name="typeFilter" value="${typeFilter}" type="text"
                           onchange="this.form.submit()">
                </td>
            </tr>
            <tr>
                <td>
            <label class="filterLabel"><fmt:message key='label.filter_by_status'/>: </label>
                </td>
                <td>
            <c:forEach var="entry" items="${chosenStatusMap}">
                <fmt:message key='report.status.${entry.key.name}'/>
                <c:choose>
                    <c:when test="${entry.value}">
                         <input class="statusFilterCheckBox" type="checkbox" name="chosen_status_id"
                                value="${entry.key.id}" checked
                                onChange="this.form.submit();"/>
                    </c:when>
                    <c:otherwise>
                         <input class="statusFilterCheckBox" type="checkbox" name="chosen_status_id"
                                value="${entry.key.id}"
                                onChange="this.form.submit();"/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
                </td>
        </form>
                <td>
                    <form action="controller" method="get">
                        <mylib2:recordsPerPageChooserTag pageCommand="reportList"
                                                         pageQuantity="1,2,10,20"
                                                         recordsPerPage="${recordsPerPage}"
                        />
                    </form>
                </td>
    </tr>
</table>
    </span>
    <hr>
    <table id="reportTable" style="width: 100%;">
        <tr class="header">
            <c:if test="${userRole.id!=2}">
                <td value="asc" onclick='sortTable(0,this);'><fmt:message key='header.user'/></td>
                <td value="asc" onclick='sortTable(1,this);'>Report ID</td>
            </c:if>
            <td value="asc" onclick='sortTable(2,this);'>
                <%--                <a href="controller?command=reportList&reportId=${report.id}">Edit</a>--%>
                <fmt:message key='header.created'/>
            </td>
            <td value="asc" onclick='sortTable(3,this);'><fmt:message key='header.last_update'/></td>
            <td value="asc" onclick='sortTable(4,this);'><fmt:message key='header.report_type'/></td>
            <td value="asc" onclick='sortTable(5,this);'><fmt:message key='header.status'/></td>
            <td><fmt:message key='header.actions'/></td>
        </tr>
        <c:forEach var="report" items="${paginationList}">
            <tr>
                <c:if test="${userRole.id!=2}">
                    <td>${report.user.companyName}</td>
                    <td>${report.report.id}</td>
                </c:if>
                <td>${report.report.date}</td>
                <td>${report.report.lastUpdate}</td>
                <td><fmt:message key='report.type.${report.type.id}'/></td>
                <td>
                    <span class="status ${report.status.name}">
                    <fmt:message key='report.status.${report.status.name}'/>
                </span>
                </td>
                <td>
                    <span class="actions">
                        <c:choose>
                            <c:when test="${userRole.id==2}">
                                <c:choose>
                                    <c:when test="${report.status.id==1}">
                                        <a class="reportActionEditButton"
                                           href="controller?command=editReport&reportId=${report.report.id}">
                                            <fmt:message key='button.edit'/>
                                        </a>
                                        <a class="reportActionTrashButton"
                                           href="controller?command=deleteReport&reportId=${report.report.id}"
                                           onclick="return confirm(<fmt:message key='label.confirm'/>">
                                            <fmt:message key='button.delete'/>
                                        </a>
                                    </c:when>
                                    <c:when test="${report.status.id!=1}">
                                        <a class="reportActionEditButton"
                                           href="controller?command=editReport&reportId=${report.report.id}">
                                            <fmt:message key='button.show'/>
                                        </a>
                                        <c:choose>
                                            <c:when test="${report.status.id==2}">
                                                <a class="reportActionCancelButton"
                                                   href="controller?command=cancelReport&reportId=${report.report.id}"
                                                   onclick="return confirm(<fmt:message key='label.confirm'/>)">
                                                    <fmt:message key='button.cancel'/>
                                                </a>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <a class="reportActionEditButton"
                                   href="controller?command=editReport&reportId=${report.report.id}">
                                    <fmt:message key='button.show'/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<mylib:paginationFooter pageCommand="reportList"/>
<script src="js/pages/reportList.js"></script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>