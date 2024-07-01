import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InfoJson {
    private JSONObject info;

    public InfoJson(String filePath) {
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
            this.info = jsonObject.getJSONObject("info");
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 학생정보 (info)
     * data : grades.json -> info 객체에서 가져올 값
     */
    public String info(String data) {
        return info.getString(data);
    }
}
