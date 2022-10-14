<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>
<script>
    $(function () {
        $("#cancel").button({});
        $("#update").button({});
        $("#settings").buttonset();
    });
</script>

<div id="content">
    <form id="settings_form" action="controller" method="post">
        <table id="userData" class="centerTable">
            <tr>
                <td class="label">
                    <input type="hidden" name="command" value="viewSettings"/>
                    <input type="hidden" name="save" value="true">
                    <label for="first_name">
                        <fmt:message key="user.first_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="first_name" value="${user.firstName}" name="first_name">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="last_name">
                        <fmt:message key="user.last_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="last_name" value="${user.lastName}" name="last_name">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="patronymic">
                        <fmt:message key="user.last_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="patronymic" value="${user.patronymic}" name="patronymic">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="code_passport">
                        <fmt:message key="user.last_name"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="code_passport" value="${user.codePassport}" name="code_passport">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="phone">
                        <fmt:message key="user.phone"/>
                    </label>
                </td>
                <td class="leftAlignmentTD"><input id="phone" type="tel" value="${user.phone}" name="phone"></td>
            </tr>
            <tr>
                <td class="label">
                    <label for="email">
                        <fmt:message key="user.email"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="email" value="${user.email}" name="email" required></td>
            </tr>
            <tr>
                <td class="label">
                    <label for="password">
                        <fmt:message key="settings_jsp.current_password"/>*
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="currentPassword" type="password" name="currentPassword" required>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="password">
                        <fmt:message key="user.password"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="password" type="password" name="password">
                </td>
            </tr>
            <tr>
                <td>${error}</td>
            </tr>
            <tr>
                <td class="centerAlignmentTD" colspan='2' class="label">
                <span id="settings">
                <input id="update" class="btn" type="submit"
                       value='<fmt:message key="settings_jsp.button.update"/>'>
                <a id="cancel" class="btn" href="controller?command=viewSettings">
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