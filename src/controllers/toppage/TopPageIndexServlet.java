package controllers.toppage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/index.html")//http://localhost:8080という記述のみでトップページにアクセスできるようにする為
public class TopPageIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public TopPageIndexServlet() {
        super();

    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //ログイン直後のトップページでフラッシュメッセージを表示する
	    if(request.getSession().getAttribute("flush") != null){
	        request.setAttribute("flush",request.getSession().getAttribute("flush"));
	        request.getSession().removeAttribute("flush");
	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
	    rd.forward(request, response);
	}

}
