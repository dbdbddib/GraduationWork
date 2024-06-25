import java.util.ArrayList;

public class Exam {
    public static void main(String[] args) {
        ArrayList<String> termList = new ArrayList<>();
        for (String term : Json.semesters.keySet()) {
            termList.add(term);
        }
        System.out.println(termList);
    }
}
