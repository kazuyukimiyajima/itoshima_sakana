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
import models.User;
import utils.DBUtil;
import models.validators.FishValidator;


@WebServlet("/fishes/create")
public class FishesCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



    public FishesCreateServlet() {
        super();

    }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = (String)request.getParameter("_token");
		if( _token != null && _token.equals(request.getSession().getId())){

		    EntityManager em = DBUtil.createEntityManager();

		    Fish f = new Fish();

		    //ログインユーザーをデータベースからセッションスコープに登録
            f.setUser((User)request.getSession().getAttribute("login_user"));

		    f.setName(request.getParameter("fishname"));

		    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		    f.setCreated_at(currentTime);
            f.setUpdated_at(currentTime);

		    List<String> errors = FishValidator.validator(f);
		    if(errors.size() > 0){
		        em.close();

		        request.setAttribute("_token", request.getSession().getId());
		        request.setAttribute("fish", f);
		        request.setAttribute("errors", errors);


		        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
		        rd.forward(request, response);

		    }else{

		        em.getTransaction().begin();
		        em.persist(f);
		        em.getTransaction().commit();
		        em.close();
		        request.getSession().setAttribute("flush", "登録が完了しました。");

		        response.sendRedirect(request.getContextPath() + "/fishes/index");
		    }



		}

	}

}
