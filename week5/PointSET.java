
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;
public class PointSET {

    private SET<Point2D> set ;
    public PointSET() {
    set = new SET<>();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }
    public int size()
    {
        return set.size();
    }
    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        set.add(p);

    }
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        return set.contains(p);
    }
    public void draw()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D pnt :set) {
            StdDraw.point(pnt.x(),pnt.y());
        }
    }
    public  Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
            throw new IllegalArgumentException("Invalid Arg");
        LinkedList<Point2D> list = new LinkedList<>();
        for (Point2D p:set)
            if (rect.contains(p))
                list.add(p);
        return list;
    }
    public Point2D nearest( Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException("Invalid Arg");
        if (set.isEmpty())
            return null;
        Point2D min = new Point2D(p.x(),p.y());
        double dist = Double.POSITIVE_INFINITY;
        for (Point2D pt:set)
        {
            double cdist =  p.distanceSquaredTo(pt);
            if (cdist <= dist)
            {
                dist = cdist;
                min =  pt;
            }
        }
        return min;
    }
    public static  void main(String[] args)
    {
        System.out.println("Initiating !..");
        PointSET pnt = new PointSET();
        for(int i = 0; i < 10; i++)
        {
            double x = StdRandom.uniform(0.0,1.0);
            double y = StdRandom.uniform(0.0,1.0);
            pnt.insert(new Point2D(x,y));
        }
        pnt.draw();
        Point2D pt = pnt.nearest(new Point2D(0,0));
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(pt.x(),pt.y());
        System.out.println("Terminating !! ");
    }

}

