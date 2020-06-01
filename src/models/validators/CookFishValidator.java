package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.CookFish;

public class CookFishValidator {
    //バリデーションの実行
    public static List<String> validate(CookFish cf){
        List<String> errors = new ArrayList<String>();

        String title_error = _validateTitle(cf.getTitle());
        if(!title_error.equals("")){
            errors.add(title_error);
        }

        String content_error = _validateContent(cf.getContent());
        if(!content_error.equals("")){
            errors.add(content_error);
        }
        return errors;

    }

    //タイトルの必須入力チェック
    private static String _validateTitle(String title){
        if(title == null || title.equals("")){
            return "料理名を入力してください。";
        }
        return "";
    }

    //メッセージ入力の必須入力チェック
    private static String _validateContent(String content){
        if(content == null || content.equals("")){
            return "調理方法を入力してください。";
        }
        return "";
    }

}
