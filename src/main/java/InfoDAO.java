import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InfoDAO {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gradu";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private InfoJson iJson;

    public InfoDAO(String jsonFilePath) {
        this.iJson = new InfoJson(jsonFilePath);
    }

    // 개인정보
    public void info() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String[] data = {"학번", "학생구분", "이름", "학적상태", "주민번호", "입학구분", "정원구분",
                "학과", "학년", "분반", "휴대전화", "지도교수", "주소", "이메일", "입학시기", "수험번호",
                "전형구분", "출신고등학교","고교졸업일자", "입학일자"};
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            pstmt = conn.prepareStatement("INSERT INTO info (학번, 학생구분, 이름, 학적상태, 주민번호, " +
                    "입학구분, 정원구분, 학과, 학년, 분반," +
                    " 휴대전화, 지도교수, 주소, 이메일, 입학시기, 수험번호, 전형구분, 출신고등학교, 고교졸업일자, 입학일자) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i < data.length; i++) {
                pstmt.setString(i + 1, iJson.info(data[i]));
            }
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " rows inserted.");
        } catch (SQLException ex) {
            System.out.println("SQL error occurred.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            GradesDAO.close(null, pstmt, conn);
        }
    }
}
