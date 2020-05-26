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


@WebServlet("/users/show")
public class UsersShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UsersShowServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //DBと接続するEntityManagerのインスタンス作成
	    EntityManager em = DBUtil.createEntityManager();

	    //該当のIDユーザーの情報１件を取得する　request.getParameter()これだとString型を取得してしまう為、Integer.parseIntで数値型に変換する
	    User u = em.find(User.class, Integer.parseInt(request.getParameter("id")));
	    em.close();

	    //ユーザーのデータをリクエストスコープにセットしてshow.jspを呼びだす
	    request.setAttribute("user", u);

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/show.jsp");
	    rd.forward(request, response);
	}

}
