<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="List orders" scope="page"/>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<form>
    количество записей на странице
    <input type="hidden" name="command" value="allUsers">
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
<table>
    <thead>
    <tr class="header">
        <td>№</td>
        <td><fmt:message key="user.first_name"/></td>
        <td><fmt:message key="user.last_name"/></td>
        <td><fmt:message key="user.phone"/></td>
        <td><fmt:message key="user.email"/></td>
        <td><fmt:message key="user.role"/></td>
    </tr>
    </thead>
    <c:forEach var="bean" items="${paginationList}">
        <tr>
            <td>${bean.id}</td>
            <td>${bean.firstName}</td>
            <td>${bean.lastName}</td>
            <td>${bean.phone}</td>
            <td>${bean.email}</td>
            <c:choose>
                <c:when test="${bean.id!=user.id}">
                    <td>
                        <form action="controller">
                            <input type="hidden" name="command" value="changeUserRole"/>
                            <input type="hidden" name="user_id" value="${bean.id}"/>
                            <select name="role_id" onChange="this.form.submit();">
                                <c:forEach var="role" items="${roleMap}" varStatus="loop">
                                    <c:choose>
                                        <c:when test="${role.key != bean.roleId}">
                                            <option value="${loop.index}">
                                                <fmt:message key='role.${role.value.name}'/>
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option selected value="${role.key}"><fmt:message
                                                    key='role.${role.value.name}'/>
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <fmt:message key='role.${roleMap.get(bean.roleId).name}'/></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>


<div class="pagination_section">
    <c:if test="${selectedPage != 1}">
        <a href="controller?command=allUsers&selectedPage=${selectedPage - 1}&recordsPerPage=${recordsPerPage}"><<
            Previous</a>
    </c:if>
    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${selectedPage == i}">
                <a href="controller?command=allUsers&selectedPage=${i}&recordsPerPage=${recordsPerPage}"
                   class="active">${i}</a>
            </c:when>
            <c:otherwise>
                <a href="controller?command=allUsers&selectedPage=${i}&recordsPerPage=${recordsPerPage}">${i}</a>
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