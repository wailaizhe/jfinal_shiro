/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.core.realm  
 * @Title: ShiroDbRealm.java  
 * @Description: 基于db实现的shiro realm   
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年9月8日  下午10:10:38  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年9月8日      李飞                       V1.0.0        新建文件   
 */
package common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jfinal.plugin.activerecord.Record;

import common.entity.TRole;
import common.entity.Users;
import common.shiro.user.CaptchaUsernamePasswordToken;
import common.shiro.user.SimpleUser;

/**  
 * @ClassName: ShiroDbRealm  
 * @Description: 基于db实现的shiro realm 
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年9月8日 下午10:10:38
 * @since V1.0.0  
 */
public class ShiroDbRealm extends AuthorizingRealm {
    Users dao=new Users().dao();
    TRole roleDao=new TRole().dao();
	@Resource
	//private UserService userService;
	
	/**
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleUser simpleUser = (SimpleUser) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (null == simpleUser) {
			return info;
		}
		String sqlPara="select * from users where username=?";
		Users user=dao.findFirst(sqlPara,simpleUser.getUsername());
		if (null == user) {
			return info;
		}

		/*List<SysOperate> oplist = shiroDao.getOperateByUserId(user.getId());// 获取用户所有的功能权限
		for (SysOperate o : oplist) {
			info.addStringPermission(o.getPermission());// 获取资源名称
		}*/
		Set<String> roleNames = new HashSet<String>();
		roleNames.add(simpleUser.getRole());
		info.setRoles(roleNames);
		return info;
	}

	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
		String sqlPara="select * from users where username=?";
		    //校验用户是否存在
			Users user=dao.findFirst(sqlPara,authcToken.getUsername());
			if (user != null) {
				String password = String.valueOf(authcToken.getPassword());
				//校验用户密码是否正确
				if (password.equals(user.getPassword())) {
					SimpleUser simpleUser = new SimpleUser(user.getId(), user.getUsername());
					TRole role=roleDao.findById(user.getFkRoleId());
					//获取用户的角色名和角色，放进当前用户登录的实例中 注意这可以不放，当用户进行权限角色认证时 还是走 上面的方法 AuthorizationInfo()
					simpleUser.setRoleName(role.getRoleName());
					simpleUser.setRole(role.getRoleNum());
					return new SimpleAuthenticationInfo(simpleUser, password, getName());
				} else {
					throw new AuthenticationException("密码错误");
				}
			} else {
				throw new UnknownAccountException("用户不存在");
			}
			
	}

}
