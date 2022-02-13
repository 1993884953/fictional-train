package com.example.demo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloneUtil {
    /**
     * 实现深度克隆
     *
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T deepClone(T t) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        //将当前这个对象写到一个输出流当中，，因为这个对象的类实现了Serializable这个接口，所以在这个类中
        //有一个引用，这个引用如果实现了序列化，那么这个也会写到这个输出流当中
        oos.writeObject(t);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        //将流中的东西读出类，读到一个对象流当中，这样就可以返回这两个对象的东西，实现深克隆
        return (T) ois.readObject();
    }
}
