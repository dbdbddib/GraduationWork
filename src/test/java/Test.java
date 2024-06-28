import java.util.ArrayList;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Json json = new Json("grades.json");
        Set<String> terms = json.semesters.keySet();

        System.out.println(terms);
    }
}
