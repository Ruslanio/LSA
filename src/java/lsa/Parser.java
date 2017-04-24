package lsa;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 17.04.2017.
 */
public class Parser {
    private static final String SYMBOLS = "[^а-яА-Яa-zA-Z\\s]";
    Analyzer analyzer;

    public Parser() {
        analyzer = new RussianAnalyzer();
    }

    public List<String> parseToWords(String doc) throws IOException {
        doc = doc.replaceAll(SYMBOLS, "");
        List<String> result = new ArrayList<>();
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(doc));
        stream.reset();
        while (true) {
            if (!stream.incrementToken()) break;
            AttributeSource token = stream.cloneAttributes();
            CharTermAttribute term = (CharTermAttribute) token.addAttribute(CharTermAttribute.class);
            result.add(term.toString());
        }
        stream.close();
        return result;
    }
    
}
