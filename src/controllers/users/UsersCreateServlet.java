package controllers.users;

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

import models.User;
import models.validators.UserValidator;
import utils.DBUtil;
import utils.EncryptUtil;

@WebServlet("/users/create")
public class UsersCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UsersCreateServlet() {
        super();
            }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CSRF対策
	    String _token = (String)request.getParameter("_token");
	    //CSRF対策を行っていく。_tokenに値がセットされていなかったり、セッションIDが異なっているとデータが登録できないようにする
	    if(_token != null && _token.equals(request.getSession().getId())){
	        //EntityManagerの生成
	        EntityManager em = DBUtil.createEntityManager();

	        User u = new User();

	        //リクエストパラメータにセット
	        u.setName(request.getParameter("user"));
	        u.setPassword(//SHA256という暗号化式を利用し、パスワードを暗号化
	                EncryptUtil.getPasswordEncrypt(
	                        request.getParameter("password"),
	                        (String)this.getServletContext().getAttribute("salt")
	                        )
	                );
	        u.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));

	        //現在日時の情報をもつオブジェクトを取得
	        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	        u.setCreated_at(currentTime);
	        u.setUpdated_at(currentTime);
	        u.setDelete_flag(0);

	        //バリデーションを実行し、エラーがあれば新規ユーザー登録のフォームに戻す
	        List<String> errors = UserValidator.validate(u, true);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", u);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/users/index");
            }

	    }


	}

}
