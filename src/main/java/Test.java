import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<String> termList = new ArrayList<>(Json.semesters.keySet());
        System.out.println(termList);
    }
}
