package org.bolyuk.bkcmmnlib;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

public class TinyDB<T> {
    String _initPath;
    boolean _isSub=false;
    Type getter;

    HashMap<String, T> cache = new HashMap<>();

    preGetter preGetter;

    public TinyDB(String initPath){
        this._initPath=initPath;
    }

    public TinyDB(String initPath, Type getter){
        this._initPath=initPath;
        this.getter=getter;
    }

    private TinyDB(String initPath, boolean isSub){
        this._initPath=initPath;
        this._isSub=isSub;
    }

    public void remove(String id){
        FileUtil.remove(_initPath+"/"+id);
    }

    public void put(T object){
        FileUtil.write(_initPath+"/"+UUID.randomUUID().toString(), object);
    }

    public void write(String id, T object){
        FileUtil.write(_initPath+"/"+id, object);
    }

    public synchronized T get(String id){
        if(cache.containsKey(id))
          return cache.get(id);

        T buf = FileUtil.readTo(_initPath+"/"+id, getter);

        if(preGetter != null)
            buf = (T) preGetter.onPreGet(buf);

        cache.put(id, buf);

        return buf;
    }

    public List<T> getAll(){
        ArrayList<T> r = new ArrayList<>();

        Arrays.stream(FileUtil.getSubPaths(_initPath)).forEach(s -> {
            if(!FileUtil.isDirectory(_initPath+"/"+s))
                r.add(get(s));
        });

        return r;
    }

    public boolean contains(String id){
        return FileUtil.isExist(_initPath+"/"+id);
    }

    public TinyDB dir(String name){
        FileUtil.dir(_initPath+"/"+name);
        return this;
    }

    public TinyDB to(String name){
        return new TinyDB(_initPath+"/"+name, true);
    }

    public TinyDB back(){
        if(_isSub)
            return new TinyDB(_initPath.substring(0, _initPath.lastIndexOf("/")));
        return null;
    }

    public TinyDB preGetAction(preGetter preGetter){
        this.preGetter=preGetter;
        return this;
    }

    public interface preGetter<T>{
         T onPreGet(T obj);
    }

    public synchronized void save(){
        cache.forEach((s, t) -> write(s, t));
        cache.clear();
    }

}
