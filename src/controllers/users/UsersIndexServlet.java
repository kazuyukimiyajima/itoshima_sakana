package controllers.users;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;

@WebServlet("/users/index")
public class UsersIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UsersIndexServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    EntityManager em = DBUtil.createEntityManager();

	    //開くページ数を取得
	    int page = 1;
	    try{
	        //数値ではないものをInteger.parseIntに渡すとNumberFormatEcceptionという例外が表示される
	        page = Integer.parseInt(request.getParameter("page"));
	    }catch(NumberFormatException e){}

	    //最大件数と開始位置を指定してメッセージを取得
	    List<User> users = em.createNamedQuery("getAllUsers",User.class)
	                         .setFirstResult(15 * (page - 1))//何件目からデータを取得するか
	                         .setMaxResults(15)//データの最大取得件数
	                         .getResultList();
	    //全件数を取得
	    long users_count = (long)em.createNamedQuery("getUserCount",Long.class)
	                               .getSingleResult();
	    em.close();

	    request.setAttribute("users",users);
	    request.setAttribute("users_count", users_count);//全件数
	    request.setAttribute("page",page);//ページ数

	    //フラッシュメッセージがセッションスコープにセットされていたら
	    //リクエストスコープに保存する(セッションスコープから削除）
	    if(request.getSession().getAttribute("flush") !=null){
	        request.setAttribute("flush", request.getSession().getAttribute("flush"));
	        request.getSession().removeAttribute("flush");
	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/index.jsp");
	    rd.forward(request, response);

	}

}
