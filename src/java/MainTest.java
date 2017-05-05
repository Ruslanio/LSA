import algorithm.Answerer;
import lsa.LSA;
import lsa.Parser;
import lsa.WrongDimensionException;
import service.TxtReader;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ruslan on 10.03.2017.
 */
//при добавлении статьи в docs.txt необходимо после каждой статьи ставить знак-разделитель "=-=" в отдельной строке

public class MainTest {
    private static final String DOCS_PATH = "src/res/docs.txt";

    public static void main(String[] args) {

        LSA lsa = new LSA();

        ArrayList<String> test = new TxtReader().loadFile(DOCS_PATH);


        HashMap<String, double[]> result = null;

        //если даешь определенную размерность, обработай исключение, если нет - не парься, оно не вылезет
        try {
            result = (HashMap<String, double[]>) lsa.doLSA(test);
        } catch (WrongDimensionException e) {
            e.printStackTrace();
        }


        Set<String> keySet = result.keySet();
        Object[] keys = keySet.toArray();
        ArrayList<String> keysArray = new ArrayList<>();

        for (int i = 0; i < keys.length; i++) {
            System.out.println(keys[i]);
            keysArray.add((String) keys[i]);
        }


        HashMap<double[], String> answers = new HashMap<>();
        Parser parser = new Parser();
        Answerer answerer = new Answerer();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String question = scanner.nextLine();
            System.out.println();

            ArrayList<String> parsedQuestion = null;
            try {
                parsedQuestion = (ArrayList<String>) parser.parseToWords(question);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<double[]> questionVecs = new ArrayList<>();

            for (String quest : parsedQuestion) {
                questionVecs.add(result.get(quest));
            }

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
