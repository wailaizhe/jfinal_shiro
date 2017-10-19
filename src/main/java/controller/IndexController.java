package controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller{
	public void index() {
		renderTemplate("success.html");
	}
    public void success() {
    	renderTemplate("login.html");
    }
    public void unauthorized() {
    	renderTemplate("unPermission.html");
    }
}
