import java.util.ArrayList;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String data = "{'신청학점': '77', '이수학점': '77', '평점평균': '4.16', '백분위점수': '96.42'}";

        // '신청학점' 추출
        String 신청학점 = data.split("신청학점': '")[1].split("'")[0].trim();

        System.out.println(신청학점);  // 출력: 77
    }
}
