package eu.gressly.math.random;

/**
 * Test weighted random categories
 * @author Philipp Gressly (phi AT gressly DOT ch)
 */
public class Example {

    public static void main(String[] args) {
       new Example().main();
    }
    
    void main() {
       WeightedRandomCategories wrc;
       double [] wghts = new double[] {533, 420, 260, 150, 104};
       wrc = new WalkerCategories(wghts);
       int[] einzeltag = new int[wghts.length];
       int n = (533+420+260+150+104)*10;
       while (n > 0) {
           int kat = wrc.getWeightedCategory(Math.random());
           einzeltag[kat] ++;
           n = n-1;
       }
       for(int kk : einzeltag) {
           System.out.print(kk + "  " );
       }
       System.out.println();
    }

}
 // end of class Example