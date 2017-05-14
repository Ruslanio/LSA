import lsa.LSA;
import lsa.WrongDimensionException;
import service.TxtReader;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Ruslan on 14.05.2017.
 */
public class TempTest {
    public static void main(String[] args) throws WrongDimensionException {
        LSA lsa = new LSA(new TxtReader().loadFile("src/res/test-docs.txt"),2);
        HashMap<String, double[]> words = lsa.getWordsResultMap();
        HashMap<String, double[]> docs = lsa.getDocsResultMap();

        Set<String> keySetWords = words.keySet();
        for (String key : keySetWords) {
            System.out.print(key + " : ");
            double[] vec = words.get(key);
            for (double num : vec) {
                System.out.print(Math.round(num * 100.0)/100.0 + " , ");
            }
            System.out.println();
        }

        System.out.println("====================================================");

        Set<String> keySetDocs = docs.keySet();
        for (String key : keySetDocs) {
            System.out.print(key + " : ");
            double[] vec = docs.get(key);
            for (double num : vec) {
                System.out.print(Math.round(num * 100.0)/100.0 + " , ");
            }
            System.out.println();
        }
    }
}
