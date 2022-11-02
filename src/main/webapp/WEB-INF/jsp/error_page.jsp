<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<script src="js/boostrap.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="css/boostrap.css"/>
<c:set var="title" value="Error" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<c:set var="code"
       value="${requestScope['jakarta.servlet.error.status_code']}"/>
<c:set var="message"
       value="${requestScope['jakarta.servlet.error.message']}"/>
<c:set var="exception"
       value="${requestScope['jakarta.servlet.error.exception']}"/>

<br>
<div class="container-fluid">
    <div class="text-center">
        <c:if test="${code==null or code==''}">
            <div class="error mx-auto" data-text="404">404</div>
        </c:if>
        <div class="error mx-auto" data-text=${code}>${code}</div>
        <p class="lead text-gray-800 mb-5">
            <c:if test="${message==null or message==''}">
                <fmt:message key="error.notFound"/>
            </c:if>
            ${message}</p>
        <p>
            <%-- this way we print exception stack trace --%>
            <c:if test="${not empty exception}">
                <hr/>
                <h3>Stack trace:</h3>
                <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
                    ${stackTraceElement}
                </c:forEach>
            </c:if>
        </p>
        <p class="text-gray-500 mb-0">It looks like you found a glitch in the matrix...</p>
        <a href="controller?command=reportList">&larr; Back to Reports</a>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>