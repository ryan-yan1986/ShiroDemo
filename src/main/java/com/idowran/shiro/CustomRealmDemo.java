package com.idowran.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import com.idowran.shiro.realm.CustomRealm;

public class CustomRealmDemo {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CustomRealm customRealm = new CustomRealm();
		// 创建一个加密对象
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");// 加密算法的名称
		matcher.setHashIterations(1);		// 加密次数
		
		// 设置加密对象
		customRealm.setCredentialsMatcher(matcher);
		
		// 1.获取安全管理器
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// 2.获取主体
		Subject subject = SecurityUtils.getSubject();
		// 待验证的token
		UsernamePasswordToken token = new UsernamePasswordToken("Tony", "258369");
		// 登录
		subject.login(token);
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
		
		// 检测角色，检测多个角色
		subject.checkRole("admin");
		// 检测权限
		subject.checkPermissions("user:delete", "user:select");
		
		// 退出登录
		subject.logout();
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
	}
}
