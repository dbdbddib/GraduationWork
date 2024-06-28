import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

// Data Access Object(DAO) : Database 에 접속하여 데이터를 저장하거나 읽음
// main()
public class GradesDAO {

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


    // 학기 데이터 추출
    private ArrayList<String> termList = new ArrayList<>(Json.semesters.keySet());


    // 전체성적 end
    public void meta() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String[] columns = {"학번", "신청학점", "이수학점", "평점평균", "백분위점수"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");
            pstmt = conn.prepareStatement("INSERT INTO meta (학번, 신청학점, 이수학점, 평점평균, 백분위) VALUES (?, ?, ?, ?, ?)");
            for (int i = 0; i < columns.length; i++) {
                pstmt.setString(i + 1, Json.meta(columns[i]));
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
//        for (String term : Json.semesters.keySet()) {
//            termList.add(term);
//        }

        String[] data = {"학번", "학기", "신청", "이수", "평점", "백분위", "석차"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");
            pstmt = conn.prepareStatement("INSERT INTO semesters (학번, 학기, 신청, 이수, 평점, 백분위, 석차) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < termList.size(); i++) {
                for (int j = 0; j < data.length; j++) {
                    pstmt.setString(j + 1, Json.semesters(termList.get(i), data[j]));
                }
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

    // 과목성적
    public void courses() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String[] data = {"과목", "구분", "학점", "실점", "등급", "평점"};
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");
            pstmt = conn.prepareStatement("INSERT INTO courses (학기, 과목, 구분, 학점, 실점, 등급, 평점) VALUES (?, ?, ?, ?, ?, ?, ?)");
            // "2020/10"(termList) 의 JSON배열을 가져와라
            for (String s : termList) {
                // Json.courses(termList.get(i)) -> "2020/10" 배열 안 객체 하나씩 setString
                for (JSONObject course : Json.courses(s)) {
                    pstmt.setString(1, s);
                    for (int k = 0; k < data.length; k++) {
                        pstmt.setString(k + 2, course.getString(data[k]));
                    }
                    pstmt.executeUpdate();
                }
            }
            System.out.println("All rows inserted.");
        } catch (SQLException ex) {
            System.out.println("SQL error occurred.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            close(null, pstmt, conn);
        }
    }

//    // 개인정보
//    public void info() {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        String[] data = {"학번", "학생구분", "이름", "학적상태", "주민번호", "입학구분", "정원구분",
//                "학과", "학년", "분반", "휴대전화", "지도교수", "주소", "이메일", "입학시기", "수험번호",
//                "전형구분", "출신고등학교","고교졸업일자", "입학일자"};
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");
//            pstmt = conn.prepareStatement("INSERT INTO info (학번, 과목, 구분, 학점, 실점, 등급, 평점, 과목, 구분, 학점, " +
//                    "실점, 등급, 평점, 과목, 구분," +
//                    " 학점, 실점, 등급, 평점, 과목, 구분, 학점, 실점, 등급, 평점) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//
//            for (int i = 0; i < data.length; i++) {
//                pstmt.setString(i + 1, Json.info(data[i]));
//            }
//            int rowsAffected = pstmt.executeUpdate();
//            System.out.println(rowsAffected + " rows inserted.");
//        } catch (SQLException ex) {
//            System.out.println("SQL error occurred.");
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        } finally {
//            close(null, pstmt, conn);
//        }
//    }



    public static void main(String[] args) {
        GradesDAO g = new GradesDAO();
        g.courses();

    }
}
