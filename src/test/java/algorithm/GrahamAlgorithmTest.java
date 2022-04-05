package algorithm;

import javafx.geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;
import simulator.algorithm.GrahamAlgorithm;
import simulator.container.MapData;
import simulator.input.MapReader;
import simulator.model.Hospital;
import simulator.model.MapObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrahamAlgorithmTest {

    @Test
    public void WhenGivenInsidePointsThenIsInsideReturnsTrue() {
        double[] xs = {0, 0, 1, -1, 1, -1};
        double[] ys = {0, 1, 1, -1, -1, 1};
        List<Point2D> points = new ArrayList<>();
        List<Point2D> result;
        for(int i=0; i < xs.length; i++)
            points.add(new Point2D(xs[i], ys[i]));
        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        result = graham.scan();
        for(Point2D e : result)
            System.out.println("(" + e.getX() + ", "+ e.getY()+")");

        Assert.assertTrue(graham.isInside(new Point2D(0,0)));
        Assert.assertTrue(graham.isInside(new Point2D(0,1)));
        Assert.assertTrue(graham.isInside(new Point2D(1,1)));
    }

    @Test
    public void WhenGivenOutsidePointsThenIsInsideReturnsFalse() {
        double[] xs = {0, 0, 1, -1, 1, -1};
        double[] ys = {0, 1, 1, -1, -1, 1};
        List<Point2D> points = new ArrayList<>();
        List<Point2D> result;
        for(int i=0; i < xs.length; i++)
            points.add(new Point2D(xs[i], ys[i]));
        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        result = graham.scan();
        for(Point2D e : result)
            System.out.println("(" + e.getX() + ", "+ e.getY()+")");

        Assert.assertFalse(graham.isInside(new Point2D(2,2)));
        Assert.assertFalse(graham.isInside(new Point2D(1,2)));
        Assert.assertFalse(graham.isInside(new Point2D(2,1)));
    }

    @Test
    public void WhenGivenDifferentPointsFromFileThenIsInsideReturnsCorrectBool() throws IOException {
        String fileName = "test_files/GrahamAlgorithm_test_files/grahamAlgorithm_test1.txt";
        File file = new File(fileName);
        MapReader dataReader = new MapReader();
        MapData mapData = dataReader.importData(file);

        List<Point2D> points = new ArrayList<>();

        for(Hospital h : mapData.getHospitals()) {
            points.add(h.getCoordinates());
        }

        for(MapObject o : mapData.getMapObject()) {
            points.add(o.getCoordinates());
        }

        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        graham.scan();

        Assert.assertFalse(graham.isInside(new Point2D(2,2)));
        Assert.assertFalse(graham.isInside(new Point2D(50,0)));
        Assert.assertFalse(graham.isInside(new Point2D(15,-10)));

        Assert.assertTrue(graham.isInside(new Point2D(50,60)));
        Assert.assertTrue(graham.isInside(new Point2D(90,40)));
        Assert.assertTrue(graham.isInside(new Point2D(20,81)));
    }

    @Test
    public void WhenGivenDifferentPointsFromFileThenScanReturnsCorrectListOfPoints() throws IOException {
        String fileName = "test_files/GrahamAlgorithm_test_files/grahamAlgorithm_test1.txt";
        File file = new File(fileName);
        MapReader dataReader = new MapReader();
        MapData mapData = dataReader.importData(file);

        List<Point2D> points = new ArrayList<>();
        List<Point2D> result;

        for(Hospital h : mapData.getHospitals()) {
            points.add(h.getCoordinates());
        }

        for(MapObject o : mapData.getMapObject()) {
            points.add(o.getCoordinates());
        }

        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        result = graham.scan();

        List<Point2D> expected = new ArrayList<>();
        expected.add(new Point2D(-1, 50));
        expected.add(new Point2D(10, 140));
        expected.add(new Point2D(120, 130));
        expected.add(new Point2D(140, 10));
        expected.add(new Point2D(10, 10));

        Assert.assertEquals(result, expected);
    }

    @Test
    public void WhenGivenDifferentPointsFromFileThenIsInsideReturnsCorrectBool2() throws IOException {
        String fileName = "test_files/GrahamAlgorithm_test_files/grahamAlgorithm_test2.txt";
        File file = new File(fileName);
        MapReader dataReader = new MapReader();
        MapData mapData = dataReader.importData(file);

        List<Point2D> points = new ArrayList<>();

        for(Hospital h : mapData.getHospitals()) {
            points.add(h.getCoordinates());
        }

        for(MapObject o : mapData.getMapObject()) {
            points.add(o.getCoordinates());
        }

        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        graham.scan();

        Assert.assertFalse(graham.isInside(new Point2D(-20,0)));
        Assert.assertFalse(graham.isInside(new Point2D(201,201)));
        Assert.assertFalse(graham.isInside(new Point2D(60,-10)));

        Assert.assertTrue(graham.isInside(new Point2D(5,50)));
        Assert.assertTrue(graham.isInside(new Point2D(110,55)));
        Assert.assertTrue(graham.isInside(new Point2D(40,70)));
    }

    @Test
    public void WhenGivenDifferentPointsFromFileThenScanReturnsCorrectListOfPoints2() throws IOException {
        String fileName = "test_files/GrahamAlgorithm_test_files/grahamAlgorithm_test2.txt";
        File file = new File(fileName);
        MapReader dataReader = new MapReader();
        MapData mapData = dataReader.importData(file);

        List<Point2D> points = new ArrayList<>();
        List<Point2D> result;

        for(Hospital h : mapData.getHospitals()) {
            points.add(h.getCoordinates());
        }

        for(MapObject o : mapData.getMapObject()) {
            points.add(o.getCoordinates());
        }

        GrahamAlgorithm graham = new GrahamAlgorithm(points);
        result = graham.scan();

        List<Point2D> expected = new ArrayList<>();
        expected.add(new Point2D(0, 200));
        expected.add(new Point2D(200, 200));
        expected.add(new Point2D(200, 0));
        expected.add(new Point2D(0, 0));

        Assert.assertEquals(expected, result);
    }
}
