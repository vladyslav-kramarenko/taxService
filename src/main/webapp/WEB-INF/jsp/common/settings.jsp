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
        <table id="userData" class="centerTable">
            <c:choose>
                <%--                <c:when test="${user.isIndividual==true}">--%>
                <c:when test="${1==1}">
                    <tr>
                        <td class="label">
                            <input type="hidden" name="command" value="viewSettings"/>
                            <input type="hidden" name="save" value="true">
                            <label for="first_name">
                                <fmt:message key="user.first_name"/>
                            </label>
                        </td>
                        <td class="leftAlignmentTD">
                            <input id="first_name" value="${userDetails.firstName}" name="first_name">
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="last_name">
                                <fmt:message key="user.last_name"/>
                            </label>
                        </td>
                        <td class="leftAlignmentTD">
                            <input id="last_name" value="${userDetails.lastName}" name="last_name">
                        </td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label for="patronymic">
                                <fmt:message key="user.patronymic"/>
                            </label>
                        </td>
                        <td class="leftAlignmentTD">
                            <input id="patronymic" value="${userDetails.patronymic}" name="patronymic">
                        </td>
                    </tr>

                </c:when>
                <c:otherwise>
                    <tr>
                        <td class="label">
                            <label for="company_name">
                                <fmt:message key="user.patronymic"/>
                            </label>
                        </td>
                        <td class="leftAlignmentTD">
                            <input id="company_name" value="${user.companyName}" name="company_name">
                        </td>
                    </tr>

                </c:otherwise>
            </c:choose>

            <tr>
                <td class="label">
                    <label for="code">
                        <fmt:message key="user.code"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="code" value="${user.code}" name="code">
                </td>
            </tr>

            <tr>
                <td class="label">
                    <label for="phone">
                        <fmt:message key="user.phone"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="phone" type="tel" value="${userDetails.phone}" name="phone" placeholder="000-000-0000" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}">
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="email">
                        <fmt:message key="user.email"/>
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="email" value="${user.email}" name="email" required>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <label for="currentPassword">
                        <fmt:message key="settings_jsp.current_password"/>*
                    </label>
                </td>
                <td class="leftAlignmentTD">
                    <input id="currentPassword" value="" type="password" name="currentPassword" required>
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
                <td class="centerAlignmentTD label" colspan='2'>
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