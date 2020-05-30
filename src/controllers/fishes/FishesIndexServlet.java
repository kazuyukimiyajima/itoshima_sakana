package controllers.fishes;

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
import utils.DBUtil;



@WebServlet("/fishes/index")
public class FishesIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public FishesIndexServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		int page;
		try{
		    page = Integer.parseInt(request.getParameter("page"));
		}catch(Exception f){
		    page = 1;
		}
	    List<Fish> fishes = em.createNamedQuery("getAllFishes", Fish.class)
	                             .setFirstResult(15 * (page - 1))
	                             .setMaxResults(15)
	                             .getResultList();

	    long fishes_count = (long)em.createNamedQuery("getFishesCount",Long.class)
	                                 .getSingleResult();

	    em.close();

	    request.setAttribute("fishes", fishes);
	    request.setAttribute("fishes_count", fishes_count);
	    request.setAttribute("page", page);

	    if(request.getSession().getAttribute("flush") != null){
	        request.setAttribute("flush", request.getSession().getAttribute("flush"));
	        request.getSession().removeAttribute("flush");

	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/fishes/index.jsp");
	    rd.forward(request, response);
	}

}
