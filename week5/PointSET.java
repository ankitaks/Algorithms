import edu.princeton.cs.algs4.*;
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
        return null;
    }
    public Point2D nearest( Point2D p)
    {
        return  null;
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
    }
}
