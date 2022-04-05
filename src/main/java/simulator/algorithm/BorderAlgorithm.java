package simulator.algorithm;

import javafx.geometry.Point2D;

import java.util.List;

public interface BorderAlgorithm {
    List<Point2D> scan();
    boolean isInside(Point2D point);
}
