package com.yahy.account.persist.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;

import com.yahy.account.persist.beans.Account;
import com.yahy.account.persist.exception.AccountPersistException;
import com.yahy.account.persist.inf.IAccountPersistService;

/** 
 * AccountPersistServiceImpl.java
 * @author：sll
 * @date：2016年8月2日 上午1:04:12 
 * @version：1.0    
 */
public class AccountPersistServiceImpl implements IAccountPersistService {
	private String filePath;
	private SAXReader saxreader = new SAXReader();
	private static final String ELEMENT_ROOT = "account-persist";
	private static final String ELEMENT_ACCOUNTS = "accounts";
	private static final String ELEMENT_ACCOUNT = "account";
	private static final String ELEMENT_ID = "id";
	private static final String ELEMENT_NAME = "name";
	private static final String ELEMENT_EMAIL = "email";
	private static final String ELEMENT_PASSWORD = "password";
	private static final String ELEMENT_ACTIVATED = "activated";

	public Account createAccount(Account account)
			throws AccountPersistException {
		Document document = readDocument();
		//定位元素
		Element accountElements = document.getRootElement().element(ELEMENT_ACCOUNTS);
		 //创建元素
	    Element accountElement = new BaseElement(ELEMENT_ACCOUNT);
	    //添加属性
	    Element eid = DocumentHelper.createElement("id");
	    eid.setText(account.getId());
	    accountElement.add(eid);
	    Element ename = DocumentHelper.createElement("name");
	    ename.setText(account.getName());
	    accountElement.add(ename);
	    Element eemail = DocumentHelper.createElement("email");
	    eemail.setText(account.getEmail());
	    accountElement.add(eemail);
	    Element epassword = DocumentHelper.createElement("password");
	    epassword.setText(account.getPassword());
	    accountElement.add(epassword);
	    Element eactivated = DocumentHelper.createElement("activated");
	    eactivated.setText(account.isActivated()?"true":"false");
	    accountElement.add(eactivated);
	    accountElements.add(accountElement);
	    this.writeDocument(document);
		return this.readAccount(account.getId());
	}

	@SuppressWarnings("unchecked")
	public Account readAccount(String id) throws AccountPersistException {
		Document document = readDocument();
		Account account = null;
		Element accountsElement = document.getRootElement().element(ELEMENT_ACCOUNTS);
		System.out.println("个数"+accountsElement.elements().size());
		for (Element element : (List<Element>)accountsElement.elements()) {
			System.out.println(element.elementText(ELEMENT_NAME));
			System.out.println(element.element(ELEMENT_ID));
			if (id.equals(element.element(ELEMENT_ID).getTextTrim())) {
				account = buildAccount(element);
			}
		}
		
		return account;
	}

	//修改内容有问题
	@SuppressWarnings("unchecked")
	public Account updateAccount(Account account)
			throws AccountPersistException {
		Document document = readDocument();
		Element accountsElement = document.getRootElement().element(ELEMENT_ACCOUNTS);
		System.out.println("个数"+accountsElement.elements().size());
		for (Element element : (List<Element>)accountsElement.elements()) {
			System.out.println(element.elementText(ELEMENT_NAME));
			System.out.println(element.element(ELEMENT_ID));
			if (account.getId().equals(element.element(ELEMENT_ID).getTextTrim())) {
				element.element("name").setText(account.getName());
				element.element("email").setText(account.getEmail());
				element.element("password").setText(account.getPassword());
				element.element("activated").setText(account.isActivated()?"true":"false");
			}
		}
		
		this.writeDocument(document);
		return this.readAccount(account.getId());
	}

	public void deleteAccount(String id) throws AccountPersistException {
		// TODO Auto-generated method stub
		System.out.println("暂未实现账户删除方法！");
	}
	
	/**
	 * 根据Element属性字段构建Account对象
	 * @param element
	 * @return
	 */
	private Account buildAccount(Element element) {
		Account account = new Account();
		account.setId(element.elementText(ELEMENT_ID));
		account.setName(element.elementText(ELEMENT_NAME));
		account.setPassword(element.elementText(ELEMENT_PASSWORD));
		account.setEmail(element.elementText(ELEMENT_EMAIL));
		account.setActivated(("true".equals(element.element(ELEMENT_ACTIVATED))?true:false));
		return account;
	}
	
	/**
	 * 读取持久化文件
	 * @return
	 * @throws AccountPersistException
	 */
	private Document readDocument() throws AccountPersistException {
		File dataFile = new File(filePath);
		if(!dataFile.exists()) {
			dataFile.getParentFile().mkdirs();
			Document document = DocumentHelper.createDocument();
	        Element root = document.addElement(ELEMENT_ROOT);
	        root.addElement(ELEMENT_ACCOUNTS);
	        writeDocument(document);
		}
        Document document;
		try {
			document = saxreader.read(new File(filePath));
		} catch (DocumentException e) {
			throw new AccountPersistException("读取持久化文件失败!");
		}
        return document;
	}
	
	/**
	 * 写入持久化文件
	 * @param document
	 * @throws AccountPersistException
	 */
	private void writeDocument(Document document) throws AccountPersistException {
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
			XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
			writer.write(document);
		} catch (IOException e) {
			throw new AccountPersistException("写入持久化文件失败！");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new AccountPersistException("关闭文件写入流失败！");
				}
			}
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
