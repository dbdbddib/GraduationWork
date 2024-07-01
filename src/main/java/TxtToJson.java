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
//            JSONObject semesters = parseSemesters(fileContent);
//            JSONObject courses = parseCourses(fileContent);

            finalData.put("meta", meta);
//            finalData.put("semesters", semesters);
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

//    private static JSONObject parseSemesters(String fileContent) {
//        JSONObject semesters = new JSONObject();
//        String[] lines = fileContent.split("\n");
//        for (String line : lines) {
//            if (line.contains("{'학기':")) {
//                String[] parts = line.split(": \\{");
//                String key = parts[0].trim().replace("'", "");
//                String[] values = parts[1].replace("'}", "").replace("'", "").split(", ");
//                JSONObject semester = new JSONObject();
//                for (String value : values) {
//                    String[] pair = value.split(": ");
//                    semester.put(pair[0].trim(), pair[1].trim());
//                }
//                semesters.put(key, semester);
//            }
//        }
//        return semesters;
//    }
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
