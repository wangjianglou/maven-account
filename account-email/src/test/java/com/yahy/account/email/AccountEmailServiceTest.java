package com.yahy.account.email;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.yahy.account.email.inf.IAccountEmailService;

/** 
 * AccountEmailServiceTest.java
 * @author：sll
 * @date：2016年7月31日 下午4:40:28 
 * @version：1.0    
 */
public class AccountEmailServiceTest {
	private GreenMail greenMail;
	
	@Before
	public void startMailServer() throws Exception {
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("test", "xxx");
		greenMail.start();
		System.out.println("greenmail 启动!");
	}
	
	@Ignore
	@Test
	public void testSendMail() throws Exception {
		System.out.println("greenmail 测试发送开始!");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		IAccountEmailService accountEmailService = (IAccountEmailService)ctx.getBean("accountEmailService");
		
		String htmlText = "<h3>Test</h3>";
		String subject = "song，你好";
		accountEmailService.sendMail("test1@mail.extern", subject, htmlText);
		
		greenMail.waitForIncomingEmail(2000, 1);
		
		Message[] msgs = greenMail.getReceivedMessages();
		assertEquals(1, msgs.length);
		assertEquals(subject, msgs[0].getSubject());
		assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
		System.out.println("greenmail 测试发送结束!");
	}
	
	@After
	public void stopMailServer() throws Exception {
		greenMail.stop();
		System.out.println("greenmail 关闭!");
	}
}
