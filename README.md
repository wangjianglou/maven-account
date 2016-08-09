# maven-account
学习《maven in action》时整理的书中的账户注册示例，稍微有些不完整；
注册信息-》保存账户-》发送激活邮件-》激活账户（包含代码，未调试）；
登录账户（未包含）；

1.acccount-parent是聚合工程，同时其pom也被其他模块继承。在此工程目录下执行mvn clean install可以对其他模块进行清理、编译、测试、安装本地仓库的操作；
2.acccount-email提供发送激活账户邮件的功能，需要使用开通了stmp服务的邮箱；
3.account-persist使用xml进行持久化；
4.account-kaptcha使用google的kaptcha生成验证码图片；
5.account-service封装底层功能，为account-web工程提供服务；
6.account-web提供账户注册web服务。

