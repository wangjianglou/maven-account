package com.yahy.account.email.inf;

import com.yahy.account.email.exception.AccountEmailException;

public interface IAccountEmailService {
	/**
	 * 发送html格式邮件
	 * @param to 接收地址
	 * @param subject 邮件主题
	 * @param htmlText 邮件内容
	 * @throws AccountEmailException
	 */
	void sendMail(String to, String subject, String htmlText)
	throws AccountEmailException;
}
