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

        lsa.doLSA(test);
    }
}
