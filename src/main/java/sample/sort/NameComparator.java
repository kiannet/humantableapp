package sample.sort;

import sample.entity.Human;

import java.util.Comparator;

public class NameComparator implements Comparator<Human> {

    public int compare(Human human1, Human human2){

        if(human1.getName().length() > human2.getName().length())
            return 1;
        else if(human1.getName().length() < human2.getName().length())
            return -1;
        else
            return 0;
    }
}
