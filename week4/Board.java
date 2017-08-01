import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
public class Board
{
    private final  int[][] mat;
    private final int x, y;
    private final int dim;
    private int manhattan; // var to cache manhattan distance
    private int hamming; // var to cache hamming distance
    public Board(int[][] blocks)
    {
        dim = blocks.length;
        assert dim > 0;
        int t  = -1;
        int t1 = -1;
        mat =  new  int[dim][dim];
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                mat[i][j] = blocks[i][j]; // copy the elements of blocks into mat
                if (mat[i][j] == 0) {
                    t = i;                  // store the index of 0
                    t1 = j;
                }
            }
        }
        assert t >= 0 && t1 >= 0;
        x  =  t;
        y  =  t1;
        manhattan = -1;
        hamming = -1;

    }
    public Iterable<Board> neighbors()
    {
        int size = 0;
        Board[] board = new Board[4];
        int i = x;
        int j = y;
        int[][] matP;
        if (i > 0)
        {
            matP = swap(i-1, j, i, j);
            board[size++] = new Board(matP);
        }
        if (i < (dim - 1))
        {
            matP = swap(i + 1, j, i, j);
            board[size++] = new Board(matP);
        }
        if (j > 0)
        {
            matP = swap(i, j - 1, i, j);
            board[size++] = new Board(matP);
        }
        if (j < (dim - 1))
        {
            matP = swap(i, j + 1, i, j);
            board[size++] = new Board(matP);
        }
        LinkedList<Board> list = new LinkedList<>();
        while (size != 0)
            list.add(board[--size]);
        return list;
    }
    public boolean equals(Object that)
    {
        if (!(that instanceof  Board))
            return false;
           assert that instanceof Board;
            Board y1 =  (Board) that;
            if (y1.dimension() != dim)
                return  false;
            for (int i = 0; i < dim; i++)
            {
                for (int j = 0; j < dim; j++) {
                    if (y1.mat[i][j] != this.mat[i][j])
                        return false;
                }
            }
            return true;

    }
    public boolean isGoal()
    {
        return hamming() == 0;
    }
    public Board twin()
    {
        int[][] ret = copy();
        int i = StdRandom.uniform(0, dim);
        int j = StdRandom.uniform(0, dim);
        if (ret[i][j] == 0)
            return twin();
        int r = StdRandom.uniform(0, dim);
        int c = StdRandom.uniform(0, dim);
        if (ret[r][c] == 0 || (i == r) && (j == c))
            return twin();
        int temp =  ret[i][j];
        ret[i][j] = ret[r][c];
        ret[r][c] = temp;
        Board twin = new Board(ret);
        return twin;
    }
    private int[][] copy()
    {
        int[][] tiles = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tiles[i][j] = mat[i][j];
            }

        }
        return tiles;
    }
    private int[][] swap(int r, int c, int r1, int c1)
    {
        int[][] matP = copy();
        int tmp = matP[r][c];
        matP[r][c] = matP[r1][c1];
        matP[r1][c1] = tmp;
        return  matP;
    }
    public String toString()
    {
        StringBuilder s =  new  StringBuilder();
        s.append(dim + "\n");
        int i = 0;
        while (i < dim) {
            int j = 0;
            while (j < dim) {
                s.append(String.format("%2d ", mat[i][j]));
                j = j + 1;
            }
            s.append("\n");
            i = i + 1;
        }
        return s.toString();
    }
    public int manhattan()
    {
        if (manhattan >= 0)
            return manhattan;
        int sum = 0;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                int t  =  mat[i][j];
                if (t != 0) {
                    int dr = Math.abs(((t - 1) / dim) - i);
                    int dc = Math.abs(((t - 1) % dim) - j);
                    sum += dr + dc;
                }
            }
        }
        manhattan = sum;
        return sum;
    }
    public int hamming()
    {
        if (hamming >= 0)
            return hamming;
        int sum = -1;
        int c = 1;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++, c++)
                if (mat[i][j] != c)
                    sum++;
        hamming = sum;
        return hamming;
    }
    public int dimension()
    {
        return dim;
    }
    
}
