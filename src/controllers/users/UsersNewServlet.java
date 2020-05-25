package controllers.users;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

@WebServlet("/users/new")
public class UsersNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UsersNewServlet() {
        super();
            }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CSRF対策
	    request.setAttribute("_token", request.getSession().getId());
	    //新規ユーザー登録フォーム作成ページを表示する上でのまじない　画面表示時のエラー回避のため、とりあえず文字数０のデータをフォームに渡す
	    request.setAttribute("user", new User());

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/new.jsp");
	    rd.forward(request, response);
	}

}
