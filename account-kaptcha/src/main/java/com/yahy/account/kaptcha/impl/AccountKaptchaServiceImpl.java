package com.yahy.account.kaptcha.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yahy.account.kaptcha.exception.AccountKaptchaException;
import com.yahy.account.kaptcha.inf.IAccountKaptchaService;

/** 
 * AccountKaptchaServiceImpl.java
 * @author：sll
 * @date：2016年8月5日 上午1:00:58 
 * @version：1.0    
 */
public class AccountKaptchaServiceImpl implements IAccountKaptchaService,
		InitializingBean {
	// 验证码字典
	private Map<String, String> katpchaMap = new HashMap<String, String>();
	// 默认验证码生成器
	private DefaultKaptcha producer;
	// 预定义values,主要用于测试
	private List<String> preDefinedTexts;
	// 循环遍历preDefinedTexts
	private int textCount = 0;

	// springframe 初始化此类对象后调用
	public void afterPropertiesSet() throws Exception {
		System.out.println("开始初始化验证码生成器！");
		producer = new DefaultKaptcha();
		producer.setConfig(new Config(new Properties()));
		System.out.println("结束初始化验证码生成器！");
	}

	public String generateKatpchaKey() throws AccountKaptchaException {
		String key = RandomGenerator.getRandomString();
		String value = this.getKaptchaText();
		System.out.println("key:"+key+", value:"+value);
		katpchaMap.put(key, value);
		return key;
	}

	public byte[] generateKatpchaImage(String kaptchaKey)
			throws AccountKaptchaException {
		String text = katpchaMap.get(kaptchaKey);
		if (text == null) {
			throw new AccountKaptchaException("Katpcha key :"+kaptchaKey +" not found!");
		}
		
		BufferedImage image = producer.createImage(text);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			throw new AccountKaptchaException("Failed to write kaptcha stream!", e);
		}
		
		return out.toByteArray();
	}

	public boolean validateKatpcha(String kaptchaKey, String kaptchaValue)
			throws AccountKaptchaException {
		String text = katpchaMap.get(kaptchaKey);
		if (text == null) {
			throw new AccountKaptchaException("Katpcha key :"+kaptchaKey +" not found!");
		}
		
		if (text.equals(kaptchaValue)) {
			katpchaMap.remove(kaptchaKey);
			return true;
		} else {
			return false;
		}
	}

	public List<String> getPreDefinedTexts() {
		return preDefinedTexts;
	}

	public void setPreDefinedTexts(List<String> preDefinedTexts) {
		this.preDefinedTexts = preDefinedTexts;
	}
	
	private String getKaptchaText() {
		String text = null;
		if (preDefinedTexts!=null && !preDefinedTexts.isEmpty()) {
			text = preDefinedTexts.get(textCount);
			textCount = (textCount+1) % preDefinedTexts.size();
		} else {
			text = producer.createText();
		}
		
		return text;
	}
	

}
