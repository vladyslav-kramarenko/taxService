<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>

<div align="center">
    <c:if test="${not empty errorMessage}">
<span class="errorSpan">
    <h3>${errorMessage}</h3>
</span>
    </c:if>
    <h2><fmt:message key="resetPassword_jsp.label.reset_password"/></h2>
    <p>
        <fmt:message key="resetPassword_jsp.label.description"/>:
    </p>
    <div id="individual" class="tabcontent">
        <form id="resetForm" action="controller" method="post">
            <input type="hidden" name="command" value="resetPassword"/>
            <fieldset>
                <legend>
                    <fmt:message key="login_jsp.label.email"/>
                </legend>
                <input type="text" name="email" id="email" size="20" required><br/>
            </fieldset>
            <div class="g-recaptcha"
                 data-sitekey="6LeCWdkiAAAAABblWg_dmJHRAaP9EUHsq8uQ7x4w"></div>
            <br>
            <span id="log">
                <input type="submit" id="resetBtn"
                       value='<fmt:message key="resetPassword_jsp.button.reset_password"/>'>
            </span>
        </form>
    </div>
</div>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript" src="js/pages/resetPassword.js"></script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>