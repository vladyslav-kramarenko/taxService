<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<%--<body onload="init()">--%>
<body>

<div id="main">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <table id="main-container" style="width: 100%;">
        <tr>
            <td>id</td>
            <td>Date</td>
            <td>Report type</td>
            <td>Status</td>
            <td>Edit</td>
        </tr>
        <c:forEach var="report" items="${reportsList}">
            <tr>
                <td>${report.id}</td>
                <td>${report.date}</td>
                <td>${report.typeName}</td>
                <td>${report.statusName}</td>
                <td><a href="controller?command=editReport&reportId=${report.id}">edit</a></td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>