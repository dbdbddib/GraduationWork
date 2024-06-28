import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json {

    private JSONObject meta;
    JSONObject semesters;
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

    public String meta(String data) {
        return meta.getString(data);
    }

    public String semesters(String term, String data) {
        return semesters.getJSONObject(term).getString(data);
    }

    public JSONArray getCourses(String term) {
        return courses.getJSONArray(term);
    }

    public List<JSONObject> courses(String term) {
        JSONArray courseArray = getCourses(term);
        List<JSONObject> courseList = new ArrayList<>();
        for (int i = 0; i < courseArray.length(); i++) {
            courseList.add(courseArray.getJSONObject(i));
        }
        return courseList;
    }
}
