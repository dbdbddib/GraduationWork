import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json {

    private static JSONObject meta;
    public static JSONObject semesters;
    private static JSONObject courses;
    private static JSONObject info;
    // 생성자로 하고 나머지 static 삭제

    static {
        try {
            // JSON 파일 읽기
            FileReader fr = new FileReader("grades.json");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            fr.close();

            // JSON 객체 생성
            JSONObject jsonObject = new JSONObject(sb.toString());
            meta = jsonObject.getJSONObject("meta");
            semesters = jsonObject.getJSONObject("semesters");
            courses = jsonObject.getJSONObject("courses");
            info = jsonObject.getJSONObject("info");
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전체성적 (meta)
     * data : grades.json -> meta 에서 가져올 값
     */
    public static String meta(String data) {
        return meta.getString(data);
    }

    /**
     * 학기성적 (semesters)
     * term : 학기 정보 (예: "2020/10")
     * data : grades.json -> semesters 에서 가져올 값
     */
    public static String semesters(String term, String data) {
        return semesters.getJSONObject(term).getString(data);
    }

    /**
     * 과목성적 (courses)
     * term : 학기 정보 (예: "2020/10")
     * courseArray -> 예시 "2020/10" 배열을 가져오는
     * courseList -> "2020/10" 가져온 배열안 객체들을 따로따로 담긴 List  ->  return  (DAO.java -> courses())
     */
    public JSONArray getCourses(String term) {
        return courses.getJSONArray(term);
    }

    public static List<JSONObject> courses(String term) {
        Json json = new Json();

        // ex) 다 "2020/10" 첫번째로 예시 들겠음
        // "2020/10" 배열 가져옴
        JSONArray courseArray = json.getCourses(term);      // ex) 2020/10 [] 배열의 값


        // "2020/10"의 배열 안 한 객체씩 가져오는 for뮨
        // courseList -> 배열 안 객체들의 리스트  [2020/10, 2023/20, 2022/20, 2023/10]
        List<JSONObject> courseList = new ArrayList<>();
        for (int i = 0; i < courseArray.length(); i++) {
            courseList.add(courseArray.getJSONObject(i));
        }

        return courseList;
    }

    public static String info(String data) {
        return info.getString(data);
    }
}
