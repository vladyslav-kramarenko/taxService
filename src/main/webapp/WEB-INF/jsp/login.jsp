<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<script>
    $(function () {
        $("#log").buttonset();
        $("#loginBtn").button({});
        $("#registerBtn").button({});
    });
</script>
<c:if test="${not empty errorMessage}">
<span class="errorSpan">
    <h3>${errorMessage}</h3>
</span>
</c:if>
<span class="content">
    <span id="inputForm">
        <form id="login_form" action="controller" method="post">
            <input type="hidden" name="command" value="login"/>
            <fieldset>
                <legend>
                    <fmt:message key="login_jsp.label.email"/>
                </legend>
                <input id="email" name="email" required/><br/>
            </fieldset>
            <br/>
            <fieldset>
                <legend>
                    <fmt:message key="login_jsp.label.password"/>
                </legend>
                <input id="password" type="password" name="password" required/>
            </fieldset>
            <br/>
            <span id="log">
                <input type="submit" class="btn" id="loginBtn"
                       value='<fmt:message key="login_jsp.button.login"/>'>
                <input type="submit" class="btn" id="registerBtn"
                       value='<fmt:message key="login_jsp.button.registration"/>'>
            </span>
        </form>
    </span>
</span>
<script>
    $("#login_form").validate();
</script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>