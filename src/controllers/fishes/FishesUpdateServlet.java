package controllers.fishes;

import java.io.IOException;
import java.sql.Timestamp;
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
import models.validators.FishValidator;


@WebServlet("/fishes/update")
public class FishesUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public FishesUpdateServlet() {
        super();

            }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = (String)request.getParameter("_token");
		if(_token != null && _token.equals(request.getSession().getId())){
		    EntityManager em = DBUtil.createEntityManager();

		    Fish f = em.find(Fish.class,(Integer)(request.getSession().getAttribute("fish_id")));

		    f.setName(request.getParameter("fishname"));
		    f.setUpdated_at(new Timestamp(System.currentTimeMillis()));

		    List<String> errors = FishValidator.validator(f);
		    if(errors.size() > 0) {
		        em.close();

		        request.setAttribute("_token", request.getSession().getId());
		        request.setAttribute("fish", f);
		        request.setAttribute("errors", errors);

		        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/fishes/edit.jsp");
		        rd.forward(request, response);
		    }else{
		        em.getTransaction().begin();
		        em.getTransaction().commit();
		        em.close();
		        request.getSession().setAttribute("flush","更新が完了しました。");

		        request.getSession().removeAttribute("fish_id");

		        response.sendRedirect(request.getContextPath() + "/fishes/index");
		    }



		}

	}

}
