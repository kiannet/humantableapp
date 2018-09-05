package sample.entity;

import javafx.beans.property.SimpleStringProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Human implements Comparable<Human>{
    private String name;
    private int age;
    private SimpleStringProperty birthdayString = new SimpleStringProperty("");
    private Date birthday;

    public Human(){
    }

    public Human(String name, int age, String birthdayString){
        this.name = name;
        this.age = age;
        this.birthdayString = new SimpleStringProperty(birthdayString);

        try {
            this.birthday = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public Date getBirthday() {
        return birthday;
    }
    public String getBirthdayString() {
        return birthdayString.getValue();
    }
    public SimpleStringProperty getBirthdayStringProperty(){
        return birthdayString;
    }

    @Override
    public String toString() {
        return name + " " + age + " " + birthday;
    }

    @Override
    public int compareTo(Human other) {
        return name.compareTo(other.name);
    }
}
