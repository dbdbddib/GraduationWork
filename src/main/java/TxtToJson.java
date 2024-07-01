import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class TxtToJson {

    public static void main(String[] args) {
        try {
            // 파일 읽기
            File file = new File("grades.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            String fileContent = sb.toString();

            // JSON 객체 생성
            JSONObject finalData = new JSONObject();
            JSONObject meta = parseMetaData(fileContent);
            JSONObject semesters = parseSemesters(fileContent);
//            JSONObject courses = parseCourses(fileContent);

            finalData.put("meta", meta);
            finalData.put("semesters", semesters);
//            finalData.put("courses", courses);

            // JSON 파일로 저장
            try (FileWriter fileWriter = new FileWriter("output.json")) {
                fileWriter.write(finalData.toString(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject parseMetaData(String fileContent) {
        JSONObject meta = new JSONObject();
        String[] lines = fileContent.split("\n");

        meta.put("학번", "202003377"); // 학번은 예시 데이터로 고정
        meta.put("신청학점", lines[0].split("신청학점': '")[1].split("'")[0].trim());
        meta.put("이수학점", lines[0].split("이수학점': '")[1].split("'")[0].trim());
        meta.put("평점평균", lines[0].split("평점평균': '")[1].split("'")[0].trim());
        meta.put("백분위점수", lines[0].split("백분위점수': '")[1].split("'")[0].trim());

        return meta;
    }

    private static JSONObject parseSemesters(String fileContent) {
        JSONObject semesters = new JSONObject();
        String[] lines = fileContent.split("\n");

        // 예제 JSON 데이터 (실제 데이터는 fileContent로부터 읽어올 것입니다)
        String data = "{'2020/10': {'학기': '1', '신청': '20', '이수': '20', '평점': '4.15', '백분위': '96.32', '석차': '17 / 125'}, '2022/20': {'학기': '2', '신청': '20', '이수': '20', '평점': '4.03', '백분위': '95.05', '석차': '12 / 110'}, '2023/10': {'학기': '1', '신청': '17', '이수': '17', '평점': '4.06', '백분위': '95.37', '석차': '22 / 103'}, '2023/20': {'학기': '2', '신청': '20', '이수': '20', '평점': '4.40', '백분위': '98.95', '석차': '8 / 101'}}";

        // JSON 데이터 파싱
        JSONObject jsonData = new JSONObject(data.replace("'", "\""));

        // 학기 목록 추출 및 정렬
        Set<String> termSet = jsonData.keySet();
        List<String> termList = new ArrayList<>(termSet);
        Collections.sort(termList);

        // 각 학기의 정보를 JSONObject에 저장
        for (String term : termList) {
            JSONObject termData = jsonData.getJSONObject(term);
            semesters.put(term, termData);
        }

        return semesters;
    }
//
//    private static JSONObject parseCourses(String fileContent) {
//        JSONObject courses = new JSONObject();
//        String[] lines = fileContent.split("\n");
//        String currentSemester = null;
//        JSONArray courseArray = null;
//
//        for (String line : lines) {
//            if (line.contains("': [")) {
//                currentSemester = line.split(": \\[")[0].trim().replace("'", "");
//                courseArray = new JSONArray();
//            } else if (line.contains("{'과목':")) {
//                String[] parts = line.replace("{", "").replace("}", "").replace("'", "").split(", ");
//                JSONObject course = new JSONObject();
//                for (String part : parts) {
//                    String[] pair = part.split(": ");
//                    course.put(pair[0].trim(), pair[1].trim());
//                }
//                if (courseArray != null) {
//                    courseArray.put(course);
//                }
//            } else if (line.contains("]")) {
//                if (currentSemester != null && courseArray != null) {
//                    courses.put(currentSemester, courseArray);
//                }
//            }
//        }
//
//        return courses;
//    }
}
