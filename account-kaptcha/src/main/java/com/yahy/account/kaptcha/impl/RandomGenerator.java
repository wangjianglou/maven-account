package com.yahy.account.kaptcha.impl;

import java.util.Random;

/** 
 * RandomGenerator.java
 * @author：sll
 * @date：2016年8月5日 上午12:51:49 
 * @version：1.0    
 */
public class RandomGenerator {
	public static String range = "0123456789abcdefghijklmnopqrstuvwxyz";
	public static synchronized String getRandomString() {
		StringBuffer random = new StringBuffer();
		for(int i=0; i<8; i++) {
			random.append(range.charAt(new Random().nextInt(range.length())));
		}
		
		return random.toString();
	}
}
