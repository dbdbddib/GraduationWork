import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Test {

    private JSONObject courses;

    public JSONArray getCourses(String term) {
        return courses.getJSONArray(term);
    }
    public static String courses(String term, String data) {
        Json json = new Json();

        // ex) 다 "2020/10" 첫번째로 예시 들겠음
        // "2020/10" 객체를 가져옴
        JSONArray courseArray = json.getCourses(term);      // ex) 2020/10 [] 배열의 값


        //"2020/10"의 배열 안 한 객체씩 가져오는 for뮨
        for (int i = 0; i < courseArray.length(); i++) {
            JSONObject course = courseArray.getJSONObject(i);
            System.out.println(course.getString(data));
        }

        return "";
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.courses( "2020/10", "과목");
    }
}
