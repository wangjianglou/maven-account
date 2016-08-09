package com.yahy.account.service.beans;
/** 
 * SignupResuest.java
 * @author：sll
 * @date：2016年8月6日 下午10:25:26 
 * @version：1.0    
 */
public class SignupResuest {
 private String id;
 private String name;
 private String email;
 private String password;
 private String confirmPassword;
 private String kaptchaKey;
 private String kaptchaText;
 private String activateServiceUrl;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}
public String getKaptchaKey() {
	return kaptchaKey;
}
public void setKaptchaKey(String kaptchaKey) {
	this.kaptchaKey = kaptchaKey;
}
public String getKaptchaText() {
	return kaptchaText;
}
public void setKaptchaText(String kaptchaText) {
	this.kaptchaText = kaptchaText;
}
public String getActivateServiceUrl() {
	return activateServiceUrl;
}
public void setActivateServiceUrl(String activateServiceUrl) {
	this.activateServiceUrl = activateServiceUrl;
}
 
 
}
