package com.yahy.account.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yahy.account.service.beans.SignupResuest;
import com.yahy.account.service.exception.AccountServiceException;
import com.yahy.account.service.inf.IAccountService;

/**
 * Servlet implementation class SignupServlet
 */
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationContext context;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@ Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirm_pasword");
		String kaptchaKey = request.getParameter("kaptcha_key");
		String kaptchaValue = request.getParameter("kaptcha_value");
		
		if (id == null || id.length() == 0 ||
			email == null || email.length() == 0 ||
			name == null || name.length() == 0 ||
			password == null || password.length() == 0 ||
			confirmpassword == null || confirmpassword.length() == 0 ||
			kaptchaKey == null || kaptchaKey.length() == 0 ||
			kaptchaValue == null || kaptchaValue.length() == 0) {
			response.sendError(400, "参数不完整！");
			return;
		}
		
		IAccountService accountService = (IAccountService)context.getBean("accountService");
		SignupResuest signupResuest = new SignupResuest();
		signupResuest.setId(id);
		signupResuest.setName(name);
		signupResuest.setEmail(email);
		signupResuest.setPassword(password);
		signupResuest.setConfirmPassword(confirmpassword);
		signupResuest.setKaptchaKey(kaptchaKey);
		signupResuest.setKaptchaText(kaptchaValue);
		signupResuest.setActivateServiceUrl(getServletContext().getRealPath("/")+"activate");
		
		try {
			accountService.signUp(signupResuest);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("账户已经创建，请查收邮件进行激活！");
		} catch (AccountServiceException e) {
			response.sendError(400, e.getMessage());
			return;
		}
	}

}
