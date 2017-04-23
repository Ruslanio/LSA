import lsa.LSA;
import lsa.Parser;

import java.util.*;

/**
 * Created by Ruslan on 10.03.2017.
 */
//при добавлении статьи в docs.txt необходимо после каждой статьи ставить знак-разделитель "=-=" в отдельной строке

public class MainTest {
    private static ArrayList<String> stopWords;
    private static final String DOCS_PATH = "src/res/docs.txt";

    public static void main(String[] args) {

        stopWords = new TxtReader().loadFile("src/res/stop-words.txt");
        if (stopWords == null)
            System.out.println("null");

        LSA lsa = new LSA();

        ArrayList<String> test = new TxtReader().loadFile(DOCS_PATH);


        HashMap<String, double[]> result = (HashMap<String, double[]>) lsa.doLSA(test);
        Set<String> keySet = result.keySet();
        Object[] keys = keySet.toArray();
        ArrayList<String> keysArray = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            System.out.println(keys[i]);
            keysArray.add((String) keys[i]);
        }
        HashMap<double[], String> answers = new HashMap<>();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String question = scanner.nextLine();
            System.out.println();
            Parser parser = new Parser();
            ArrayList<String> parsedQuestion = (ArrayList<String>) parser.parseToWords(stopWords, question);
            ArrayList<double[]> questionVecs = new ArrayList<>();
            for (String quest : parsedQuestion) {
                questionVecs.add(result.get(quest));
            }
            Answerer answerer = new Answerer();
            double[] questionOneVec = answerer.getOneVec(questionVecs);
            LinkedList<String> sortedAns = answerer.getAnswers(answers, questionOneVec);
            LinkedList<Double> ansVer = answerer.getAnswersVer();
            for (int i = 0; i < sortedAns.size(); i++) {
                if (i < 5) {
                    System.out.print(sortedAns.get(i));
                    System.out.println(ansVer.get(i));
                }
            }
            System.out.println();
            String answer = scanner.nextLine();
            if (!answers.containsValue(answer)) {
                answers.put(questionOneVec, answer);
            }
            System.out.println();

        }

    }
}
