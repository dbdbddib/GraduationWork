import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Data Access Object(DAO) : Database 에 접속하여 데이터를 저장하거나 읽음
// main()
public class GradesDAO {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gradu";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private Json json;
    private final List<String> termList;

    public GradesDAO(String jsonFilePath) {
        this.json = new Json(jsonFilePath);
        Set<String> termSet = json.semesters.keySet();
        this.termList = new ArrayList<>(termSet);
    }

    public static void close(ResultSet rs, Statement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 전체성적 end
    public void meta() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String[] columns = {"학번", "신청학점", "이수학점", "평점평균", "백분위점수"};

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            pstmt = conn.prepareStatement("INSERT INTO meta (학번, 신청학점, 이수학점, 평점평균, 백분위) VALUES (?, ?, ?, ?, ?)");
            for (int i = 0; i < columns.length; i++) {
                pstmt.setString(i + 1, json.meta(columns[i]));
            }
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
        } catch (SQLException ex) {
            System.out.println("SQL error occurred.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            close(null, pstmt, conn);
        }
    }

    // 학기성적 end
    public void semesters() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String[] data = {"학기", "신청", "이수", "평점", "백분위", "석차"};

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            pstmt = conn.prepareStatement("INSERT INTO semesters (학기, 신청, 이수, 평점, 백분위, 석차) VALUES (?, ?, ?, ?, ?, ?)");
            for (String s : termList) {
                pstmt.setString(1, s);
                for (int j = 0; j < data.length - 1; j++) {
                    pstmt.setString(j + 2, json.semesters(s, data[j]));
                }
                pstmt.executeUpdate();
            }
            System.out.println("semesters Table All rows inserted.");
        } catch (SQLException ex) {
            System.out.println("SQL error occurred.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            close(null, pstmt, conn);
        }
    }

    // 과목성적
    public void courses() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String[] data = {"과목", "구분", "학점", "실점", "등급", "평점"};
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            pstmt = conn.prepareStatement("INSERT INTO courses (학기, 과목, 구분, 학점, 실점, 등급, 평점) VALUES (?, ?, ?, ?, ?, ?, ?)");
            // "2020/10"(termList) 의 JSON배열을 가져와라
            for (String s : termList) {
                // Json.courses(termList.get(i)) -> "2020/10" 배열 안 객체 하나씩 setString
                for (JSONObject course : json.courses(s)) {
                    pstmt.setString(1, s);
                    for (int k = 0; k < data.length; k++) {
                        pstmt.setString(k + 2, course.getString(data[k]));
                    }
                    pstmt.executeUpdate();
                }
            }
            System.out.println("courses Table All rows inserted.");
        } catch (SQLException ex) {
            System.out.println("SQL error occurred.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            close(null, pstmt, conn);
        }
    }


    public static void main(String[] args) {
//        GradesDAO g = new GradesDAO("output.json");
//        InfoDAO i = new InfoDAO("info.json");
        TxtToJson tj = new TxtToJson("grades.txt");
    }
}
