import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;
import java.util.NoSuchElementException;
public class KdTree {
    private Node root;
    private int size;
    private static class Node
    {
        private Point2D pt;
        private RectHV rect;
        private Node rb;
        private Node lb;
        boolean rv;
        Node(Point2D pt)
        {
            this.pt = pt;
        }
    }
    public KdTree()
    {
        root = null;
        size = 0;
    }
    private Node insert(Node x, Point2D pt, boolean level)
    {
        if (x == null) {
            return new Node(pt);
        }
        int cmp =   pt.X_ORDER.compare(pt, x.pt);
        if (level)
               cmp =   pt.Y_ORDER.compare(pt, x.pt);
        if (cmp < 0) x.lb = insert(x.lb, pt, !level);
        if (cmp >= 0) x.rb = insert(x.rb, pt, !level);
        return x;
    }
    public boolean isEmpty()
    {
        return root == null;
    }
    public int size()
    {
        return size;
    }
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        if (isEmpty())
            return false;

        return contain(root, p, false);
    }
    private  boolean contain(Node x, Point2D pt, boolean level)
    {
        if (x != null)
        {
            if (x.pt.equals(pt))
                return true;
            else
            {
                int cmp =   pt.X_ORDER.compare(pt, x.pt);
                if (level)
                    cmp =   pt.Y_ORDER.compare(pt, x.pt);
                if (cmp < 0) return contain(x.lb, pt, !level);
                else
                    return contain(x.rb, pt, !level);

            }
        }
        return false;
    }
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
            throw new IllegalArgumentException("Invalid Arg");
        LinkedList<Point2D> list =  new LinkedList<>();
        range(root, list, rect);
        return list;
    }
    private void range(Node x, LinkedList<Point2D> list, RectHV rect)
    {
        if (x != null)
        {
            if (rect.intersects(x.rect))
            {
                if (rect.contains(x.pt))
                    list.add(x.pt);
                range(x.lb, list, rect);
                range(x.rb, list, rect);
            }
        }
    }
    public void draw()
    {
        drawRect(root, false);
    }
    private  static  void drawRect( Node x, boolean level)
    {
        if (x != null) {
            drawPoint(x.pt);
            if (level) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.pt.y(), x.rect.xmax(), x.pt.y());
            }
            else
                StdDraw.line(x.pt.x(), x.rect.ymin(), x.pt.x(), x.rect.ymax());
            drawRect(x.lb, !level);
            drawRect(x.rb, !level);
        }
    }
    private static void drawPoint(Point2D pt)
    {
        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(pt.x(), pt.y());
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.RED);
    }

    public static void main(String[] args) {
        String filename = "inp.txt";//args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.draw();
        StdDraw.setPenColor(StdDraw.CYAN);
        Point2D p = new Point2D(0.070313, 0.660156);//StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));

        StdDraw.setPenRadius(0.02);
        StdDraw.point(p.x(),p.y());

        Point2D px=kdtree.nearest(p);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.03);
        StdDraw.point(px.x(), px.y());

    }

    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        else {
            if (!contains(p)) {
                root = insert(root, p, false);
                rectify(root, null, false);
                size++;
            }
        }
    }
    private void rectify( Node x, Node parent, boolean level)
    {
        if (x == null) return;
        if (parent == null)
        {
            assert  x == root;
            x.rect = new RectHV(0, 0, 1, 1);
            x.rv = true;
            rectify(x.lb, root, true);
            rectify(x.rb, root, true);
        }
        else
        { if (!x.rv)
            if (x == parent.lb)
            {   double x1, x2;
                double y1, y2;
                x1  = parent.rect.xmin();
                y1  = parent.rect.ymin();
                if (level)
                {   x2  = parent.pt.x();
                    y2  = parent.rect.ymax();
                }
                else
                {
                    x2 = parent.rect.xmax();
                    y2 = parent.pt.y();
                }
                x.rect = new RectHV(x1, y1, x2, y2);
                x.rv = true;
            }
            else
            {   double x1, x2;
                double y1, y2;
                x2  = parent.rect.xmax();
                y2  = parent.rect.ymax();
                if (level)
                {
                    x1  = parent.pt.x();
                    y1  = parent.rect.ymin();
                }
                else
                {
                    x1 = parent.rect.xmin();
                    y1 = parent.pt.y();
                }
                x.rect = new RectHV(x1, y1, x2, y2);
            }
            x.rv = true;
            rectify(x.lb, x, !level);
            rectify(x.rb, x, !level);
        }
    }
    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        if (isEmpty())
            throw new NoSuchElementException("");
        Point2D chmp = misc(root, p, false,root.pt);


        return chmp;
    }
    private Point2D misc(Node x, Point2D pt, boolean level, Point2D chmp)
    {
        if (x == null) {
            return chmp;
        }
        double d;
        int cmp =   pt.X_ORDER.compare(pt, x.pt);
        d = pt.x() - x.pt.x();
        if (level) {
            cmp = pt.Y_ORDER.compare(pt, x.pt);
            d = pt.y() - x.pt.y();
        }
        d *= d;
        double dist = x.pt.distanceSquaredTo(pt);
        if (cmp >= 0)
        {
             Point2D pl =  misc(x.rb, pt, !level, chmp);
             double d1 = pl.distanceSquaredTo(pt);
             if (d1 > dist)
                 pl = x.pt;
             d1 =  Math.min(d1, dist);
            if (d1 < d)
             {
                 return pl;
             }
             return  misc(x.lb, pt, !level, pl);

        }
        else
        {
            Point2D pl =  misc( x.lb, pt, !level, chmp);
            double d1 = pl.distanceSquaredTo(pt);
            if (d1 > dist)
                pl = x.pt;
            d1 =  Math.min(d1, dist);
            if (d1 < d )
            {
                return pl;
            }
            return  misc(x.rb, pt, !level, pl);
        }

    }
}

