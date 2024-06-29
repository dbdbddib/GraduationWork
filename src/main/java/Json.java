import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json {

    private JSONObject meta;
    public JSONObject semesters;
    private JSONObject courses;

    public Json(String filePath) {
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            fr.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            this.meta = jsonObject.getJSONObject("meta");
            this.semesters = jsonObject.getJSONObject("semesters");
            this.courses = jsonObject.getJSONObject("courses");
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전체성적 (meta)
     * data : grades.json -> meta 객체에서 가져올 값
     */
    public String meta(String data) {
        return meta.getString(data);
    }

    /**
     * 학기성적 (semesters)
     * term : 학기 정보 (예: "2020/10")
     * data : grades.json -> semesters 객체에서 가져올 값
     */
    public String semesters(String term, String data) {
        return semesters.getJSONObject(term).getString(data);
    }



    public JSONArray getCourses(String term) {
        return courses.getJSONArray(term);
    }
    /**
     * 과목성적 (courses)
     * term : 학기 정보 (예: "2020/10")
     * data : grades.json -> courses 에서 가져올 값
     * courseArray -> 예시 "2020/10" 배열을 가져오는
     * courseList -> "2020/10" 가져온 배열안 객체들을 따로따로 담긴 List  ->  return
     */
    public List<JSONObject> courses(String term) {
        JSONArray courseArray = getCourses(term);
        List<JSONObject> courseList = new ArrayList<>();
        for (int i = 0; i < courseArray.length(); i++) {
            courseList.add(courseArray.getJSONObject(i));
        }
        return courseList;
    }


}
