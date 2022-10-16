<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageCommand" required="true" type="java.lang.String" %>

<div class="pagination_section">
    <c:if test="${selectedPage != 1}">
        <a href="controller?command=${pageCommand.trim()}
        &selectedPage=${selectedPage - 1}
        &recordsPerPage=${recordsPerPage}">
            << Previous
        </a>
    </c:if>
    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${selectedPage == i}">
                <a href="controller?command=${pageCommand.trim()}
                &selectedPage=${i}
                &recordsPerPage=${recordsPerPage}"
                   class="active">${i}
                </a>
            </c:when>
            <c:otherwise>
                <a href="controller?command=${pageCommand.trim()}
                &selectedPage=${i}
                &recordsPerPage=${recordsPerPage}">
                        ${i}
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${selectedPage < noOfPages}">
        <a href="controller?command=${pageCommand}
        &selectedPage=${selectedPage + 1}
        &recordsPerPage=${recordsPerPage}">
            Next >>
        </a>
    </c:if>
</div>