package com.yahy.account.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yahy.account.kaptcha.impl.RandomGenerator;
import com.yahy.account.kaptcha.inf.IAccountKaptchaService;
import com.yahy.account.persist.beans.Account;
import com.yahy.account.persist.inf.IAccountPersistService;
import com.yahy.account.service.beans.SignupResuest;
import com.yahy.account.service.exception.AccountServiceException;
import com.yahy.account.service.inf.IAccountService;

/** 
 * AccountServiceTest.java
 * @author：sll
 * @date：2016年8月7日 上午1:49:49 
 * @version：1.0    
 */
public class AccountServiceTest {
	private IAccountService accountservice;
	private IAccountKaptchaService accountKaptchaService;
	private IAccountPersistService accountPersistService;
	private SignupResuest signupResuest;
	
	@Ignore
	public void prepare() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:account-service.xml","classpath:account-kaptcha.xml","classpath:account-email.xml","classpath:account-persist.xml"});
		accountservice = (IAccountService)ctx.getBean("accountService");
		accountKaptchaService = (IAccountKaptchaService)ctx.getBean("accountKaptchaService");
		accountPersistService = (IAccountPersistService)ctx.getBean("accountPersistService"); 
		
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountKaptchaService.setPreDefinedTexts(preDefinedTexts);
		String key = RandomGenerator.getRandomString();
		
		signupResuest = new SignupResuest();
		signupResuest.setId("6868");
		signupResuest.setName("sll");
		signupResuest.setPassword("123456");
		signupResuest.setEmail("song_abc@qq.com");
		signupResuest.setKaptchaKey(key);
		signupResuest.setKaptchaText("12345");
	}
	
	@Ignore
	public void signUp() throws Exception {
		accountservice.signUp(signupResuest);
		Account account = accountPersistService.readAccount("6868");
		assertNotNull(account);
		assertEquals("sll", account.getName());
		assertEquals("123456", account.getPassword());
		assertEquals("song_abc@qq.com", account.getEmail());
	}
	
}
