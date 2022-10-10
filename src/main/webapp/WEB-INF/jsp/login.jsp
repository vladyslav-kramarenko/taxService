<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script>
    $(function () {
        $("#log").buttonset();
        $("#loginBtn").button({});
        $("#registerBtn").button({});
    });
</script>
<div align="center" class="content">
    <form id="login_form" action="controller"
          method="post">
        <input type="hidden" name="command" value="login"/>
        <fieldset>
            <legend>
                <fmt:message key="login_jsp.label.email"/>
            </legend>
            <input name="email" required/><br/>
        </fieldset>
        <br/>
        <fieldset>
            <legend>
                <fmt:message key="login_jsp.label.password"/>
            </legend>
            <input type="password" name="password" required/>
        </fieldset>
        <br/>
        <span id="log">
            <input type="submit" class="btn" id="loginBtn" value='<fmt:message key="login_jsp.button.login"/>'>
            <input type="submit" class="btn" id="registerBtn" value='<fmt:message key="login_jsp.button.registration"/>'>
        </span>
    </form>
</div>
<script>
    $("#login_form").validate();
</script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>