<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Report"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<br>

<c:if test="${userRole.name=='user' and editable=='true'}">
    <form action="controller" class="cmxform" id="loadXML_form" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend><fmt:message key='report.label.load_xml'/></legend>
            <input name="file" type="file"/>
            <input type="hidden" name="command" value='loadXML'/>
            <input type="hidden" name="reportTypeId" value='${reportTypeId}'/>
            <input class="aButton" type="submit" id="loadBtn" value="<fmt:message key='button.load'/>"/>
        </fieldset>
    </form>
</c:if>

<fieldset>
    <legend><fmt:message key='legend.report_info'/></legend>
    <span id="reportStatusSpan">
    <c:if test="${reportComment!=null}">
        Current report status:
        <label id="statusLabel"><fmt:message key='report.status.${reportStatus}'/></label>
        Comment:
        <label id="commentLabel">${reportComment}</label>
    </c:if>
    </span>
</fieldset>

<c:if test="${userRole.name=='inspector'}">
        <span id="changeStatusSpan">
        <form action="controller" id="updateStatusForm" method="post">

            <input type="hidden" name="command" value='updateReportStatus'/>
            <input type="hidden" name="reportId" value="${reportId}"/>

            <label id="statusSelectLabel" for="statusSelect"> Change status to:</label>

            <select id="statusSelect" name="status" onchange=showComment(this)>
                <c:forEach var="status" items="${statusTypes}" varStatus="loop">
                    <option value="${status.id}">
                        <fmt:message key='report.status.${status.name}'/>
                    </option>
                </c:forEach>
            </select>

            <input class="comment" id="comment" type="text" name="comment" value="Your comment">
            <input id="updateStatusBtn" type="submit" name="Update status">
        </form>
            </span>
</c:if>
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
