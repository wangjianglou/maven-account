package com.yahy.account.kaptcha.inf;

import java.util.List;

import com.yahy.account.kaptcha.exception.AccountKaptchaException;

/** 
 * IAccountCaptchaService.java
 * @author：sll
 * @date：2016年8月5日 上午12:39:52 
 * @version：1.0    
 */
public interface IAccountKaptchaService {
	/**
	 * 生成随机验证码主键
	 * @return
	 * @throws AccountKaptchaException
	 */
	public String generateKatpchaKey() throws AccountKaptchaException;
	/**
	 * 根据验证码主键生成验证码图片
	 * @param kaptchaKey
	 * @return
	 * @throws AccountKaptchaException
	 */
	public byte[] generateKatpchaImage(String kaptchaKey) throws AccountKaptchaException;
	/**
	 * 校验页面传入的验证码主键和用户输入的验证码值
	 * @param kaptchaKey
	 * @param kaptchaValue
	 * @return
	 * @throws AccountKaptchaException
	 */
	public boolean validateKatpcha(String kaptchaKey, String kaptchaValue) throws AccountKaptchaException;
	
	/**
	 * 针对测试设置指定的验证码内容
	 * @param preDefinedTexts
	 */
	public void setPreDefinedTexts(List<String> preDefinedTexts);
	
}
