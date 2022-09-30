<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<script>
		$(function() {
			$("#cancel").button({});
			$("#update").button({});
		});
	</script>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<form id="settings_form" action="controller" method="post">
		<table Style="width: 100%">
			<tr>
				<td align="right"><input type="hidden" name="command"
					value="viewSettings" /> <input type="hidden" name="save"
					value="true"> <label for="first_name"><fmt:message
							key="user.first_name" /></label></td>
				<td><input id="first_name" value="${user.firstName}"
					name="first_name"></td>
			</tr>
			<tr>
				<td align="right"><label for="last_name"><fmt:message
							key="user.last_name" /></label></td>
				<td><input id="last_name" value="${user.lastName}"
					name="first_name"></td>
			</tr>
			<tr>
				<td align="right"><label for="phone"><fmt:message
							key="user.phone" /></label></td>
				<td><input id="phone" value="${user.phone}" name="phone"></td>
			</tr>
			<tr>
				<td align="right"><label for="email"><fmt:message
							key="user.email" /></label></td>
				<td><input id="last_nemailame" value="${user.email}"
					name="email" required></td>
			</tr>
			<tr>
				<td align="right"><label for="city"><fmt:message
							key="user.city" /></label></td>
				<td><input id="city" value="${user.city}" name=city></td>
			</tr>
			<tr>
				<td align="right"><label for="address"><fmt:message
							key="user.address" /></label></td>
				<td><input id="address" value="${user.address}" name="address"></td>
			</tr>
			<tr>
				<td align="right"><label for="password"><fmt:message
							key="settings_jsp.current_password" />*</label></td>
				<td><input id="password" name="password" required></td>
			</tr>
			<tr>
				<td align="right"><label for="password"><fmt:message
							key="user.password" /></label></td>
				<td><input id="password" name="password"></td>
			</tr>
			<tr>
				<td>${error}</td>
			</tr>
			<tr>
				<td align="right"><input style="width: 150" id="update"
					type="submit"
					value='<fmt:message key="settings_jsp.button.update"/>'></td>

				<td align="left"><a style="width: 150" id="cancel"
					href="controller?command=viewSettings"><fmt:message
							key="settings_jsp.button.cancel" /></a></td>
			</tr>
		</table>
	</form>
	<%-- CONTENT --%>



	<script>
		$("#settings_form").validate();
	</script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>