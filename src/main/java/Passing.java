import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Passing {
    public static void main(String[] args) {
        FileReader fr = null;
        FileWriter fw = null;

        try {
            // 읽을 파일의 경로를 설정합니다.
            fr = new FileReader("C:\\Users\\admin\\Desktop\\Stis_Remake-main\\log\\202003377\\grades.txt");
            // 저장할 파일의 경로를 설정합니다.
            fw = new FileWriter("grades.txt");

            int c;
            while ((c = fr.read()) != -1) {
                fw.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // FileReader와 FileWriter를 닫아 리소스를 해제합니다.
            try {
                if (fr != null) {
                    fr.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
