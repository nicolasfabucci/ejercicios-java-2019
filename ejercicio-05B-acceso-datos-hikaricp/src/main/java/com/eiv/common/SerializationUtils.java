package com.eiv.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtils {

    public static byte[] serialize(Object state) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(state);
            oos.flush();
            return bos.toByteArray();
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T deserialize(InputStream in) {
        ObjectInputStream oip = null;
        try {
            oip = new ObjectInputStream(in);
            
            @SuppressWarnings("unchecked")
            T result = (T) oip.readObject();
            return result;
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
