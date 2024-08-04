import java.util.*;

public class GenericsExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        for (String s : list) {
            System.out.println(s);
        }
    }
}
