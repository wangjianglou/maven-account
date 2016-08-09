package com.yahy.account.service.inf;

import com.yahy.account.service.beans.SignupResuest;
import com.yahy.account.service.exception.AccountServiceException;

/** 
 * IAccountService.java
 * @author：sll
 * @date：2016年8月6日 下午10:17:35 
 * @version：1.0    
 */
public interface IAccountService {
	/**
	 * 生成验证码KEY
	 * @return
	 * @throws AccountServiceException
	 */
	String generateKapchaKey() throws AccountServiceException;
	/**
	 * 根据key生成验证码图片
	 * @param kaptchaKey
	 * @return
	 * @throws AccountServiceException
	 */
	byte[] generateKaptchaImage(String kaptchaKey) throws AccountServiceException;
	/**
	 * 注册用户
	 * @param signupResuest
	 * @throws AccountServiceException
	 */
	void signUp(SignupResuest signupResuest) throws AccountServiceException;
	/**
	 * 激活用户
	 * @param activationNumber
	 * @throws AccountServiceException
	 */
	void activate(String activationNumber) throws AccountServiceException;
	/**
	 * 登录
	 * @param id
	 * @param password
	 * @throws AccountServiceException
	 */
	void login(String id, String password) throws AccountServiceException;
}
