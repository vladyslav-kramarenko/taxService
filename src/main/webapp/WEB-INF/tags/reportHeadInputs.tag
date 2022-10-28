<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="reportTypeId" required="true" type="java.lang.String" %>

<input type="hidden" name="reportId" value="${reportId}"/>
<input type="hidden" name="command" value="submitReport"/>
<input type="hidden" name="reportTypeId" value="${reportTypeId}"/>
