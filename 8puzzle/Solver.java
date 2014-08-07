import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Ireena on 3/29/14.
 */
public class Solver {
    private int totalMoves ;
    private boolean isSolvable ;
    private Stack<Board> solution;

    private class BoardWrapper {
        Board board ;
        int currMoves ;
        Board prev ;
        int preMoves;
        private BoardWrapper(Board board, int moves, Board prev, int preMoves) {
            this.board = board;
            this.currMoves  = moves;
            this.prev = prev ;
            this.preMoves = preMoves ;
        }

        private int priority(){
            return  board.manhattan() + currMoves ;
        }

        public int getPreMoves() {
            return preMoves;
        }

        public int getCurrMoves() {
            return currMoves ;
        }
        public Board getBoard() {
            return board;
        }
        public Board getPrev() {
            return prev;
        }

    }

    private static class BoardComparator implements Comparator<BoardWrapper>{

        public int compare(BoardWrapper o1, BoardWrapper o2) {
            int priority1 = o1.priority() ;
            int priority2 = o2.priority();
            return (priority1 < priority2) ? -1 : priority1 > priority2 ? 1 : 0;
        }
    }


    public Solver(Board initial){
        if(initial.dimension() < 2 || initial.dimension() >= 128 ) {
            return;
        }
        Stack<BoardWrapper> initialStack = new Stack<BoardWrapper>();
        MinPQ<BoardWrapper> minPQ = new MinPQ<BoardWrapper>(new BoardComparator()) ;
        MinPQ<BoardWrapper> minPQTwin =  new MinPQ<BoardWrapper>(new BoardComparator()) ;

        BoardWrapper initialWrapper = new BoardWrapper(initial,0,null,0);
        minPQ.insert(initialWrapper);
        Board twin = initial.twin();
        minPQTwin.insert(new BoardWrapper(twin, 0, null,0));

        while((!minPQ.isEmpty()) ){
             BoardWrapper boardWrapper = minPQ.delMin();
             BoardWrapper twinBoardwrapper = null;
             Board mainBoard = boardWrapper.getBoard();
             int preMoves = boardWrapper.getCurrMoves();
             Board twinBoard = null ;
             if(!minPQTwin.isEmpty()) {
                 twinBoardwrapper = minPQTwin.delMin();
                 twinBoard = twinBoardwrapper.getBoard();
             }
             if(mainBoard.isGoal()){
                 isSolvable = true;
                 initialStack.push(boardWrapper);
                 break;
             } else if((twinBoard != null) && twinBoard.isGoal()){
                 return;

             }
             if(mainBoard != null){
                 for (Board b : mainBoard.neighbors()) {
                    Board prev = boardWrapper.getPrev();
                    if (!b.equals(prev)) {
                         BoardWrapper curr = new BoardWrapper(b,preMoves+1, mainBoard,preMoves);
                         minPQ.insert(curr);
                         initialStack.push(curr);

                    }
                 }
             }
            if(twinBoard != null){
               for (Board b : twinBoard.neighbors()) {
                    Board prev = twinBoardwrapper.getPrev();
                    if (!b.equals(prev)) {
                       int twinPreMoves = twinBoardwrapper.getCurrMoves();
                       minPQTwin.insert(new BoardWrapper(b, twinPreMoves+1, twinBoard,twinPreMoves));
                    }
                }
            }
        }

            solution = new Stack<Board>();
            BoardWrapper curr = null;
            while((curr == null) || (!curr.getBoard().isGoal())){
                curr = initialStack.pop();
            }
            if(curr.getBoard().isGoal()){
                     solution.push(curr.getBoard());
                     while (!initialStack.isEmpty()){
                               BoardWrapper prev = initialStack.pop();
                               if(prev.getBoard().equals(curr.getPrev()) && (prev.getCurrMoves() == curr.getPreMoves())){
                                  solution.push(prev.getBoard());
                                  if(prev.getBoard().equals(initial) ){
                                      break;
                                  }
                                   curr = prev;
                               }
                         }
                         solution.push(initial);
            }

            totalMoves = solution.size()-1;


    }
    public boolean isSolvable(){
        return isSolvable ;
    }

    public int moves(){
        return totalMoves;
    }
    public Iterable<Board> solution(){
       final Iterator<Board> iterator = solution.iterator();
       return new Iterable<Board>() {
           public Iterator<Board> iterator() {
               return iterator;
           }
       } ;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())   {
                StdOut.println(board);
            }

        }
       //System.out.println(sum);

    }
}
