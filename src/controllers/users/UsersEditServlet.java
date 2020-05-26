package controllers.users;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;


@WebServlet("/users/edit")
public class UsersEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UsersEditServlet() {
        super();
            }

                                         //request 意味：要求           response 意味：応答
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//EntityManager　インスタンス作成
	    EntityManager em = DBUtil.createEntityManager();

	    //指定のID１件をデータベースから取得 リクエストパラメータで取得したString型を数値型へ変換
	    User u = em.find(User.class, Integer.parseInt(request.getParameter("id")));

	    //リクエストスコープへユーザー名と_tokenをセットしてあげる
	    request.setAttribute("user", u);
	    request.setAttribute("_token", request.getSession().getId());//セッションIDを登録
	    //ユーザーIDをセッションスコープに登録
	    request.getSession().setAttribute("user_id", u.getId());

	    //サーブレット からJSPを表示する getRequestDispatcher()メソッドの引数にJSPを指定し、
	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
	    rd.forward(request, response);//レスポンスの画面としてJSPを呼び出す
	}

}
