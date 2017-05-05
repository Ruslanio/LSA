package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Dmitry on 17.04.2017.
 */
public class Answerer {

    LinkedList<Double> answersVer = new LinkedList<>();

    public double[] getOneVec(ArrayList<double[]> vecs){
        double[] resVec = new double[vecs.get(0).length];
        for(int i=0;i<resVec.length;i++){
            resVec[i]=0;
        }
        for(int i=0;i<vecs.size();i++){
            for(int j=0;j<resVec.length;j++){
                resVec[j]=resVec[j]+vecs.get(i)[j];
            }
        }
        return resVec;
    }

    public LinkedList<String> getAnswers(HashMap<double[],String> answers, double[] question){
        LinkedList<String> sortedAnswers = new LinkedList<>();
        LinkedList<Double> answersVer = new LinkedList<>();
        Set<double[]> keySet = answers.keySet();
        for(double[] key:keySet){
            double cos = getCos(key,question);
            int i=0;
            while(i<sortedAnswers.size()&&cos<answersVer.get(i)){
                i++;
            }
            sortedAnswers.add(i, answers.get(key));
            answersVer.add(i,cos);
        }
        this.answersVer=answersVer;
        return sortedAnswers;
    }

    double getCos(double[] vec1, double[] vec2){
        double vec1dl = 0;
        double vec2dl = 0;
        double scal = 0;
        double cos=0;
        for(int i=0;i<vec1.length;i++){
            vec1dl=vec1dl+vec1[i]*vec1[i];
            vec2dl=vec2dl+vec2[i]*vec2[i];
            scal=vec1[i]*vec2[i]+scal;
        }
        vec1dl=Math.sqrt(vec1dl);
        vec2dl=Math.sqrt(vec2dl);
        cos=scal/(vec1dl*vec2dl);
        return cos;
    }

    public LinkedList<Double> getAnswersVer() {
        return answersVer;
    }
}
