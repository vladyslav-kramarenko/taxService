<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>
<%@ include file="/WEB-INF/jspf/errorMessage.jspf" %>
<div class="content">
    <div id="individual" class="tabcontent">
        <form id="login_form" action="controller" method="post">
            <input type="hidden" name="command" value="login"/>
            <fieldset class="roundFieldset">
                <legend>
                    <fmt:message key="login_jsp.label.email"/>
                </legend>
                <input id="email" type="email" name="email" required/><br/>
            </fieldset>
            <br/>
            <fieldset>
                <legend>
                    <fmt:message key="login_jsp.label.password"/>
                </legend>
                <input id="password" type="password" name="password" required/>
            </fieldset>
            <br>
            <div class="g-recaptcha"
                 data-sitekey="6LeCWdkiAAAAABblWg_dmJHRAaP9EUHsq8uQ7x4w"></div>
            <br>
            <span id="log">
                <input type="submit" class="btn" id="loginBtn"
                       value='<fmt:message key="login_jsp.button.login"/>'>
                <a href="controller?command=registration" class="btn" id="registerBtn">
                    <fmt:message key="login_jsp.button.registration"/>
                </a>
            </span>
        </form>
        <a href="controller?command=resetPassword">
            <fmt:message key="login_jsp.label.reset_password"/>
        </a>
    </div>
</div>

<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript" src="js/pages/login.js"></script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>