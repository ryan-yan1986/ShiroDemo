package com.idowran.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcReamlDemo {
	
	static DruidDataSource dataSource = new DruidDataSource();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 1.创建JDBC领域
		JdbcRealm jdbcRealm = new JdbcRealm();
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/Shiro");
		dataSource.setUsername("root");
		dataSource.setPassword("my123456");
		// 设置数据源
		jdbcRealm.setDataSource(dataSource);
		// 设置权限开关，否则不能检测权限
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		// 自定义查询用户的sql语句
		String sqlUser = "select password from my_user where username = ?";
		jdbcRealm.setAuthenticationQuery(sqlUser);
		// 自定义查询用户角色的语句
//		String sqlRole = "select role_name from my_user_roles where username = ?";
//		jdbcRealm.setUserRolesQuery(sqlRole);;
		
		// 1.窗前安全管理器
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		// 设置领域
		defaultSecurityManager.setRealm(jdbcRealm);
		
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// 2.获取主体
		Subject subject = SecurityUtils.getSubject();
		// 待验证的token
		UsernamePasswordToken token = new UsernamePasswordToken("Tony", "147258");
		// 登录
		subject.login(token);
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
		
		// 检测角色，检测多个角色
		subject.checkRole("admin");
		// 要设置权限开关，这个地方才能正确检测权限
		subject.checkPermission("user:select");
		
		// 退出登录
		subject.logout();
		System.out.println("isAuthenticated: " + subject.isAuthenticated());
	}
}
