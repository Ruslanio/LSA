import lsa.LSA;
import lsa.WrongDimensionException;
import service.TxtReader;

import java.util.ArrayList;

/**
 * Created by Ruslan on 12.05.2017.
 */
public class TimeTest {
    private static final String DOCS_PATH = "src/res/docs.txt";

    public static void main(String[] args) throws WrongDimensionException, InterruptedException {
        TxtReader reader = new TxtReader();
        ArrayList docs = reader.loadFile(DOCS_PATH);
        System.out.println("size = " + docs.size());
        long result = 0;
        int tries = 10;
        for (int i = 0;i < tries; i++){
            long start = System.currentTimeMillis();
            LSA lsa = new LSA(docs);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            result = result + (end - start);
            Thread.sleep(5000);
        }
        result = result/tries;
        System.out.println("average time : " + result);
    }
}
