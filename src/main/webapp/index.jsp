<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
<table style="width: 100%" id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%
        String site = new String("controller?command=login");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    %>

</table>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>