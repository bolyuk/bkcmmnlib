package org.bolyuk.bkcmmnlib;

import java.util.List;
import java.util.Random;

public class ListUtils {
    public static <T> T getRandomElement(List<T> list){
        T r =list.get(new Random().nextInt(list.size()-1));
        System.out.println(r);
        return r;
    }

    public static <T> void print(List<T> list){
        list.forEach(e -> System.out.println(e.toString()+" "));
    }
}
