<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Info"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%--<body onload="init()">--%>
<head>
    <link rel="stylesheet" type="text/css" href="../../style/style.css"/>
</head>
<body>
<script>
    $(function () {
        // $("#log").buttonset();
        $("#newReportBtn").button({});
        // $("#registerBtn").button({});
    });
</script>

<div id="main">
    <form class="cmxform">
        <input type="hidden" name="command" value="editReport">
        <input type="submit" id="newReportBtn" name="newReport" value="Create new report">
    </form>
    <table id="main-container" style="width: 100%;">
        <tr>
            <%--            <td>id</td>--%>
            <td>Created</td>
            <td>Last update</td>
            <td>Report type</td>
            <td>Status</td>
            <td>Edit</td>
        </tr>
        <c:forEach var="report" items="${reportsList}">
            <tr>
                    <%--                <td>${report.id}</td>--%>
                <td>${report.date}</td>
                <td>${report.lastUpdate}</td>
                <td>${report.typeName}</td>
                <td>${report.statusName}</td>

                <td>
                    <c:choose>
                        <c:when test="${report.statusName=='created'}">
                            <a href="controller?command=editReport&reportId=${report.id}">edit</a>
                        </c:when>
                        <c:otherwise>
                            <a href="controller?command=editReport&reportId=${report.id}">show</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>