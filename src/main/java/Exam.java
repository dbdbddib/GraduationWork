import java.util.ArrayList;

public class Exam {
    public static void main(String[] args) {
        ArrayList<String> termList = new ArrayList<>(Json.semesters.keySet());
        System.out.println(termList);
    }
}
