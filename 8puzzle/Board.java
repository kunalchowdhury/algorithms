import java.util.Iterator;

/**
 * Created by Ireena on 3/29/14.
 */
public class Board {
    private int[][] blocks ;
    private int N ;
    private Iterable<Board> thyNeighbor ;
    private int manhattanVal =-1 ;
    public Board(int[][] blocks){
           this.blocks = blocks ;
           this.N = blocks.length;
    }
    public int dimension(){
           return N ;
    }
    public int hamming(){
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = blocks[i][j];
                if(val != 0){
                    int[] arr = getCoordsFromInt(val);
                    if((i != arr[0]-1) || (j != arr[1]-1)){
                       sum ++;
                    }
                }
            }
        }
        return sum;
    }

    public int manhattan(){
        if(manhattanVal == -1){
            int sum = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int val = blocks[i][j];
                    if(val != 0) {
                        int[] arr = getCoordsFromInt(val);
                        sum += (Math.abs((i+1) - arr[0])+Math.abs((j+1) - arr[1]));
                    }
                }
            }
            manhattanVal = sum;
        }

        return manhattanVal;
    }

    public boolean isGoal(){
        /*for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                    if(blocks[i][j] !=0){
                        int[] arr = getCoordsFromInt(blocks[i][j]) ;
                        if((arr[0] != i+1) || (arr[1] != j+1)){
                            return false;
                        }
                    }
            }
        }*/
       return manhattan() == 0;
    }

    public Board twin(){
        boolean isZeroOnFirst = false;

        int[][] targetArr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                   if((i == 0) && (blocks[i][j] ==0)){
                       isZeroOnFirst = true;
                   }
                   targetArr[i][j] = blocks[i][j];
            }
        }

        int target =  isZeroOnFirst ? 1: 0;
        int temp = targetArr[target][0];
        targetArr[target][0]=targetArr[target][1];
        targetArr[target][1] = temp;
        return new Board(targetArr) ;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Board) || (this.N != ((Board)obj).N)){
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                  if(blocks[i][j] != ((Board)obj).blocks[i][j]){
                      return false;
                }
            }
            
        }

        return true ;

    }
    public Iterable<Board> neighbors(){
        if(thyNeighbor == null){
            int x = 0;
            int y = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(blocks[i][j] ==0 ){
                        x = i ;
                        y = j;
                    }
                }
            }
            Board[] boards;
            if(((x ==0) || (x == N-1)) && ((y ==0)||(y == N-1))){
                int[][] a1= copyArr();
                int[][] a2= copyArr();
                boards = new Board[]{new Board(a1),new Board(a2)};
                if((x == 0) && (y == 0)){
                    swap(a1[0],null,0,1);
                    swap(a2[0],a2[1],0,0);
                }else if((x == N-1) && (y == 0)) {
                    swap(a1[N-1],null,0,1);
                    swap(a2[N-2],a2[N-1],0,0);
                }else if((x == 0) && (y == N-1)) {
                    swap(a1[0],null,N-2,N-1);
                    swap(a2[0],a2[1],N-1,N-1);
                } else{
                    swap(a1[N-1],null,N-2,N-1);
                    swap(a2[N-2],a2[N-1],N-1,N-1);
                }
            }else {
                if ((y == 0) || (y == N - 1) || (x == 0) || (x == N - 1)) {
                    int[][] a1 = copyArr();
                    int[][] a2 = copyArr();
                    int[][] a3 = copyArr();
                    boards = new Board[]{new Board(a1), new Board(a2), new Board(a3)};
                    if (y == 0) {
                        swap(a1[x], null, 0, 1);
                        swap(a2[x], a2[x - 1], 0, 0);
                        swap(a3[x], a3[x + 1], 0, 0);
                    } else if (y == N - 1) {
                        swap(a1[x], null, N - 2, N - 1);
                        swap(a2[x], a2[x - 1], N - 1, N - 1);
                        swap(a3[x], a3[x + 1], N - 1, N - 1);
                    } else if (x == 0) {
                        swap(a1[0], null, y, y - 1);
                        swap(a2[0], null, y, y + 1);
                        swap(a3[0], a3[1], y, y);
                    } else if (x == N - 1) {
                        swap(a1[N - 1], null, y, y - 1);
                        swap(a2[N - 1], null, y, y + 1);
                        swap(a3[N - 2], a3[N - 1], y, y);
                    }

                } else {
                    int[][] a1 = copyArr();
                    int[][] a2 = copyArr();
                    int[][] a3 = copyArr();
                    int[][] a4 = copyArr();
                    boards = new Board[]{new Board(a1), new Board(a2), new Board(a3), new Board(a4)};
                    swap(a1[x], null, y, y - 1);
                    swap(a2[x], null, y, y + 1);
                    swap(a3[x], a3[x - 1], y, y);
                    swap(a4[x], a4[x + 1], y, y);
                }

            }

            thyNeighbor = new Boards(boards);
        }

        return thyNeighbor ;
    }
    private static class Boards implements Iterable<Board>{
        Board[] boards ;

        private Boards(Board[] boards) {
            this.boards = boards;
        }


        public Iterator<Board> iterator() {
            return new BoardIterator(boards);
        }
    }

    private static class BoardIterator implements Iterator<Board>{
        Board[] boards ;
        int curr ;

        private BoardIterator(Board[] boards) {
            this.boards = boards;
            this.curr = 0;
        }

        public boolean hasNext() {
            return curr != boards.length;
        }

        public Board next() {
            return boards[curr++];
        }

        public void remove() {
              throw new UnsupportedOperationException();
        }
    }


    private int[][] copyArr(){
        int[][] arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks[i], 0, arr[i], 0, N);
        }
        return arr ;
    }

    private void swap(int[] arr1,int[] arr2, int i, int j){
        if(arr2 == null){
            int temp = arr1[i] ;
            arr1[i] = arr1[j];
            arr1[j] = temp;
        }else{
            int temp = arr1[i];
            arr1[i] = arr2[j];
            arr2[j] = temp ;
        }
    }

    private int[] getCoordsFromInt(int i){
            int x = (i % N)!=0 ? ((i/N)+1) : (i/N);
            int y = (i % N)!=0 ? (i % N) : N ;
            return new int[]{x,y} ;
    }

    public static void main(String[] args) {


    }


}
