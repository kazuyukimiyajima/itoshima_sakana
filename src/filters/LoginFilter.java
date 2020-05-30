package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;


@WebFilter("/*") //基本的にwebページを表示する上で読みこむ全てのファイルでログイン状態を調べます。
public class LoginFilter implements Filter {


    public LoginFilter() {
            }


	public void destroy() {
			}



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        //CSSフォルダは認証処理から除外する    上の@WebFilter("/*")でCSSの読み込みの対象を外す
        if(!servlet_path.matches("/css.*")){
            HttpSession session = ((HttpServletRequest)request).getSession();

            //セッションスコープに登録された従業員情報を取得し変数 u に格納します。　ログアウトした時は u は　null になります。
            User u = (User)session.getAttribute("login_user");

            //ログイン画面について↓
            if(!servlet_path.equals("/login")){
            //もしログアウトしている状態であれば、ログイン画面にリダイレクト
                if(u == null){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    return;
                }

                //ユーザーの管理機能は管理者のみが閲覧できるようにする
                //ログインしているユーザー情報のadmin_flagをチェックし、一般ユーザーであるadmin_flagが０であればトップページへリダイレクトするようにしている
                if(servlet_path.matches("/users.*") && u.getAdmin_flag() == 0){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
        }else {  //ログイン画面について
            //ログインしているのにログイン画面を表示させようとした場合にはシステムのトップページへリダイレクト
            if(u != null){
                ((HttpServletResponse)response).sendRedirect(context_path + "/");
                return;
            }
          }
        }

		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {


	}

}
