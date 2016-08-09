package com.yahy.account.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yahy.account.service.exception.AccountServiceException;
import com.yahy.account.service.inf.IAccountService;

/**
 * Servlet implementation class KaptchaImageServlet
 */
public class KaptchaImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	private ApplicationContext context;
	
	@Override
	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		if (key == null || key.length() == 0) {
			response.sendError(400, "未找到对应验证码KEY!");
		} else {
			IAccountService accountService = (IAccountService)context.getBean("accountService");
			response.setContentType("image/jpeg");
			OutputStream out = response.getOutputStream();
			try {
				out.write(accountService.generateKaptchaImage(key));
				out.close();
			} catch (AccountServiceException e) {
				response.sendError(404, e.getMessage());
			}
		}
	}

}
