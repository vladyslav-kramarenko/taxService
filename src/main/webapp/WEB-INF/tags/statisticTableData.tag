<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="commandLink" required="true" type="java.lang.String" %>

<c:forEach var="statisticItem" items="${paginationList}">
    <tr>
        <td>${statisticItem.id}</td>
        <td><a href="${commandLink}=${userItem.name}">${statisticItem.name}</a>
        </td>
        <c:forEach var="status" items="${statusMap}" varStatus="loop">
            <td>
                <c:forEach var="statusValue" items="${statisticItem.statistic}" varStatus="loop">
                    <c:choose>
                        <c:when test="${statusValue.key==status.value}">
                            ${statusValue.value}
                        </c:when>
                    </c:choose>
                </c:forEach>
            </td>
        </c:forEach>
    </tr>
</c:forEach>