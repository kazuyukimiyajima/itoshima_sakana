package controllers.toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Fish;
import models.User;
import utils.DBUtil;



@WebServlet("/index.html")//http://localhost:8080という記述のみでトップページにアクセスできるようにする為
public class TopPageIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public TopPageIndexServlet() {
        super();

    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    EntityManager em = DBUtil.createEntityManager();

	    User login_user = (User)request.getSession().getAttribute("login_user");

	    int page;
	    try{
	        page = Integer.parseInt(request.getParameter("page"));
	    }catch(Exception e){
	        page = 1;
	    }
	    List<Fish> fishes = em.createNamedQuery("getMyAllFishes",Fish.class)
	                          .setParameter("user", login_user)
	                          .setFirstResult(15 * (page - 1))
	                          .setMaxResults(15)
	                          .getResultList();

	    long fishes_count = (long)em.createNamedQuery("getMyFishesCount",Long.class)
	                                .setParameter("user", login_user)
	                                .getSingleResult();
	    em.close();

	    request.setAttribute("fishes", fishes);
	    request.setAttribute("fishes_count", fishes_count);
	    request.setAttribute("page", page);

	    //ログイン直後のトップページでフラッシュメッセージを表示する
	    if(request.getSession().getAttribute("flush") != null){
	        request.setAttribute("flush",request.getSession().getAttribute("flush"));
	        request.getSession().removeAttribute("flush");
	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
	    rd.forward(request, response);
	}

}
