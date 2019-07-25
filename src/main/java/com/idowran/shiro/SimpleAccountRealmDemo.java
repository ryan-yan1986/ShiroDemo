package com.idowran.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

public class SimpleAccountRealmDemo {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 1.获取安全管理器
		DefaultSecurityManager defaultSecurityManager = getSecurityManager();
		
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// 2.获取主体
		Subject subject = SecurityUtils.getSubject();
		// 待验证的token
		UsernamePasswordToken token = new UsernamePasswordToken("Tony", "123456");
		// 登录
		subject.login(token);
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
		
		// 检测角色，检测多个角色
		subject.checkRole("admin");
		subject.checkRoles("admin", "user");
		
		// 退出登录
		subject.logout();
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
	}
	
	public static DefaultSecurityManager getSecurityManager() {
		// 创建一个简单领域
		SimpleAccountRealm realm = new SimpleAccountRealm();
		// 添加一个账户，(账号，密码，多个角色)
		realm.addAccount("Tony", "123456", "admin", "user");
		
		// 1.构建安全管理器
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		// 2.设置领域
		defaultSecurityManager.setRealm(realm);
		
		return defaultSecurityManager;
	}
	
	
}
