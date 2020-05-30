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

@WebServlet("/users/update")
public class UsersUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public UsersUpdateServlet() {
        super();
            }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //CSRF対策
	    String _token = (String)request.getParameter("_token");
	    //CSRF対策を行う。_tokenがヌルじゃない場合、IDとがきちんと取得できた場合
	    if(_token != null && _token.equals(request.getSession().getId())){
	        //EntityManagerのインスタンス生成
	        EntityManager em = DBUtil.createEntityManager();

	        //指定のID１件をデータベースから取得
	        User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));

	        //パスワードの入力値チェックを行う指定をする
	        Boolean password_check_flag = true; //クライアントからのパスワード入力が間違ってなければリクエストパラメータに格納
	        String password = request.getParameter("password");

	        if(password == null || password.equals("")){ //パスワードがnull、又は未入力のときは失敗
	            password_check_flag = false;
	        }else{ //それ以外は ソルト文字で暗号化
	            u.setPassword(
	                    EncryptUtil.getPasswordEncrypt(
	                            password,
	                            (String)this.getServletContext().getAttribute("salt")
	                            )
	                    );
	        }

	        //フォームで入力があった内容を各プロパティに上書き
	        u.setName(request.getParameter("user"));
	        u.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));
	        u.setUpdated_at(new Timestamp(System.currentTimeMillis()));
	        u.setDelete_flag(0);

	        //バリデーションを設定してエラーがあれば編集画面のフォームに戻します 必須入力の確認
	        List<String> errors = UserValidator.validate(u, password_check_flag);
	        if(errors.size() > 0){
	            em.close();

	            //フォームに初期値を設定さらにエラーメッセージを送る　空白にしてエラーメッセージをだす
	            request.setAttribute("_token", request.getSession().getId());
	            request.setAttribute("user", u);
	            request.setAttribute("errors", errors);

	            //JSPを表示する　request.getRequestDispatcher() メソッドの引数にJSPを指定
	            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
	            rd.forward(request, response);

	        }else {//エラーがなければ
	            //データベースを更新
	            em.getTransaction().begin();
	            em.getTransaction().commit();
	            em.close();
	            request.getSession().setAttribute("flush","更新が完了しました。");

	            //セッションスコープ上の不要になったデータを削除
	            request.getSession().removeAttribute("user_id");

	            //indexページへリダイレクト
	            response.sendRedirect(request.getContextPath() + "/index");

	        }
	    }
}

}
