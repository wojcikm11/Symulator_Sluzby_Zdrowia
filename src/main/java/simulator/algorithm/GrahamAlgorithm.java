package simulator.algorithm;

import javafx.geometry.Point2D;
import simulator.math2D.Math2D;
import simulator.structures.DynamicTableStack;
import simulator.structures.StackImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GrahamAlgorithm implements BorderAlgorithm {
    private List<Point2D> pointList;
    private Point2D array[];
    private List<Point2D> resultList;
    private StackImplementation<Point2D> grahamStack;
    private final int n;

    public GrahamAlgorithm(List<Point2D> pointList) {
        this.pointList = pointList;
        this.array = new Point2D[pointList.size()];
        this.resultList = new ArrayList<>();
        this.grahamStack = new DynamicTableStack<>();
        this.n = array.length;
    }

    public List<Point2D> scan() {
        StackImplementation<Point2D> stack = new DynamicTableStack<>();
        int iter = 0;
        for(Point2D point : pointList) {
            array[iter] = point;
            iter++;
        }

        int minId = bottomPointId();


        Point2D temp = array[0];
        array[0] = array[minId];
        array[minId] = temp;

        Arrays.sort(array, 1, n, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D o1, Point2D o2) {
                int o = Math2D.orientation(array[0], o1, o2);
                if (o == 0)
                    return (Math2D.squareOfDistance(array[0], o1) >= Math2D.squareOfDistance(array[0], o2)) ? 1 : -1;

                return (o == 2) ? -1 : 1;
            }
        });

        int newArraySize = 1;
        for (int i=1; i<n; i++) {
            while (i < n-1 && Math2D.orientation(array[0], array[i], array[i+1]) == 0)
                i++;

            array[newArraySize] = array[i];
            newArraySize++;
        }

        if(newArraySize < 3)
            throw new IllegalArgumentException("Cannot make a convex hull from less than 3 points");

        stack.push(array[0]);
        stack.push(array[1]);
        stack.push(array[2]);

        for (int i = 3; i < newArraySize; i++) {
            while (Math2D.orientation(stack.lookUpUnderTheTop(), stack.lookUpTop(), array[i]) != 2)
                stack.pop();
            stack.push(array[i]);
        }

        try {
            grahamStack = (DynamicTableStack)((DynamicTableStack<Point2D>) stack).clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Stack cannot be cloned!");
        }

        while (!stack.isEmpty())
            resultList.add(stack.pop());

        return resultList;
    }

    @Override
    public boolean isInside(Point2D point) {
        StackImplementation<Point2D> stack = null;

        try {
            stack = (DynamicTableStack)((DynamicTableStack<Point2D>) grahamStack).clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Stack cannot be cloned!");
        }

        Point2D end= stack.pop(), begin, point0 = end;
        boolean lastEdge = false;
        while(!stack.isEmpty() || !lastEdge) {

            if(!stack.isEmpty()) {
                begin = end;
                end = stack.pop();
            } else {
                begin = end;
                end = point0;
                lastEdge = true;
            }

            if(point.equals(begin) || point.equals(end))
                return true;

            if(Math2D.orientation(begin, end, point) == 2)
                return false;
            else if(Math2D.orientation(begin, end, point) == 0) {
                Point2D beginPoint = new Point2D(point.getX() - begin.getX(), point.getY() - begin.getY());
                Point2D beginEnd = new Point2D(end.getX() - begin.getX(), end.getY() - begin.getY());

                if (beginPoint.getY() * beginEnd.getY() > 0) {
                    return Math2D.squareOfDistance(begin, point) < Math2D.squareOfDistance(begin, end);
                } else if(beginPoint.getY() * beginEnd.getY() < 0)
                    return false;
                else {
                    if (beginPoint.getX() * beginEnd.getX() > 0)
                        return Math2D.squareOfDistance(begin, point) < Math2D.squareOfDistance(begin, end);
                    else return false;
                }
            }
        }

        return true;
    }

    private int bottomPointId() {
        double yMin = array[0].getY();
        int minId = 0;

        for (int i = 1; i < n; i++) {
            double y = array[i].getY();

            if ((y < yMin) || (yMin == y && array[i].getX() < array[minId].getX())) {
                yMin = array[i].getY();
                minId = i;
            }
        }
        return minId;
    }
}
