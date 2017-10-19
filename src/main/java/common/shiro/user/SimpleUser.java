/**
 * Copyright (C) 2014 陕西威尔伯乐信息技术有限公司
 * @Package com.wellbole.web.core.shiro  
 * @Title: SimpleUser.java  
 * @Description: 简单用户对象，用于在Session中保存用户对象 
 * @author 李飞 (lifei@wellbole.com)    
 * @date 2014年11月11日  上午4:31:55  
 * @since V1.0.0 
 *
 * Modification History:
 * Date         Author      Version     Description
 * -------------------------------------------------------------
 * 2014年11月11日      李飞                       V1.0.0        新建文件   
 */
package common.shiro.user;

import java.io.Serializable;

/**  
 * @ClassName: SimpleUser  
 * @Description: 简单用户对象，用于在Session中保存用户对象
 * @author 李飞 (lifei@wellbole.com)   
 * @date 2014年11月11日 上午4:31:55
 * @since V1.0.0  
 */

public class SimpleUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private final Integer id;
	private final String username;
	private final String name;
	private String roleName;
	private String role;
	public SimpleUser(Integer id, String username,String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}

	public SimpleUser(Integer id, String username) {
		this.id = id;
		this.username = username;
		this.name = username;
	}

	public final Integer getId() {
		return id;
	}
	/**
	 * @Title: getUsername  
	 * @Description: 获得用户名 
	 * @return 
	 * @since V1.0.0
	 */
	public final String getUsername() {
		return username;
	}
	/**
	 * @Title: getRoleName  
	 * @Description: 获得角色名称 
	 * @return 
	 * @since V1.0.0
	 */
	public final String getRoleName() {
		return roleName;
	}
	/**
	 * @Title: setRoleName  
	 * @Description: 设定角色名称  
	 * @param roleName 
	 * @since V1.0.0
	 */
	public final void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public final String getRole() {
		return role;
	}
	public final void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	
}
