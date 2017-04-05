import java.util.ArrayList;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class MainTest {
    private static ArrayList<String> stopWords;
    public static void main(String[] args) {

        stopWords = new TxtReader().loadFile("src/res/stop-words.txt");
        if (stopWords == null)
            System.out.println("null");

        System.out.println(stopWords);
        LSA lsa = new LSA(stopWords);


        ArrayList<String> test = new ArrayList<>();
        test.add("Британская полиция знает о местонахождении основателя WikiLeaks");
        test.add("В суде США начинается процесс против россиянина, рассылавшего спам");
        test.add("Церемонию вручения Нобелевской премии мира бойкотируют 19 стран");
        test.add("В Великобритании арестован основатель сайта Wikileaks Джулиан Ассандж");
        test.add("Украина игнорирует церемонию вручения Нобелевской премии");
        test.add("Шведский суд отказался рассматривать апелляцию основателя Wikileaks");
        test.add("НАТО и США разработали планы обороны стран Балтии против России");
        test.add("Полиция Великобритании нашла основателя WikiLeaks, но, не арестовала");
        test.add("В Стокгольме и Осло сегодня состоится вручение Нобелевских премий");

        lsa.doLSA(test);
    }
}
