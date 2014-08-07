/**
 * Created by Ireena on 3/22/14.
 */
public class Percolation {
    private int[] open;
    private WeightedQuickUnionUF unionFind;
    private int length;

    public Percolation(int N) {
        this.length = N;
        open = new int[N * N + 2];
        for (int i = 1; i <= N * N; i++) {
            open[i] = -1; // set all closed

        }
        open[0] = 0;
        open[N*N + 1]= N*N + 1;
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 1; i <= N; i++) {
            unionFind.union(open[0], i);
        }

        for (int j = N * (N - 1) + 1; j <= N * N; j++) {
            unionFind.union(open[N * N + 1], j);
        }
    }

    private boolean isValidPosition(int i){
         if(i < 1 || i > length){
            return false ;
         }
         return true;
    }


    public void open(int i, int j) {
        if(!isValidPosition(i) || !isValidPosition(j)){
            System.out.println(i+" , "+j);
            throw new IndexOutOfBoundsException();
        }
        // calculate the position
        int pos = length * (i - 1) + j;
        open[pos] = pos;
        // if the neighbouring positions are open then perform union
        if (j > 1) {
            int left = pos - 1;
            if (open[left] != -1) {
                unionFind.union(left, pos);
            }
        }
        if (j < length) {
            int right = pos + 1;
            if (open[right] != -1) {
                unionFind.union(right, pos);
            }
        }
        if (i > 1) {
            int above = length * (i - 2) + j;
            if (open[above] != -1) {
                unionFind.union(above, pos);
            }
        }
        if (i < length) {
            int below = length * i + j;
            if (open[below] != -1) {
                unionFind.union(below, pos);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if(!isValidPosition(i) || !isValidPosition(j)){
            throw new IndexOutOfBoundsException();
        }

        int pos = length * (i - 1) + j;
        return open[pos] != -1;
    }

   public boolean isFull(int i, int j) {
       if(!isValidPosition(i) || !isValidPosition(j)){
           throw new IndexOutOfBoundsException();
       }
       boolean result = false;
       if (!isOpen(i, j)) {
           result = false;
       } else{
           int pos = length * (i - 1) + j;
           result = unionFind.connected(0, pos);
       }
       return result;
   }

    public boolean percolates(){
        return unionFind.connected(0, length * length + 1);
    }

}
