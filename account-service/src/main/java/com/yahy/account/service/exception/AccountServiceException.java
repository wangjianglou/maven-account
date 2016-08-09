package com.yahy.account.service.exception;
/** 
 * AccountServiceException.java
 * @author：sll
 * @date：2016年8月6日 下午10:15:36 
 * @version：1.0    
 */
public class AccountServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6441006661939330691L;

	public AccountServiceException() {
		// TODO Auto-generated constructor stub
	}

	public AccountServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AccountServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
