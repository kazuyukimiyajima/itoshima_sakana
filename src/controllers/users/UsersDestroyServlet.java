package controllers.users;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtil;
import models.User;

@WebServlet("/users/destroy")
public class UsersDestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



    public UsersDestroyServlet() {
        super();

            }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CSRF対策
	    String _token = (String)request.getParameter("_token");
	    //CSRF対策を行う　_tokenがnullではない場合　IDがきちんと取得できた場合
	    if(_token != null && _token.equals(request.getSession().getId())){

	        //EntityManagerのインスタンス生成
	        EntityManager em = DBUtil.createEntityManager();

	        //削除するデータ１件をデータベースから取得する
	        User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));
	        u.setDelete_flag(1); //デリートフラグを１にする
	        u.setUpdated_at(new Timestamp(System.currentTimeMillis()));

	        em.getTransaction().begin();
	        em.getTransaction().commit();
	        em.close();
	        request.getSession().setAttribute("flush", "削除が完了しました。");//フラッシュメッセージをセッションスコープに格納

	        //indexページにリダイレクト
	        response.sendRedirect(request.getContextPath() + "/users/index");
	    }
	}

}
