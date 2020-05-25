package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.User;


public class UserValidator {
    //バリデーションの実行
    public static List<String> validate(User u, Boolean password_check_flag){
        List<String> errors = new ArrayList<String>();

        String user_error = _validateUser(u.getUser());
        if(!user_error.equals("")){
            errors.add(user_error);
        }

        String password_error = _validatePassword(u.getPassword(), password_check_flag);
        if(!password_error.equals("")){
            errors.add(password_error);
        }
        return errors;
    }
    //社員名の必須入力チェック
    private static String _validateUser(String user){
        if(user == null || user.equals("")){
            return "ユーザー名を入力してください。";
        }
        return "";
    }
    //パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag){
        //パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))){
            return "パスワードを入力してください。";
        }
        return "";
    }

}
