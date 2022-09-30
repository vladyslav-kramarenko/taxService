<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Info"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<body>

<form action="controller" class="cmxform" id="loadXML_form"  method="post" enctype="multipart/form-data">
<c:if test="${reportStatus}==created"></c:if>
    <fieldset>
        <legend>Load XML</legend>
        <input name="file" type="file"/>
        <input type="hidden" name="command" value='loadXML'/>
        <input type="submit" id="loadBtn" value='Load'/>
    </fieldset>
</form>

<%@ include file="/WEB-INF/jspf/taxForms/F0103405.jspf" %>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>
