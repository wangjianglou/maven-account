package com.yahy.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.yahy.account.email.exception.AccountEmailException;
import com.yahy.account.email.inf.IAccountEmailService;
import com.yahy.account.kaptcha.exception.AccountKaptchaException;
import com.yahy.account.kaptcha.impl.RandomGenerator;
import com.yahy.account.kaptcha.inf.IAccountKaptchaService;
import com.yahy.account.persist.beans.Account;
import com.yahy.account.persist.exception.AccountPersistException;
import com.yahy.account.persist.inf.IAccountPersistService;
import com.yahy.account.service.beans.SignupResuest;
import com.yahy.account.service.exception.AccountServiceException;
import com.yahy.account.service.inf.IAccountService;

/** 
 * AccountServiceImpl.java
 * @author：sll
 * @date：2016年8月6日 下午11:31:42 
 * @version：1.0    
 */
public class AccountServiceImpl implements IAccountService {
	private IAccountPersistService accountPersistService;
	private IAccountEmailService accountEmailService;
	private IAccountKaptchaService accountKaptchaService;
	private Map<String, String> activateMap = new HashMap<String, String>();

	public String generateKapchaKey() throws AccountServiceException {
		try {
			String kaptchaKey = accountKaptchaService.generateKatpchaKey();
			return kaptchaKey;
		} catch (AccountKaptchaException e) {
			throw new AccountServiceException("验证码KEY生成失败！", e);
		}
	}

	public byte[] generateKaptchaImage(String kaptchaKey)
			throws AccountServiceException {
		try {
			byte[] imageBytes = accountKaptchaService.generateKatpchaImage(kaptchaKey);
			return imageBytes;
		} catch (AccountKaptchaException e) {
			throw new AccountServiceException("验证码图片生成失败！验证码KEY:"+kaptchaKey, e);
		}
	}

	public void signUp(SignupResuest signupResuest)
			throws AccountServiceException {
		try {
			if (!signupResuest.getConfirmPassword().equals(signupResuest.getPassword())) {
				throw new AccountServiceException("注册失败：密码与确认密码不一致！");
			}
			if (!accountKaptchaService.validateKatpcha(signupResuest.getKaptchaKey(), signupResuest.getKaptchaText())) {
				throw new AccountServiceException("注册失败：所输入验证码不正确！");
			}
			
			Account account = new Account();
			account.setId(signupResuest.getId());
			account.setName(signupResuest.getName());
			account.setPassword(signupResuest.getPassword());
			account.setEmail(signupResuest.getEmail());
			account.setActivated(false);
			accountPersistService.createAccount(account);
			
			String activateId = RandomGenerator.getRandomString();
			activateMap.put(account.getId(), activateId);
			System.out.println("signupResuest.getActivateServiceUrl():"+signupResuest.getActivateServiceUrl());
			String link = signupResuest.getActivateServiceUrl().endsWith("/")?signupResuest.getActivateServiceUrl()+activateId:signupResuest.getActivateServiceUrl()+"?key="+activateId;
			accountEmailService.sendMail(account.getEmail(), "测试：账户激活链接", link);
		} catch (AccountKaptchaException e) {
			throw new AccountServiceException("注册失败：验证码校验失败！", e);
		} catch (AccountPersistException e) {
			throw new AccountServiceException("注册失败：账户保存失败！", e);
		} catch (AccountEmailException e) {
			throw new AccountServiceException("注册失败：激活邮件发送失败！", e);
		}
		
	}

	public void activate(String activationNumber)
			throws AccountServiceException {
		// TODO Auto-generated method stub
		
	}

	public void login(String id, String password)
			throws AccountServiceException {
		try {
			Account account = accountPersistService.readAccount(id);
			if (null == account) {
				throw new AccountServiceException("登录失败：编号【"+id+"】对应用户不存在！");
			}
			if (!account.isActivated()) {
				throw new AccountServiceException("登录失败：账户未激活！");
			}
			if (!password.equals(account.getPassword())) {
				throw new AccountServiceException("登录失败：账户密码不正确！");
			}
		} catch (AccountPersistException e) {
			throw new AccountServiceException("登录失败！", e);
		}
		
	}

	public IAccountPersistService getAccountPersistService() {
		return accountPersistService;
	}

	public void setAccountPersistService(
			IAccountPersistService accountPersistService) {
		this.accountPersistService = accountPersistService;
	}

	public IAccountEmailService getAccountEmailService() {
		return accountEmailService;
	}

	public void setAccountEmailService(IAccountEmailService accountEmailService) {
		this.accountEmailService = accountEmailService;
	}

	public IAccountKaptchaService getAccountKaptchaService() {
		return accountKaptchaService;
	}

	public void setAccountKaptchaService(
			IAccountKaptchaService accountKaptchaService) {
		this.accountKaptchaService = accountKaptchaService;
	}
	
	
		
}
