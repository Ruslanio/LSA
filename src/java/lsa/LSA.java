package lsa;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class LSA {
    private List<String> significantWords;
    private List<String> documents;
    private static final int STANDARD_DIMENSION = 0;
    private final Parser parser;
    private HashMap<String, double[]> wordsResultMap;
    private HashMap<String, double[]> docsResultMap;

    {
        //initialization block
        parser = new Parser();
    }

    public LSA(List<String> documents) throws WrongDimensionException {
        this.documents = documents;
        doLSA(documents);
    }

    public LSA(List<String> documents, int dimension) throws WrongDimensionException {
        this.documents = documents;
        doLSA(documents, dimension);
    }


    public HashMap<String, double[]> getWordsResultMap() {
        return wordsResultMap;
    }

    public HashMap<String, double[]> getDocsResultMap() {
        return docsResultMap;
    }


    public void doLSA(List<String> documents, int dimension) throws WrongDimensionException {

        List data = preProcessing(documents);
        Matrix matrix = prepareMatrix(data);

        SingularValueDecomposition singularValueDecomposition = new SingularValueDecomposition(matrix);
        Matrix wordsVectors = singularValueDecomposition.getU();
        Matrix docsVectors = singularValueDecomposition.getV();

        if (dimension != STANDARD_DIMENSION) {
            wordsVectors = setCertainDimension(wordsVectors, dimension);
            docsVectors = setCertainDimension(docsVectors, dimension);
        }

        wordsResultMap = matrixToHashMapTransforming(wordsVectors, true);
        docsResultMap = matrixToHashMapTransforming(docsVectors, false);


    }

    public void doLSA(List<String> documents) throws WrongDimensionException {
        doLSA(documents, STANDARD_DIMENSION);
    }

    private List preProcessing(List<String> documents) {
        ArrayList<List> result = new ArrayList<>();

        for (String doc : documents) {
            ArrayList<String> words = null;
            try {
                words = (ArrayList<String>) parser.parseToWords(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.add(words);
        }
        return result;
    }

    private Matrix prepareMatrix(List<List<String>> parsedDocs) {

        HashMap<String, List<Integer>> preparedData = new HashMap<>();
        int docCount = 0;
        for (List<String> list : parsedDocs) {
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

        int n = preparedData.keySet().size();
        int m = parsedDocs.size();
        double[][] matrixData = new double[n][m];

        Set<String> keySet = preparedData.keySet();
        int i = 0;
        ArrayList<Integer> currentValues;
        significantWords = new ArrayList<>();

        for (String key : keySet) {
            significantWords.add(key);
            currentValues = (ArrayList<Integer>) preparedData.get(key);
            for (int j = 0; j < m; j++) {
//                if (currentValues.contains(j)) {
//                    matrixData[i][j] = 1;
//                } else {
//                    matrixData[i][j] = 0;
//                }
                if (currentValues.contains(j)){
                    matrixData[i][j] = matrixData[i][j] + 1;
                }
            }
            i += 1;
        }

        return new Matrix(matrixData);
    }

    private Matrix setCertainDimension(Matrix matrix, int dimension) throws WrongDimensionException {
        if (dimension <= 0 || dimension > matrix.getColumnDimension()) {
            throw new WrongDimensionException();
        }

        double[][] matrixData = matrix.getArray();
        int n = matrix.getRowDimension();
        double[][] resultMatrixData = new double[n][dimension];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < dimension; j++) {
                    resultMatrixData[i][j] = matrixData[i][j];
            }
        }
        return new Matrix(resultMatrixData);
    }

    private HashMap<String, double[]> matrixToHashMapTransforming(Matrix matrix, boolean isWords) {
        HashMap<String, double[]> result = new HashMap<>();
        int n = matrix.getRowDimension();
        int m = matrix.getColumnDimension();
        ArrayList<String> keys;
        if (isWords) {
            keys = (ArrayList<String>) significantWords;
        } else {
            keys = (ArrayList<String>) documents;
        }

        String currentWord;

        for (int i = 0; i < n; i++) {
            currentWord = keys.get(i);
            double[] currentVector = new double[m];

            for (int j = 0; j < m; j++) {
                currentVector[j] = matrix.get(i, j);
            }
            result.put(currentWord, currentVector);
        }

        return result;
    }

}
