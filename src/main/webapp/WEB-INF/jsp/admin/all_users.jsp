<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="List orders" scope="page"/>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<table>
	<thead>
	<tr class="header">
		<td>№</td>
		<td><fmt:message key="user.first_name"/></td>
		<td><fmt:message key="user.last_name"/></td>
		<td><fmt:message key="user.phone"/></td>
		<td><fmt:message key="user.email"/></td>
		<td><fmt:message key="user.role"/></td>
	</tr>
	</thead>
	<c:forEach var="bean" items="${userList}">
		<tr>
			<td>${bean.id}</td>
			<td>${bean.firstName}</td>
			<td>${bean.lastName}</td>
			<td>${bean.phone}</td>
			<td>${bean.email}</td>
			<c:choose>
				<c:when test="${bean.id!=user.id}">
					<td>
						<form action="controller">
							<input type="hidden" name="command" value="changeUserRole"/>
							<input type="hidden" name="user_id" value="${bean.id}"/>
							<select name="role_id" onChange="this.form.submit();">
								<c:forEach var="role" items="${roleMap}" varStatus="loop">
									<c:choose>
										<c:when test="${role.key != bean.roleId}">
											<option value="${loop.index}">
												<fmt:message key='role.${role.value.name}'/>
											</option>
										</c:when>
										<c:otherwise>
											<option selected value="${role.key}"><fmt:message
													key='role.${role.value.name}'/>
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</form>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						<fmt:message key='role.${roleMap.get(bean.roleId).name}'/></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
</table>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</html>