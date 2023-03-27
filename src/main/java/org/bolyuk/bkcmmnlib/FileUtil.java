package org.bolyuk.bkcmmnlib;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.lang.model.util.ElementScanner6;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class FileUtil {

    public static boolean isExist(String path){
        return new File(path).exists();
    }
    public static boolean isDirectory(String path){
        return new File(path).isDirectory();
    }



    public static void dir(String path){
        new File(path).mkdir();
    }

    public static String[] getSubPaths(String path){
        return new File(path).list();
    }

    public static String read(String path){
        File file = new File(path);
        AtomicReference<String> result = new AtomicReference<>("");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.lines().forEach(s -> result.set(result.get()+s+"\n"));
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.get();
    }

    public static void write(String path, Object value){
        File file = new File(path);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(new Gson().toJson(value));

            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> T readTo(String path, Type clazz){
        //System.out.println("class: "+clazz.getTypeName());
        String s = read(path);
        //System.out.println("value: "+s);
        return new Gson().fromJson(s, clazz);
    }
}
