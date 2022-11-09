<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="registration"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>
<%@ include file="/WEB-INF/jspf/errorMessage.jspf" %>
<div align="center" class="content">
    <div class="tab">
        <button id="individualBtn" class="tabButton tablinks active" onclick="openTab('individual')">
            <fmt:message key="label.individual"/>
        </button>
        <button id="companyBtn" class="tabButton tablinks" onclick="openTab('company')">
            <fmt:message key="label.company"/>
        </button>
    </div>
    <br>
    <div id="individual" class="tabcontent">
        <form id="registration_form_individual" action="controller" method="post">
            <input type="hidden" name="command" value="registration"/>
            <input type="hidden" name="legalType" value="1"/>
            <fieldset>
                <legend>
                    <fmt:message key="user.email"/>*
                </legend>
                <input type="email" name="email" required/><br/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.password"/>*
                </legend>
                <input type="password" name="password" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.code"/>*
                </legend>
                <input type="text" name="code" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.first_name"/>*
                </legend>
                <input type="text" name="first_name" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.last_name"/>*
                </legend>
                <input type="text" name="last_name" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.patronymic"/>
                </legend>
                <input type="text" name="patronymic"/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.phone"/>
                </legend>
                <input type="tel" name="phone" placeholder="000-000-0000" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"/>
            </fieldset>
            <br>
            <div class="g-recaptcha"
                 data-sitekey="6LeCWdkiAAAAABblWg_dmJHRAaP9EUHsq8uQ7x4w"></div>
            <br>
            <input id="regBtnInd" class="aButton" type="submit"
                   value='<fmt:message key="registration_jsp.button.registration"/>'>
        </form>
    </div>

    <div id="company" class="tabcontent">
        <form id="registration_form_company" action="controller" method="post">
            <input type="hidden" name="command" value="registration"/>
            <input type="hidden" name="legalType" value="2"/>
            <fieldset>
                <legend>
                    <fmt:message key="user.email"/>*
                </legend>
                <input type="email" name="email" required/><br/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.password"/>*
                </legend>
                <input type="password" name="password" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.company_name"/>*
                </legend>
                <input type="text" name="company_name" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="company.code"/>*
                </legend>
                <input type="text" name="code" required/>
            </fieldset>
            <fieldset>
                <legend>
                    <fmt:message key="user.phone"/>
                </legend>
                <input type="tel" name="phone" placeholder="000-000-0000" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"/>
            </fieldset>
            <br>
            <div class="g-recaptcha"
                 data-sitekey="6LeCWdkiAAAAABblWg_dmJHRAaP9EUHsq8uQ7x4w"></div>
            <br>
            <input id="regBtnComp" class="aButton" type="submit"
                   value='<fmt:message key="registration_jsp.button.registration"/>'>
        </form>
    </div>
</div>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript" src="js/pages/registration.js"></script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>