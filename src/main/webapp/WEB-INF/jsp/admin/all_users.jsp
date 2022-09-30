<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="List orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<table style="width: 100%" id="main-container">


		<tr>
			<td>
				<%-- CONTENT --%>


				<table style="width: 100%">
					<thead>
						<tr>
							<td>№</td>
							<td align="center"><fmt:message key="user.first_name" /></td>
							<td align="center"><fmt:message key="user.last_name" /></td>
							<td align="center"><fmt:message key="user.login" /></td>
							<td align="center"><fmt:message key="user.phone" /></td>
							<td align="center"><fmt:message key="user.email" /></td>
							<td align="center"><fmt:message key="user.city" /></td>
							<td align="center"><fmt:message key="user.address" /></td>
							<td align="center"><fmt:message key="user.role" /></td>
						</tr>
					</thead>
					<tr>
						<td colspan="10"><hr></td>
					</tr>
					<c:forEach var="bean" items="${userList}">

						<tr>
							<td align="center">${bean.id}</td>
							<td align="center">${bean.firstName}</td>
							<td align="center">${bean.lastName}</td>
							<td align="center">${bean.login}</td>
							<td align="center">${bean.phone}</td>
							<td align="center">${bean.email}</td>
							<td align="center">${bean.city}</td>
							<td align="center">${bean.address}</td>
							<c:choose>
								<c:when test="${bean.id!=user.id}">
									<td align="center">
										<form action="controller">
											<input type="hidden" name="command" value="changeUserRole" />
											<input type="hidden" name="user_id" value="${bean.id}" /> <select
												name="role_id" onChange="this.form.submit();">
												<c:forEach var="role" items="${roleTypes}" varStatus="loop">
													<c:choose>
														<c:when test="${loop.index != bean.roleId}">
															<option value="${loop.index}">
																<fmt:message key='role.${role.name}' />
															</option>
														</c:when>
														<c:otherwise>
															<option selected value="${loop.index}"><fmt:message
																	key='role.${role.name}' />
															</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</form>
									</td>
								</c:when>
								<c:otherwise>
									<td align="center"><fmt:message
											key='role.${roleTypes[bean.roleId].name}' /></td>
								</c:otherwise>
							</c:choose>
						</tr>

					</c:forEach>
				</table> <%-- CONTENT --%>
			</td>
		</tr>



	</table>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>