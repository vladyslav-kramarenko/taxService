<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Info"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script src="js/reportList.js"></script>

<div id="main">
    <span id="newReport">
    <c:if test="${userRole.id==2}">
        <form>
            <select name="reportTypeId" id="newReportType" class="select">
                <c:forEach var="reportType" items="${reportTypeList}">
                    <option value="${reportType.id}">
                        <fmt:message key='report.type.${reportType.id}'/></option>
                </c:forEach>
            </select>
            <input type="hidden" name="command" value="editReport">
            <input class="aButton" type="submit" id="newReportBtn" name="newReport"
                   value=
                <fmt:message key='reportList_jsp.button.new_report'/>>
        </form>
    </c:if>
    </span>
    <form>
        количество записей на странице
        <input type="hidden" name="command" value="reportList">
        <input type="hidden" name="selectedPage" value="1">
        <select name="recordsPerPage" class="select" onChange="this.form.submit();">
            <c:choose>
                <c:when test="${recordsPerPage==1}">
                    <option selected value="1">1</option>
                </c:when>
                <c:otherwise>
                    <option value="1">1</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${recordsPerPage==10}">
                    <option selected value="10">10</option>
                </c:when>
                <c:otherwise>
                    <option value="10">10</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${recordsPerPage==20}">
                    <option selected value="20">20</option>
                </c:when>
                <c:otherwise>
                    <option value="20">20</option>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${recordsPerPage==50}">
                    <option selected value="50">50</option>
                </c:when>
                <c:otherwise>
                    <option value="50">50</option>
                </c:otherwise>
            </c:choose>
        </select>
    </form>
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
            <td>Actions</td>

        </tr>
        <c:forEach var="report" items="${paginationList}">
            <tr>
                <c:if test="${userRole.id!=2}">
                    <td>${report.user.email}</td>
                    <td>${report.report.id}</td>
                </c:if>
                <td>${report.report.date}</td>
                <td>${report.report.lastUpdate}</td>
                <td>${report.type.name}</td>
                <td>${report.status.name}</td>
                <td>
                        <span class="actions">
                        <c:choose>
                            <c:when test="${userRole.id==2}">
                                <c:choose>
                                    <c:when test="${report.status.id==1}">
                                        <a class="aButton"
                                           href="controller?command=editReport&reportId=${report.report.id}">Edit</a>
                                        <a class="aButton"
                                           href="controller?command=deleteReport&reportId=${report.report.id}"
                                           onclick="return confirm('Are you sure?')">Delete</a>
                                    </c:when>
                                    <c:when test="${report.status.id==2}">
                                        <a class="aButton"
                                           href="controller?command=editReport&reportId=${report.report.id}">Show</a>
                                        <a class="aButton"
                                           href="controller?command=cancelReport&reportId=${report.report.id}"
                                           onclick="return confirm('Are you sure?')">Cancel</a>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <a href="controller?command=editReport&reportId=${report.report.id}">Show</a>
                            </c:otherwise>
                        </c:choose>
                            </span>
                </td>
            </tr>
        </c:forEach>
    </table>
    </form>
</div>

<div class="pagination_section">
    <c:if test="${selectedPage != 1}">
        <a href="controller?command=reportList&selectedPage=${selectedPage - 1}&recordsPerPage=${recordsPerPage}"><<
            Previous</a>
    </c:if>
    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${selectedPage == i}">
                <a href="controller?command=reportList&selectedPage=${i}&recordsPerPage=${recordsPerPage}"
                   class="active">${i}</a>
            </c:when>
            <c:otherwise>
                <a href="controller?command=reportList&selectedPage=${i}&recordsPerPage=${recordsPerPage}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${selectedPage < noOfPages}">
        <a href="employee.do?selectedPage=${selectedPage + 1}&recordsPerPage=${recordsPerPage}">Next >></a>
    </c:if>
</div>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>