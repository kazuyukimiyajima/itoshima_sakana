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
import models.User;
import utils.DBUtil;


@WebServlet("/fishes/edit")
public class FishesEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public FishesEditServlet() {
        super();


    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    EntityManager em = DBUtil.createEntityManager();

	    Fish f = em.find(Fish.class, Integer.parseInt(request.getParameter("id")));

	    em.close();

	    User login_user = (User)request.getSession().getAttribute("login_user");
	    if(f != null && login_user.getId() == f.getUser().getId()){
	        request.setAttribute("fish", f);
	        request.setAttribute("_token", request.getSession().getId());
	        request.getSession().setAttribute("fish_id", f.getId());

	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/fishes/edit.jsp");
	    rd.forward(request, response);





	}

}
