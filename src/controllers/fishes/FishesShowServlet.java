package controllers.fishes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Fish;
import utils.DBUtil;



@WebServlet("/fishes/show")
public class FishesShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



    public FishesShowServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		Fish f = em.find(Fish.class, Integer.parseInt(request.getParameter("id")));

		em.close();

		request.setAttribute("fish", f);
		request.setAttribute("_token", request.getSession().getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/fishes/show.jsp");
		rd.forward(request,response);
	}

}
