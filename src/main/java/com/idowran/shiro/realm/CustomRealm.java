package com.idowran.shiro.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealm extends AuthorizingRealm{
	
	static String SALT = "tony";
	
	{
		
		super.setName("CustomRealm");
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		// 用来做授权
			
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 获取角色数据
		Set<String> roles = getRolesByUsername(username);
		authorizationInfo.setRoles(roles);
		// 获取权限数据
		Set<String> permissions = getPermissionByUsername(username);
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}
	
	private Set<String> getPermissionByUsername(String username) {
		Set<String> permissions = new HashSet<>();
		permissions.add("user:delete");
		permissions.add("user:select");
		return permissions;
	}

	/**
	 * 0.根据用户名获取角色，模拟数据库操作
	 * @param username
	 * @return
	 */
	private Set<String> getRolesByUsername(String username) {
		// TODO Auto-generated method stub
		Set<String> roles = new HashSet<>();
		roles.add("admin");
		roles.add("user");
		return roles;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		// 用来做认证
		// 1.从主体传过来的信息中，获得用户名
		String username = (String)token.getPrincipal();
		
		// 2.通过用户名到数据库中获取凭证
		String password = getPasswordByUsername(username);
		if (password == null) {
			return null;
		}
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "CustomRealm");
		// 设置盐
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(SALT));
		
		return authenticationInfo;
	}
	
	/**
	 * 0.模拟数据库操作
	 * @param username
	 * @return
	 */
	private String getPasswordByUsername(String username) {
		// 这里应该去数据库取信息
		Map<String, String> userMap = new HashMap<>(16);
		// 用MD5加密，加盐
		Md5Hash md5Hash = new Md5Hash("258369", SALT);
		userMap.put("Tony", md5Hash.toString());
		return userMap.get(username);
	}

}
