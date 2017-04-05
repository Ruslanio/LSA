import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class LSA {
    private ArrayList<String> dictionary;
    private ArrayList<String> stopWords;
    private static final String SYMBOLS = "[^а-яА-Яa-zA-Z\\s]";
    private Stemmer stemmer;

    public LSA(ArrayList stopWords) {
        stemmer = new Stemmer();
        this.stopWords =stopWords;
    }

    public void doLSA(ArrayList<String> documents){

        preProcessing(documents);
    }

    private ArrayList preProcessing(ArrayList<String> documents){
        ArrayList<List> result = new ArrayList<>();

        for (String doc:documents) {
            doc = doc.replaceAll(SYMBOLS,"");
            doc = doc.toLowerCase();

            ArrayList<String> words = new ArrayList<String>(Arrays.asList(doc.split("\\s")));
            Iterator<String> iterator = words.iterator();

            while (iterator.hasNext()){
                String word = iterator.next();
                if (stopWords.contains(word)){
                    iterator.remove();
                }
                word = stemmer.stem(word);
            }
            result.add(words);
        }

        for (List<String> words:result) {
            for (String word:words){
                System.out.println(word);
            }
        }
        return result;
    }

    private void doTheMatrix(ArrayList<List> data){

    }
}
