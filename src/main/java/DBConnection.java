import java.sql.*;

public class DBConnection {

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

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String a = "학번";
        String b = "신청학점";
        String c = "이수학점";
        String d = "평점평균";
        String e = "백분위";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradu", "root", "root");

            pstmt = conn.prepareStatement("INSERT INTO 전체성적 (학번, 신청학점, 이수학점, 평점평균, 백분위) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, Json.meta(a));
            pstmt.setString(2, Json.meta(b));
            pstmt.setString(3, Json.meta(c));
            pstmt.setString(4, Json.meta(d));
            pstmt.setString(5, Json.meta(e));

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
}
