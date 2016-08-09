<%@page import="com.yahy.account.service.inf.IAccountService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
IAccountService accountService = (IAccountService)context.getBean("accountService");
String kaptchaKey = accountService.generateKapchaKey();
%>
<h2>注册新账户</h2>
<form name="signup" action="Signup" method="post">
<table border="1">
	<tr>
		<td><label>账户ID：</label></td><td><input type="text" name="id"></input></td>
	</tr>
	<tr>
				<td><label>Email:</label></td><td><input type="text" name="email"></input></td>
	</tr>
	<tr>
		<td><label>显示名称：</label></td><td><input type="text" name="name"></input></td>
	</tr>
	<tr>
		<td><label>密码：</label></td><td><input type="text" name="password"></input></td>
	</tr>
	<tr>
		<td><label>确认密码：</label></td><td><input type="text" name="confirm_pasword"></input></td>
	</tr>
	<tr>
		<td><label>验证码：</label></td><td><input type="text" name="kaptcha_value"></input></td>
	</tr>
	<tr><td></td><td><img src="<%=request.getContextPath()%>/KaptchaImage?key=<%=kaptchaKey%>"/></td></tr>
</table>
	<input type="hidden" name="kaptcha_key" value="<%=kaptchaKey%>"/><br/>
	<button>确认并提交</button>
</form>
</body>
</html>