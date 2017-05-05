package lsa;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ruslan on 10.03.2017.
 */
public class LSA {
    private ArrayList<String> significantWords;
    private static final int STANDARD_DIMENSION = -1;
    private final Parser parser;

    public LSA() {
        parser = new Parser();
    }


    public Map<String, double[]> doLSA(ArrayList<String> documents, int dimension) throws WrongDimensionException {

        ArrayList data = preProcessing(documents);
        Matrix matrix = prepareMatrix(data);

        SingularValueDecomposition singularValueDecomposition = new SingularValueDecomposition(matrix);
        Matrix finalVectors = singularValueDecomposition.getU();

        if (dimension != STANDARD_DIMENSION){
            finalVectors = setCertainDimension(finalVectors,dimension);
        }

        int n = finalVectors.getRowDimension();
        int m = finalVectors.getColumnDimension();

        HashMap<String, double[]> output = new HashMap<>();
        String currentWord;

        for (int i = 0; i < n; i++) {
            currentWord = significantWords.get(i);
            double[] currentVector = new double[m];

            for (int j = 0; j < m; j++) {
                currentVector[j] = finalVectors.get(i, j);
            }
            output.put(currentWord, currentVector);
        }
        return output;
    }

    public Map<String, double[]> doLSA(ArrayList<String> documents) throws WrongDimensionException {
        return doLSA(documents,STANDARD_DIMENSION);
    }

    private ArrayList preProcessing(ArrayList<String> documents) {
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

        int n = preparedData.keySet().size();
        int m = rawData.size();
        double[][] matrixData = new double[n][m];

        Set<String> keySet = preparedData.keySet();
        int i = 0;
        ArrayList<Integer> currentValue;
        significantWords = new ArrayList<>();

        for (String key : keySet) {
            significantWords.add(key);
            currentValue = preparedData.get(key);
            for (int j = 0; j < m; j++) {
                if (currentValue.contains(j)) {
                    matrixData[i][j] = 1;
                } else {
                    matrixData[i][j] = 0;
                }
            }
            i += 1;
        }

        return new Matrix(matrixData);
    }

    private Matrix setCertainDimension(Matrix matrix,int dimension) throws WrongDimensionException {
        if (dimension <= 0 || dimension > matrix.getColumnDimension()){
            throw new WrongDimensionException();
        }
        double[][] matrixData = matrix.getArray();
        double[][] resultMatrixData = new double[matrix.getRowDimension()][dimension];

        for (int i = 0; i < matrix.getRowDimension(); i++){
            for (int j = 0; j < dimension; j++){
                resultMatrixData[i][j] = matrixData[i][j];
            }
        }
        return new Matrix(resultMatrixData);
    }

}
