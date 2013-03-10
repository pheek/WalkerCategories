package eu.gressly.math.random;

/**
 * Weighted random categories.
 * Source: Walker s.  Knuth II (seminumerical algorithms).
 * 
 * Example: (eg. WalkerCategories as implementing class)
 * 
 * <pre>
 *   wghts = new double [] {20, 50, 30, 20};
 *   WalkerCategories wc = new WalkerCategories(wghts);
 *   int cat = wc.getWeightedCategory(Math.random());
 *   // cat will be 0, 1, 2, 3 with probabilities 20:50:30:20
 * </pre>
 * 
 * @author Philipp Gressly (phi@gressly.ch)
 */
/*
 * History: first Implementation: Jul 26, 2008
 */
public interface WeightedRandomCategories {
    /**
     * Returns the number [0 .. n-1] of a category.
     * 
     * The weights and count of the categories will be given
     * in the impementing class.
     * 
     * @param uniform01 specifies a uniform distributed random number 
     *                                    between 0 (inkl.) and 1 (exkl.)
     * @return Category number 0 .. n-1
     */
   int getWeightedCategory(double uniform01);
}
