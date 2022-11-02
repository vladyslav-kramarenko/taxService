<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<script>
    $(function () {
        $("#cancel").button({});
        $("#update").button({});
        $("#settings").buttonset();
    });
</script>

<div id="content">
    <c:if test="${not empty errorMessage}">
        <h3>${errorMessage}</h3>
    </c:if>
    <form id="settings_form" action="controller" method="post">
        <input type="hidden" name="command" value="editUser"/>
        <input type="hidden" name="save" value="true">
        <input type="hidden" name="userId" value="${userEdit.id}">
        <table id="userData" class="centerTable">
            <tr>
                <td class="label">

                    <label for="first_name">
                        <fmt:message key="user.first_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="first_name" value="${userEdit.firstName}" name="first_name">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="last_name">
                        <fmt:message key="user.last_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="last_name" value="${userEdit.lastName}" name="last_name">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="patronymic">
                        <fmt:message key="user.patronymic"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="patronymic" value="${userEdit.patronymic}" name="patronymic">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="company_name">
                        <fmt:message key="user.company_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="company_name" value="${userEdit.companyName}" name="company_name">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="code">
                        <fmt:message key="user.code"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="code" value="${userEdit.code}" name="code">
                </td>
            </tr>

            <tr>
                <td class="label">
                    <label for="phone">
                        <fmt:message key="user.phone"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="phone" type="tel" value="${userEdit.phone}" name="phone">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="email">
                        <fmt:message key="user.email"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="email" value="${userEdit.email}" name="email" required>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td class="label">--%>
            <%--                    <label for="currentPassword">--%>
            <%--                        <fmt:message key="settings_jsp.current_password"/>*--%>
            <%--                    </label>--%>
            <%--                </td>--%>
            <%--                <td class="leftAlignmentTD">--%>
            <%--                    <input id="currentPassword" value="" type="password" name="currentPassword" required>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <%--            <tr>--%>
            <%--                <td class="label">--%>
            <%--                    <label for="password">--%>
            <%--                        <fmt:message key="user.password"/>--%>
            <%--                    </label>--%>
            <%--                </td>--%>
            <%--                <td class="leftAlignmentTD">--%>
            <%--                    <input id="password" type="password" name="password">--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>${error}</td>
            </tr>
            <tr>
                <td class="centerAlignmentTD label" colspan='2'>
                <span id="settings">
                <input id="update" class="btn" type="submit"
                       value='<fmt:message key="settings_jsp.button.update"/>'>
                <a id="cancel" class="btn" href="controller?command=editUser&userId=${userEdit.id}">
                    <fmt:message key="settings_jsp.button.cancel"/>
                </a>
                </span>
                </td>
            </tr>
        </table>
    </form>
</div>

<script>
    $("#settings_form").validate();
</script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>