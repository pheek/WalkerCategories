package eu.gressly.math.random;

import java.util.Arrays;

/**
 * Returns a category number with a given probability.
 * Categories are numbered starting at 0 (zero) ending at "n-1".
 * Example:
 * <pre>  
 *   WalkerCategories wc;
 *   double [] wghts = new double[] {2,5,3};
 *   wc = new WalkerCategories(wghts);
 *   // ... later : ...
 *   int kat = wc.getWeightedCategory(Math.random());
 * </pre>
 * After execution of the above code, the variable "kat" will 
 * have a value of 0, 1 or 2 with probabilities 20%, 50% or 30%
 * respectively.
 * Original code by A. J. Walker 1977 (see Knuth, "The art of computer programing vol. 2"
 * @author Philipp Gressly (phi@gressly.ch)
 */
/*
 * History: first Implementation: Jul 26, 2008
 * Bugs   :
 */
public class WalkerCategories implements WeightedRandomCategories {

    int totalNrOfCategories; // nr of categories
    
    private class CategoryProbabilityPair implements Comparable<CategoryProbabilityPair> {
        double p; // Probability
        int c; // category
        @Override
        public String toString() {
            return c + ". " + p;
        }
        /*
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(CategoryProbabilityPair oo) {
           if (this.p < oo.p) {
               return -1;
           } 
           return 1;
        }
    }
    
    public int getNrOfCategories() {
        return totalNrOfCategories;
    }
    
    CategoryProbabilityPair PY[], QA[];
    
    /**
     * See example in the class documentation.
     */
    public WalkerCategories (double [] weights) {
        weights = normalize(weights);
        init(weights);
        spread();
    }
    
    private void spread() {    
        int kat;
        while(QA  .length > 0) {
            kat = QA[0].c;
            PY[kat].p = totalNrOfCategories * QA[0].p;
            PY[kat].c = QA[QA.length - 1].c;
            if(QA.length > 1) {
                reduce();
            } else {
                return;
            }
        }
    }
    
    private void reduce() {
        int n = QA.length;
        double pNew = QA[0].p + QA[n-1].p - 1.0/totalNrOfCategories;
        int katNew = QA[n-1].c;
        int pos;
        
        CategoryProbabilityPair tmpArr[] = new CategoryProbabilityPair[QA.length - 1];
        CategoryProbabilityPair newPK = new CategoryProbabilityPair();
        newPK.p = pNew;
        newPK.c = katNew;
        tmpArr[0] = newPK;
        pos = 1;
        while(pos < tmpArr.length) {
            tmpArr[pos] = QA[pos];
            pos = pos + 1;
        }
        Arrays.sort(tmpArr);
        QA = tmpArr;
    }
    
    private void init(double[] weights) {
       totalNrOfCategories = weights.length;
       PY = new CategoryProbabilityPair[totalNrOfCategories];
       QA = new CategoryProbabilityPair[totalNrOfCategories];
       int pos = 0;
       while(pos < totalNrOfCategories) {
           QA[pos] = new CategoryProbabilityPair();
           PY[pos] = new CategoryProbabilityPair();
           QA[pos].p = weights[pos];
           QA[pos].c = pos;
           pos = pos + 1;
       }
       Arrays.sort(QA);
    }

    /**
     * Sum of doubles will be 1.0
     * @param weights
     * @return a copy of the original "weights", but 
     */
    private double[] normalize(double[] weights) {
        double [] result = weights.clone();
        double sum = 0.0;
        for(double d : weights) {
            sum = sum + d;
        }
        int pos = 0;
        while (pos < result.length) {
            result[pos] =  result[pos] / sum;
            pos = pos + 1;
        }  
        return result;
    }

    /*
     * @see eu.gressly.hw.math.random.WeightedRandom#getWeightedCategory(double)
     */
    @Override
    public int getWeightedCategory(double uniform01) {
        double p = totalNrOfCategories * uniform01;
        int idx = (int) p;
        double rest = p - idx;
        if(rest < PY[idx].p) {
            return idx;
        }
        return PY[idx].c;
    }


}  // end of class WalkerCategories