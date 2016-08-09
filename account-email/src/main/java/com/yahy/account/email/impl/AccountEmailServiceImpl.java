package com.yahy.account.email.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.yahy.account.email.exception.AccountEmailException;
import com.yahy.account.email.inf.IAccountEmailService;

/** 
 * @author：sll 
 * @date：2016年7月31日 上午12:40:45 
 * @version：1.0    
 */
public class AccountEmailServiceImpl implements IAccountEmailService {
	private JavaMailSender javaMailSender;
	private String systemEmail;
	
	public void sendMail(String to, String subject, String htmlText)
			throws AccountEmailException {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg);
			
			
			msgHelper.setFrom(systemEmail);;
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);
			System.out.println("开始邮件发送！");
			javaMailSender.send(msg);
			System.out.println("邮件发送成功！");
		} catch (MailException e) {
			e.printStackTrace();
			throw new AccountEmailException("邮件发送失败", e);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new AccountEmailException("邮件发送失败", e);
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
