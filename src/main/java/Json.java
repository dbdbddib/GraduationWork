import java.io.*;
import org.json.JSONObject;

public class Json {

    private static JSONObject meta;

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

        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public static String meta(String res) {
        return meta.getString(res);
    }

    public static void main(String[] args) {
        String 신청학점 = meta("신청학점");
        System.out.println("신청학점: " + 신청학점);
    }
}
