<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Info"/>
<%--<%@ include file="/WEB-INF/jspf/head.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<body>
<c:if test="${reportStatus.id==1}">
    <form action="controller" class="cmxform" id="loadXML_form" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>Load XML</legend>
            <input name="file" type="file"/>
            <input type="hidden" name="command" value='loadXML'/>
            <input type="hidden" name="reportTypeId" value='${reportTypeId}'/>
            <input type="submit" id="loadBtn" value='Load'/>
        </fieldset>
    </form>
</c:if>
<c:if test="${userRole.name=='inspector'}">
    <form action="controller" class="cmxform" id="updateStatus_form" method="post">
        <input type="hidden" name="command" value='updateReportStatus'/>
        <input type="hidden" name="reportId" value="${reportId}"/>
        <input type="text" name="comment" value="Your comment">
        <select name="status">
            <c:forEach var="status" items="${statusTypes}" varStatus="loop">

                <c:choose>
                    <c:when test="${loop.index+2 != reportStatusId}">
                        <option value="${loop.index+2}">
                            <fmt:message key='report.status.${status}'/>
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option selected value="${loop.index+2}">
                            <fmt:message key='report.status.${status}'/>
                        </option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input type="submit" name="Update status">
    </form>
</c:if>

<c:choose>
    <c:when test="${reportTypeId=='F0103405'}">
        <%@ include file="/WEB-INF/jspf/taxForms/F0103405.jspf" %>
    </c:when>
</c:choose>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>
