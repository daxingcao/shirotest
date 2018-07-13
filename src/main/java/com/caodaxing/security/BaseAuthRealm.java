package com.caodaxing.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.caodaxing.entity.AuthRole;
import com.caodaxing.entity.AuthRoleExample;
import com.caodaxing.entity.LoginUser;
import com.caodaxing.entity.LoginUserExample;
import com.caodaxing.entity.Role;
import com.caodaxing.service.AuthRoleService;
import com.caodaxing.service.LoginUserService;
import com.caodaxing.service.UserRoleService;

/**
 * 
 * @author Create by daxing.cao on 2018/07/10
 * <p>登录验证以及添加角色/权限到shiro</p>
 * @version 版本号:1.0.0<br>
 * 			日期:2018/07/10<br>
 */
public class BaseAuthRealm extends AuthorizingRealm {
	
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private AuthRoleService authRoleService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		String loginName = (String) getAvailablePrincipal(principal);
		if(loginName == null) {
			return null;
		}
		//查询该用户信息
		LoginUserExample example = new LoginUserExample();
		example.createCriteria().andUsernameEqualTo(loginName);
		List<LoginUser> list = loginUserService.selectByExample(example);
		if(list.size() > 0) {
			SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
			//该用户的拥有的角色列表
			List<String> roles = new ArrayList<>();
			//该角色拥有的权限
			List<String> auths = new ArrayList<>();
			//查询角色列表
			List<Role> roleList = userRoleService.getRoleByUserId(list.get(0).getId());
			for (Role role : roleList) {
				roles.add(role.getRoleName());
				//查询该角色拥有的权限
				AuthRoleExample auth = new AuthRoleExample();
				auth.createCriteria().andRoleIdEqualTo(role.getRoleId());
				List<AuthRole> authRoleList = authRoleService.selectByExample(auth);
				for (AuthRole authRole : authRoleList) {
					auths.add(authRole.getAuthUrl());
				}
			}
			//将角色和权限加入进SimpleAuthorizationInfo中
			sai.addRoles(roles);
			sai.addStringPermissions(auths);
			return sai;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		LoginUserExample example = new LoginUserExample();
		example.createCriteria().andUsernameEqualTo(upToken.getUsername());
		List<LoginUser> list = loginUserService.selectByExample(example);
		if(list.size() > 0) {
			LoginUser loginUser = list.get(0);
			return new SimpleAuthenticationInfo(loginUser.getUsername(), loginUser.getPassword(), getName());
		}
		return null;
	}

}
