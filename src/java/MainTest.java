import java.util.ArrayList;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class MainTest {
    public static void main(String[] args) {
        LSA lsa = new LSA();
        ArrayList<String> test = new ArrayList<>();
        test.add("Британская полиция знает о местонахождении основателя WikiLeaks");
        test.add("В суде США начинается процесс против россиянина, рассылавшего спам");
        lsa.doLSA(test);
    }
}
