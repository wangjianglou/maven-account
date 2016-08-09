package com.yahy.account.kaptcha;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import com.yahy.account.kaptcha.impl.RandomGenerator;

/** 
 * RandomGeneratorTest.java
 * @author：sll
 * @date：2016年8月5日 上午1:44:34 
 * @version：1.0    
 */
public class RandomGeneratorTest {
	@Test
	public void testGetRandomString() {
		HashSet<String> container = new HashSet<String>();
		for(int i=0; i<100; ++i) {
			String randString = RandomGenerator.getRandomString();
			assertFalse(container.contains(randString));
			container.add(randString);
		}
		
		return;
	}
}
