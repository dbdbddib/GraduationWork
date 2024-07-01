import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Json json;
        List<String> termList;
        json = new Json("grades.json");
        Set<String> termSet = json.semesters.keySet();
        termList = new ArrayList<>(termSet);
        System.out.println(termSet);

        String data = "{'2020/10': {'학기': '1', '신청': '20', '이수': '20', '평점': '4.15', '백분위': '96.32', '석차': '17 / 125'}, '2022/20': {'학기': '2', '신청': '20', '이수': '20', '평점': '4.03', '백분위': '95.05', '석차': '12 / 110'}, '2023/10': {'학기': '1', '신청': '17', '이수': '17', '평점': '4.06', '백분위': '95.37', '석차': '22 / 103'}, '2023/20': {'학기': '2', '신청': '20', '이수': '20', '평점': '4.40', '백분위': '98.95', '석차': '8 / 101'}}";

        for(String s : termList) {
            String a = "': " ;
            String 학기 = data.split(s+a)[1].split("'")[3];
            String 신청 = data.split(s+a)[1].split("'")[7];
            String 이수 = data.split(s+a)[1].split("'")[11];
            String 평점 = data.split(s+a)[1].split("'")[15];
            String 백분위 = data.split(s+a)[1].split("'")[19];
            String 석차 = data.split(s+a)[1].split("'")[23];


            System.out.println(학기 + "\n" + 신청 + "\n" + 이수 + "\n" + 평점 + "\n" + 백분위 + "\n" + 석차);
        }

    }
    //String 신청 = data.split("2020/10': ")[1].split("'")[7];
}