package org.bolyuk.bkcmmnlib;

public class Random {
    public static int r(int to){
        return new java.util.Random().nextInt(to);
    }

    public static int r(int from, int to){
        if(from == to)
            return from;

        return new java.util.Random().nextInt(to - from) + from;
    }


}
