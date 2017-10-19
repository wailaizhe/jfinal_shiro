package common.shiro;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.expr.ast.Expr;
import com.jfinal.template.expr.ast.ExprList;
import com.jfinal.template.stat.Scope;
import org.apache.shiro.SecurityUtils;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaiSeries on 2017/3/6.
 */
public class shiroTagForHasRole extends Directive {

    public boolean hasAllRoles(Expr[] roleNames){
        if (roleNames.length>0){
            List<String> roleNameList = new ArrayList<String>();
            for (Expr roleName : roleNames){
                roleNameList.add(roleName.toString());
            }
            return SecurityUtils.getSubject().hasAllRoles(roleNameList);
        }
        return false;
    }
    public void setExprList(ExprList exprList){
        super.setExprList(exprList);
    }

    public void exec(Env env, Scope scope, Writer writer) {
        if (hasAllRoles(exprList.getExprArray())){
            stat.exec(env,scope,writer);
        }
    }

    public boolean hasEnd(){
        return true;
    }

}
