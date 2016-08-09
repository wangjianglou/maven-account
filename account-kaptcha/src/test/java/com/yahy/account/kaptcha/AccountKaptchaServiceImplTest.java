package com.yahy.account.kaptcha;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yahy.account.kaptcha.inf.IAccountKaptchaService;

/** 
 * AccountKaptchaServiceImplTest.java
 * @author：sll
 * @date：2016年8月5日 上午1:51:22 
 * @version：1.0    
 */
public class AccountKaptchaServiceImplTest {
	private IAccountKaptchaService accountKaptchaService;
	
	@Before
	public void prepare() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-kaptcha.xml");
		accountKaptchaService = (IAccountKaptchaService)ctx.getBean("accountKaptchaService");
	}
	
	@Test
	public void testGenerateKaptcha() throws Exception {
		String key = accountKaptchaService.generateKatpchaKey();
		assertNotNull(key);
		
		byte[] kaptchaImage = accountKaptchaService.generateKatpchaImage(key);
		assertTrue(kaptchaImage.length>0);
		
		File image = new File("target/" + key +".jpg");
		OutputStream output = null;
		try {
			output = new FileOutputStream(image);
			output.write(kaptchaImage);
		} finally {
			if (output != null) {
				output.close();
			}
			
			assertTrue(image.exists() && image.length()>0);
		}
	}
	
	@Test
	public void testValidateKaptchaCorrect() throws Exception {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountKaptchaService.setPreDefinedTexts(preDefinedTexts);
		
		String key = accountKaptchaService.generateKatpchaKey();
		byte[] kaptchaImage = accountKaptchaService.generateKatpchaImage(key);
		
		File image = new File("target/" + key +".jpg");
		OutputStream output = null;
		try {
			output = new FileOutputStream(image);
			output.write(kaptchaImage);
		} finally {
			if (output != null) {
				output.close();
			}
			
		}
		assertTrue(accountKaptchaService.validateKatpcha(key, "12345"));
		
		key = accountKaptchaService.generateKatpchaKey();
		accountKaptchaService.generateKatpchaImage(key);
		assertTrue(accountKaptchaService.validateKatpcha(key, "abcde"));
	}
	
	@Test
	public void testValidateKaptchaIncorrect() throws Exception {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("123456");
		accountKaptchaService.setPreDefinedTexts(preDefinedTexts);
		
		String key = accountKaptchaService.generateKatpchaKey();
		accountKaptchaService.generateKatpchaImage(key);
		assertFalse(accountKaptchaService.validateKatpcha(key, "67890"));
	}
}
