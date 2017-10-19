package controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

import common.shiro.ShiroKit;
import common.shiro.user.CaptchaUsernamePasswordToken;
import common.shiro.user.SimpleUser;

public class Passport extends Controller{
	public void index() {
		renderTemplate("login.html");
	}
	public void login() {
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		//UsernamePasswordToken token=new UsernamePasswordToken(getPara("username"), getPara("password"));
		CaptchaUsernamePasswordToken token =new CaptchaUsernamePasswordToken(getPara("username"), getPara("password"), false, "", "");
		Ret ret = null;
		try {
			ThreadContext.bind(subject);
			//进行用用户名和密码验证
			if (subject.isAuthenticated()) {
				subject.logout();
			} else {
				subject.login(token);
			}
			//调转到主页面
//				this.redirect("/");
			ret = Ret.ok();
		}  catch (LockedAccountException e) {
			ret = Ret.fail("msg","账号已被锁定");
		} catch (UnknownAccountException e) {
			System.err.println(e.getMessage());
			ret = Ret.fail("msg",e.getMessage());
		} catch (AuthenticationException e) {
			System.err.println(e.getMessage());
			ret = Ret.fail("msg",e.getMessage());
		} catch (Exception e) {
			ret = Ret.fail("msg","系统异常");
		}
		keepPara("username");
		renderTemplate("success.html");
	
	}
	
	public void unauthorized() {
	    SimpleUser user=ShiroKit.getLoginUser();
		String username=user.getUsername();
		setAttr("username", username);
		renderTemplate("unPermission.html");
	}
}
