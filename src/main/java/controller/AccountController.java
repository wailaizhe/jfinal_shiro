package controller;


import com.jfinal.core.Controller;

import common.shiro.RequiresRoles;

public class AccountController extends Controller {
	 @RequiresRoles( value = {"04", "01"})
	public void look() {
		System.err.println("您可以啊，能进来");
		renderText("您可以啊，能进来");
	}

}
