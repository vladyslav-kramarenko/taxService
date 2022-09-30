<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="registration" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<style>
input {
	align: center
}
</style>
<body style="align: center">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<script>
		$(function() {
			$("input[type=submit], button").button();
		});
	</script>

	<form id="registration_form" action="controller" method="post">
		<input type="hidden" name="command" value="registration" />

		<fieldset>
			<legend>
				<fmt:message key="user.login" />
				*
			</legend>
			<input name="login" required /><br />
		</fieldset>
		<br />
		<fieldset>
			<legend >
				<fmt:message key="user.password" />
				*
			</legend>
			<input type="password" name="password" required />
		</fieldset>
		<br />
		<fieldset>
			<legend>
				<fmt:message key="user.email" />
				*
			</legend>
			<input type="email" name="email" required />
		</fieldset>
		<br />
		<fieldset>
			<legend>
				<fmt:message key="user.first_name" />
			</legend>
			<input type="text" name="name" />
		</fieldset>
		<br />
		<fieldset>
			<legend>
				<fmt:message key="user.last_name" />
			</legend>
			<input type="text" name="surname" />
		</fieldset>
		<fieldset>
			<legend>
				<fmt:message key="user.phone" />
			</legend>
			<input type="text" name="phone" />
		</fieldset>
		<fieldset>
			<legend>
				<fmt:message key="user.city" />
			</legend>
			<input type="text" name="city" />
		</fieldset>
		<br />
		<fieldset>
			<legend>
				<fmt:message key="user.address" />
			</legend>
			<input type="text" name="address" />
		</fieldset>
		<br /> <input style="width: 200; align: center" type="submit"
			value='<fmt:message key="registration_jsp.button.registration"/>'>
	</form>

	<script>
		$("#commentForm").validate();
	</script>




</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>