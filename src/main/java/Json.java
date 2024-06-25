import java.io.*;
import org.json.JSONObject;

public class Json {

    private static JSONObject meta;
    public static JSONObject semesters;


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

        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전체성적 (meta)
     * data : grades.json meta 에서 가져올 값
     */
    public static String meta(String data) {
        return meta.getString(data);
    }

    /**
     * 학기성적 (semesters)
     * term : 학기 정보 (예: "2020/10")
     * data : grades.json semesters 에서 가져올 값
     */
    public static String semesters(String term, String data) {
        return semesters.getJSONObject(term).getString(data);
    }

    public static void main(String[] args) {
        String 신청학점 = meta("신청학점");
        System.out.println("신청학점: " + 신청학점);
    }
}
