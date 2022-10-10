<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="propertyName" required="true" type="java.lang.String" %>
<%@ attribute name="propertyType" required="true" type="java.lang.String" %>
<%@ attribute name="propertyClass" required="false" type="java.lang.String" %>
<%@ attribute name="readonly" required="false" type="java.lang.Boolean" %>

<input id=${propertyName} name=${propertyName} type=${propertyType}

<c:if test='${not empty requestScope.get(propertyName)}'>
<c:if test='${propertyType.equals("text")}'>
        value="${requestScope.get(propertyName)}"
</c:if>
<c:if test='${propertyType.equals("number")}'>
       value="${requestScope.get(propertyName)}"
</c:if>
<c:if test='${propertyType.equals("checkbox")}'>
       checked
</c:if>
</c:if>
<c:if test='${not empty propertyClass}'>
       class="${propertyClass}"
</c:if>
<c:if test='${not empty readonly}'>
       readonly
</c:if>
<c:if test='${reportStatus.id=="2" || reportStatus.id=="3" || reportStatus.id=="4"}'>
       readonly
</c:if>
<%--<c:if test='${userRole.name!="user"}'>--%>
<%--       readonly--%>
<%--</c:if>--%>

>