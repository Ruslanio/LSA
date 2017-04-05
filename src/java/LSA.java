import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.util.*;

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
        this.stopWords = stopWords;
    }

    public void doLSA(ArrayList<String> documents) {

        ArrayList data = preProcessing(documents);
        Matrix matrix = prepareMatrix(data);

        SingularValueDecomposition singularValueDecomposition = new SingularValueDecomposition(matrix);
        Matrix finalVectors = singularValueDecomposition.getU();

        int n = finalVectors.getRowDimension();
        int m = finalVectors.getColumnDimension();
        printGrid(finalVectors.getArrayCopy(), n, m);


    }

    private ArrayList preProcessing(ArrayList<String> documents) {
        ArrayList<List> result = new ArrayList<>();

        for (String doc : documents) {
            doc = doc.replaceAll(SYMBOLS, "");
            doc = doc.toLowerCase();

            ArrayList<String> words = new ArrayList<String>(Arrays.asList(doc.split("\\s")));
            Iterator<String> iterator = words.iterator();

            while (iterator.hasNext()) {
                String word = iterator.next();
                if (stopWords.contains(word)) {
                    iterator.remove();
                }
                word = stemmer.stem(word);
            }
            result.add(words);
        }

        for (List<String> words : result) {
            for (String word : words) {
                System.out.println(word);
            }
        }
        return result;
    }

    private Matrix prepareMatrix(ArrayList<List> rawData) {

        HashMap<String, ArrayList<Integer>> preparedData = new HashMap<>();
        int docCount = 0;
        for (List<String> list : rawData) {
            for (String wordString : list) {
                if (preparedData.containsKey(wordString)) {
                    preparedData.get(wordString).add(docCount);
                } else {
                    ArrayList<Integer> docContains = new ArrayList<>();
                    docContains.add(docCount);
                    preparedData.put(wordString, docContains);
                }
            }
            docCount += 1;
        }

        preparedData.entrySet().removeIf(element -> element.getValue().size() == 1);

//        Iterator<Map.Entry<String,ArrayList<Integer>>> iterator = preparedData.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String,ArrayList<Integer>> element = iterator.next();
//            if (element.getValue().size() == 1){
//                iterator.remove();
//            }
//        }
        int n = preparedData.keySet().size();
        int m = rawData.size();
        double[][] matrixData = new double[n][m];

        Set<String> keySet = preparedData.keySet();
        int i = 0;
        ArrayList<Integer> currentValue;

        for (String key:keySet) {
            currentValue = preparedData.get(key);
            for (int j = 0 ; j < m ; j++) {
                if (currentValue.contains(j)) {
                    matrixData[i][j] = 1;
                } else {
                    matrixData[i][j] = 0;
                }
            }
            i += 1;
        }

        printGrid(matrixData, n, m);

        Matrix resultMatrix = new Matrix(matrixData);

        return resultMatrix;
    }

    public void printGrid(double[][] a, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

}
