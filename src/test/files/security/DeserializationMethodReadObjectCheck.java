

import com.jd.security.codesec.JDSafeObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class test {

    public void deserializationDemo(Object obj) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
        outStream.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(byteIn);
        Object object = inStream.readObject();   // Noncompliant
    }

    public Object deserialization(Object obj) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
        outStream.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(byteIn);
        return inStream.readObject();    // Noncompliant
    }

    public Object deserializationTest(Object obj) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
        outStream.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        return new ObjectInputStream(byteIn).readObject();    // Noncompliant
    }

    public Object deserializationNoIssue(Object obj) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
        outStream.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        return new JDSafeObjectInputStream(new ObjectInputStream(byteIn)).readObject();    // Compliant
    }

    public static Object deSerialize(byte[] buf) {
        Object obj = null;
        if (buf != null) {
            ByteArrayInputStream bos = null;
            ObjectInputStream ios = null;
            try {
                bos = new ByteArrayInputStream(buf);
                ios = new JDSafeObjectInputStream(bos);
                obj = ios.readObject();    // Compliant
            } catch (Exception e) {
                log.error("DESERIALIZE_EXCEPTION|反序列化对象出现异常", e);
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                    if (ios != null) {
                        ios.close();
                    }
                } catch (IOException e) {
                    log.error("DESERIALIZE__IOCLOSE_ERROR|反序列化关闭IO出现异常");
                }
            }
        }
        return obj;
    }
}