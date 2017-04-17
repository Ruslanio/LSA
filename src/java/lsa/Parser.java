package lsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Ruslan on 17.04.2017.
 */
public class Parser {
    private static final String SYMBOLS = "[^а-яА-Яa-zA-Z\\s]";
    private Stemmer stemmer;

    public Parser() {
        stemmer = new Stemmer();
    }

    public List<String> parseToWords(List<String> stopWords, String doc){
        doc = doc.replaceAll(SYMBOLS, "");
        doc = doc.toLowerCase();

        ArrayList<String> words = new ArrayList<String>(Arrays.asList(doc.split("\\s")));
        ListIterator<String> iterator = words.listIterator();

        while (iterator.hasNext()) {
            String word = iterator.next();
            if (stopWords.contains(word)) {
                iterator.remove();
            } else {
                iterator.set(stemmer.stem(word));
            }
        }
        return words;
    }
}
