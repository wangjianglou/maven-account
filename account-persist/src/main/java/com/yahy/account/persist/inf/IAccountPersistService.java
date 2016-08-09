package com.yahy.account.persist.inf;

import com.yahy.account.persist.beans.Account;
import com.yahy.account.persist.exception.AccountPersistException;

/** 
 * IAccountPersistService.java
 * @author：sll
 * @date：2016年8月2日 上午1:01:32 
 * @version：1.0    
 */
public interface IAccountPersistService {
	Account createAccount(Account account) throws AccountPersistException;
	Account readAccount(String id) throws AccountPersistException;
	Account updateAccount(Account account) throws AccountPersistException;
	void deleteAccount(String id) throws AccountPersistException;
}
