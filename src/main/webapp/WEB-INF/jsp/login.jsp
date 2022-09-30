<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<body style="align: center">
	<script>
		$(function() {
			$("#log").buttonset();
			$("#loginBtn").button({});
			$("#registerBtn").button({});
		});
	</script>
	<table style="width: 100%; align: center">
		<tr>
			<td style="width: 100%" align="center" class="content center">

				<form class="cmxform" id="login_form" action="controller"
					method="post">

					<input type="hidden" name="command" value="login" />

					<fieldset>
						<legend>
							<fmt:message key="login_jsp.label.login" />
						</legend>
						<input name="login" required /><br />
					</fieldset>
					<br />
					<fieldset>
						<legend>
							<fmt:message key="login_jsp.label.password" />
						</legend>
						<input type="password" name="password" required />
					</fieldset>
					<br /> <span id="log"> <input type="submit"
						style="width: 200" id="loginBtn"
						value='<fmt:message key="login_jsp.button.login"/>'> <input
						type="submit" id="registerBtn" style="width: 200"
						value='<fmt:message key="login_jsp.button.registration"/>'></span>
				</form>

			</td>
		</tr>



	</table>
	<script>
		$("#login_form").validate();
	</script>
</body><%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>