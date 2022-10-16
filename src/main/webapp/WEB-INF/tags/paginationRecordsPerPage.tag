<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageQuantity" required="true" type="java.lang.String" %>
<c:choose>
    <c:when test="${recordsPerPage == pageQuantity}">
        <option selected value="${pageQuantity}">${pageQuantity}</option>
    </c:when>
    <c:otherwise>
        <option value="${pageQuantity}">${pageQuantity}</option>
    </c:otherwise>
</c:choose>