import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.LinkedList;
public class Solver {
    private final Tile initial;
    private Tile finalT;
    private LinkedList<Board> list;
    private class Tile implements Comparable<Tile> {
        private final Tile previous;
        private final Board brd;
        private final int moves;
        private final int priority;
        public Tile(Board brd) {
            previous = null;
            this.brd = brd;
            moves = 0;
            priority = brd.manhattan(); //+moves;
        }
        public Tile(Board board, Tile previous) {
            this.previous = previous;
            brd =  board;
            moves = previous.moves + 1;
            priority = brd.manhattan(); //+moves;
        }
        public int compareTo(Tile that) {
            return this.priority - that.priority + this.moves - that.moves;
        }
    }
    public Solver(Board initials) {
        if (initials == null)
            throw new IllegalArgumentException("Invalid argument ");
        initial =  new Tile(initials);
        MinPQ<Tile> normal = new MinPQ<>();
        normal.insert(initial);
        MinPQ<Tile> twin = new MinPQ<>();
        Board twinBoard = initial.brd.twin();
        twin.insert(new Tile(twinBoard));
        while (true)
        {
            finalT = solve(normal);
            if (finalT != null || solve(twin) != null)
                return;
        }
    }
    public boolean isSolvable() {
        return finalT != null;
    }
    private  Tile solve(MinPQ<Tile> pq)
    {
        if (pq.isEmpty())
            return  null;
        Tile best = pq.delMin();
        if (best.brd.isGoal())
            return best;
        for (Board neighbor: best.brd.neighbors()) {

            if (best.previous == null || !neighbor.equals(best.previous.brd))
            {
                pq.insert(new Tile(neighbor, best));
            }
        }
        return null;
    }
    private void makeList(Tile tile)
    {
        if (tile == null)
            return;
        list = new LinkedList<>();
        while (tile != null) {
            list.addFirst(tile.brd);
            tile = tile.previous;
        }
    }
    public Iterable<Board> solution()
    {
        if (list == null)
        makeList(finalT);
        return list;
    }
    public int moves()
    {
        return isSolvable() ? finalT.moves : -1;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();



        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

