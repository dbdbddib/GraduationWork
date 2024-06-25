import java.sql.*;
import java.util.ArrayList;

// Data Access Object(DAO) : Database 에 접속하여 데이터를 저장하거나 읽음
public class DAO {

    public static void close(ResultSet rs, Statement pstmt, Connection conn) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 전체성적   학번을 굳이 만들 필요가 있나?    학번을 빼고 DB 넣어보자
    public void meta(){
        Connection conn = null;
        PreparedStatement pstmt = null;

        String[] columns = {"학번", "신청학점", "이수학점", "평점평균", "백분위"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");

            pstmt = conn.prepareStatement("INSERT INTO 전체성적 (학번, 신청학점, 이수학점, 평점평균, 백분위) VALUES (?, ?, ?, ?, ?)");

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


    public void semesters() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        //trem : 학기   while 객체가 존재할 때까지 변경 ...
        // String[] term = {"2020/10", "2022/20", "2023/10", "2023/20"};

        ArrayList<String> termList = new ArrayList<>();
        for (String term : Json.semesters.keySet()) {
            termList.add(term);
        }

        String[] data = {"학기", "신청", "이수", "평점", "백분위", "석차"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");
            pstmt = conn.prepareStatement("INSERT INTO 전체성적 (학기, 신청, 이수, 평점, 백분위, 석차) VALUES (?, ?, ?, ?, ?, ?)");

            for (int i = 0; i < termList.size(); i++) {
                for (int j = 0; j < data.length; j++) {
                    pstmt.setString(i + 1, Json.semesters(termList.get(i), data[j]));
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


    public static void main(String[] args) {
        DAO a = new DAO();
        a.meta();
    }
}
