package models.validators;

import java.util.ArrayList;
import java.util.List;
import models.Fish;

public class FishValidator {

    public static List<String> validator(Fish f){
        List<String> errors = new ArrayList<String>();

        String name_error = _validateName(f.getName());
        if(!name_error.equals("")){
            errors.add(name_error);
        }

        return errors;
    }

    private static String _validateName(String name){
        if(name == null || name.equals("")){
            return "魚の名前を入力してください。";
        }
        return "";
    }






}
