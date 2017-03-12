import java.util.ArrayList;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class LSA {
    private ArrayList<String> dictionary = new ArrayList<>();
    private ArrayList<String> stopWords = new ArrayList<>();
//    private String[] symbols = {",",".",";",":","-","'","!","?"};
    private static final String SYMBOLS = "[^а-яА-Яa-zA-Z\\s]";
    private Stemmer stemmer;

    public LSA() {
        stemmer = new Stemmer();
    }

    public void doLSA(ArrayList<String> documents){
        preProcessing(documents);
    }

    private ArrayList preProcessing(ArrayList<String> documents){
        ArrayList<String[]> result = new ArrayList<>();

        for (String doc:documents) {
            doc = doc.replaceAll(SYMBOLS,"");
            String[] words = doc.split("\\s");

            for (int i = 0; i < words.length; i++){
                words[i] = stemmer.stem(words[i]);
            }
            result.add(words);
        }

        for (String[] words:result) {
            for (String word:words){
                System.out.println(word);
            }
        }
        return result;
    }
}
