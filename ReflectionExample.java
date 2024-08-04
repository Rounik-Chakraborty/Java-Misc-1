import java.lang.reflect.*;

public class ReflectionExample {
    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("java.util.ArrayList");
            System.out.println("Class Name: " + cls.getName());

            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("Method: " + method.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

