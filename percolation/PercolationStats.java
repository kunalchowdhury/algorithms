import java.util.Arrays;

/**
 * Created by Ireena on 3/23/14.
 */
public class PercolationStats {
    private int N ;
    private int T ;
    private int count ;
    private double[] fraction;
    private int[][] arr ;
    public PercolationStats(int N, int T){
        this.N = N ;
        this.T = T;
        this.fraction = new double[T];
        int j =0;
        while(j < T){
            count = 0;
            Percolation percolation = new Percolation(N);
            int i= 1;
            StdRandom.setSeed(System.currentTimeMillis());
            while (i < N*N-1){
                int rand = StdRandom.uniform(i, 2*i) % (N*N);
                int x = rand / N + 1;
                int y = rand % N + 1;
                if(!percolation.isOpen(x,y)){
                    percolation.open(x, y);
                    count++;
                }
                i++;
                if(percolation.percolates() ){
                    fraction[j] = (double)count/(N*N) ;
                    j++;
                    break ;
                }
            }

        }

        System.out.println("mean                    = "+mean());
        System.out.println("stddev                  = "+stddev());
        System.out.println("95% confidence interval = "+confidenceLo()+ ", " +confidenceHi());
    }
    public double mean(){
       return StdStats.mean(fraction);
    }
    public double stddev(){
        return StdStats.stddev(fraction);
    }
    public double confidenceLo(){
        double d = (mean() - 1.96*stddev()/Math.sqrt(T));
        return d;
    }

    public double confidenceHi(){
        double d = (mean() + 1.96*stddev()/Math.sqrt(T));
        return d;
    }

    public static void main(String[] args) {

    }
}
