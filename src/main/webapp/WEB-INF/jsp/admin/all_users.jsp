<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
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
    <%@ include file="/WEB-INF/jspf/recordsPerPageChooser.jspf" %>
</form>
<table>
    <thead>
    <tr class="header">
        <td>№</td>
        <td><fmt:message key="user.company_name"/></td>
        <td><fmt:message key="user.phone"/></td>
        <td><fmt:message key="user.email"/></td>
        <td><fmt:message key="header.banned"/></td>
        <td><fmt:message key="user.role"/></td>
        <td><fmt:message key="header.actions"/></td>
    </tr>
    </thead>
    <c:forEach var="userItem" items="${paginationList}">
        <tr>
            <td>${userItem.id}</td>
            <td>${userItem.companyName}</td>
            <td>${userItem.phone}</td>
            <td>${userItem.email}</td>
            <td>
                <form action="controller">
                    <input type="hidden" name="command" value="changeUserBannedStatus"/>
                    <input type="hidden" name="user_id" value="${userItem.id}"/>
                    <c:choose>
                        <c:when test="${userItem.id==user.id}">
                            <fmt:message key='banned.${userItem.banned}'/>
                        </c:when>
                        <c:otherwise>
                            <select name="banned_status" onChange="this.form.submit();">
                                <c:choose>
                                    <c:when test="${userItem.banned==true}">
                                        <option selected value="${true}">
                                            <fmt:message key='banned.true'/>
                                        </option>
                                        <option value="${false}">
                                            <fmt:message key='banned.false'/>
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${true}">
                                            <fmt:message key='banned.true'/>
                                        </option>
                                        <option selected value=${false}>
                                            <fmt:message key='banned.false'/>
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
            <td>
                <c:choose>
                    <c:when test="${userItem.id!=user.id}">
                        <form action="controller">
                            <input type="hidden" name="command" value="changeUserRole"/>
                            <input type="hidden" name="user_id" value="${userItem.id}"/>
                            <select name="role_id" onChange="this.form.submit();">
                                <c:forEach var="role" items="${roleMap}" varStatus="loop">
                                    <c:choose>
                                        <c:when test="${role.key != userItem.roleId}">
                                            <option value="${role.key}">
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
                    </c:when>
                    <c:otherwise>
                        <fmt:message key='role.${roleMap.get(userItem.roleId).name}'/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a class="aButton" id="editUserBtn" href="controller?command=editUser&userId=${userItem.id}">
                    <fmt:message key="all_users.jsp.edit_user"/>
                </a>
                <a class="aButton" id="deleteUserBtn" href="controller?command=deleteUser&userId=${userItem.id}"
                   onclick="return confirm('Are you sure?')">
                    <fmt:message key="all_users.jsp.delete_user"/>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<mylib:paginationFooter pageCommand="allUsers"/>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>