package com.yahy.account.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yahy.account.persist.beans.Account;
import com.yahy.account.persist.inf.IAccountPersistService;

/** 
 * AccountPersistServiceTest.java
 * @author：sll
 * @date：2016年8月3日 下午11:56:07 
 * @version：1.0    
 */
public class AccountPersistServiceTest {
	private IAccountPersistService accountPersistService;
	
	@Before
	public void prepared() throws Exception {
		File persistDataFile = new File("target/test-class/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
		accountPersistService = (IAccountPersistService)ctx.getBean("accountPersistService");
		
		Account account = new Account();
		account.setId("5858");
		account.setName("yahy");
		account.setEmail("test@qq.com");
		account.setPassword("testPassword");
		account.setActivated(false);
		
		accountPersistService.createAccount(account);
	}
	
	@Ignore
	@Test
	public void testReadAccount() throws Exception {
		Account account = accountPersistService.readAccount("5858");
		assertNotNull(account);
		assertEquals("5858", account.getId());
		assertEquals("yahy", account.getName());
		assertEquals("test@qq.com", account.getEmail());
		assertEquals("testPassword", account.getPassword());
		assertFalse(account.isActivated());
	}
	
	@Ignore
	public void testUpdateAccount() throws Exception {
		Account account = accountPersistService.readAccount("5858");
		account.setName("updateYahy");
		account.setActivated(true);
		account = accountPersistService.updateAccount(account);
		assertNotNull(account);
		assertEquals("5858", account.getId());
		assertEquals("updateYahy", account.getName());
		assertEquals("test@qq.com", account.getEmail());
		assertEquals("testPassword", account.getPassword());
		assertFalse(!account.isActivated());

	}

}
