package com.idowran.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class IniRealmDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IniRealm iniRealm = new IniRealm("classpath:user.ini");
		
		// 1.获取安全管理器
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(iniRealm);
		
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
		// 检测权限
		subject.checkPermission("user:delete");
//		subject.checkRoles("admin", "user");
		
		// 退出登录
		subject.logout();
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
	}

}
