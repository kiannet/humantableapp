package sample.validator;

public class DataValidator {

    public boolean validateAge(String age){

        return age.matches("[0-9]*");

    }

    public boolean validateDate(String date){

        return date.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}");

    }

}
