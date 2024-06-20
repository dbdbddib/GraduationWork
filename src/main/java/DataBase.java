
import java.io.*;
import org.json.JSONObject;

public class DataBase {
    public static void main(String[] args) {
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

            // JSON 데이터 문자열로 변환
            String jsonString = sb.toString();

            // JSON 객체 생성
            JSONObject jsonObject = new JSONObject(jsonString);

            // meta 객체에서 신청학점 값 가져오기
            JSONObject meta = jsonObject.getJSONObject("meta");
            String 신청학점 = meta.getString("신청학점");

            // 결과 출력
            System.out.println("신청학점: " + 신청학점);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }
}