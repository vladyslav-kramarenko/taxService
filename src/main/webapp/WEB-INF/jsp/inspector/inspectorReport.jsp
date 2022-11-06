<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Report"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>
<form action="controller" class="cmxform" id="updateStatus_form" method="post">
    <input type="hidden" name="command" value='updateReportStatus'/>
    <input type="hidden" name="reportId" value="${reportId}"/>
    <select name="status" onchange=showComment(this)>
        <c:forEach var="status" items="${statusTypes}" varStatus="loop">
            <c:choose>
                <c:when test="${status.id != reportStatusId}">
                    <option value="${status.id}">
                        <fmt:message key='report.status.${status.name}'/>
                    </option>
                </c:when>
                <c:otherwise>
                    <option selected value="${status.id}">
                        <fmt:message key='report.status.${status.name}'/>
                    </option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <input class="comment" id="comment" type="text" name="comment" value="Your comment">
    <input class="aButton" id="updateStatusBtn" type="submit" name="Update status">
</form>
<%--<script>--%>
<%--    const submitpdf = function (element) {--%>
<%--        var htmlString = document.getElementsByTagName("table")[0];--%>
<%--        document.getElementById('htmlString').value = htmlString.innerHTML;--%>
<%--        alert(document.getElementById('htmlString').value);--%>
<%--        element.form.submit();--%>
<%--    }--%>
<%--</script>--%>
<%--<form action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value='downloadReport'/>--%>
<%--    <input type="hidden" name="htmlString" id="htmlString" value="">--%>
<%--    <input type="button" name="downloadXML" id="downloadPdfBtn" value="Download PDF" onclick="submitpdf(this)">--%>
<%--</form>--%>
<%--<a href="controller?command=downloadXml">Download PDF</a>--%>


<script type="text/javascript" src="js/pages/report.js">
</script>
<%@ include file="/WEB-INF/jspf/taxForms/chooseReportForm.jspf" %>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>
