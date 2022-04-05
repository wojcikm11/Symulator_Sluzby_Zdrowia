package algorithm;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import simulator.algorithm.CrossingFinder;
import simulator.container.MapData;
import simulator.container.NodeData;
import simulator.graph.Crossing;
import simulator.input.DataReader;
import simulator.input.MapReader;
import simulator.model.Road;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CrossingFinderTest {
    private CrossingFinder crossingFinder;
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new MapReader();
    }

    @Test
    public void test_shouldNot_createCrossing_when_twoRoadsDoNotCross() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/roads_do_not_cross.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        int actualCrossingsNumber = 0;
        for (Crossing crossing : actualCrossings)
            actualCrossingsNumber++;
        int expectedCrossingsNumber = 0;

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 2;

        // then
        assertEquals(expectedCrossingsNumber, actualCrossingsNumber);
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }

    @Test
    public void test_should_createCrossing_when_typicalCrossing() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/normal_crossing.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        List<Crossing> expectedCrossings = List.of(new Crossing(Integer.MAX_VALUE, new Point2D(0, 0)));

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 4;

        // then
        for (int i = 0; i < actualCrossings.size(); i++) {
            assertEquals(expectedCrossings.get(i), actualCrossings.get(i));
        }
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }

    @Test
    public void test_should_createCrossings_when_roadsSquareShape() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/roads_square.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        List<Crossing> expectedCrossings = List.of(new Crossing(Integer.MAX_VALUE, new Point2D(25, 25)));

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 8;

        // then
        for (int i = 0; i < actualCrossings.size(); i++) {
            assertEquals(expectedCrossings.get(i), actualCrossings.get(i));
        }
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }

    @Test
    public void test_should_createCrossings_when_twoCrossings() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/two_crossings.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        List<Crossing> expectedCrossings = List.of(new Crossing(Integer.MAX_VALUE, new Point2D(0, 6)),
                                                   new Crossing(Integer.MAX_VALUE - 1, new Point2D(0, -6)));

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 7;

        // then
        for (int i = 0; i < actualCrossings.size(); i++) {
            assertEquals(expectedCrossings.get(i), actualCrossings.get(i));
        }
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }

    @Test
    public void test_should_createCrossings_when_fourCrossings() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/four_crossings.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        List<Crossing> expectedCrossings = List.of(new Crossing(Integer.MAX_VALUE, new Point2D(0, 25)),
                                                    new Crossing(Integer.MAX_VALUE - 1, new Point2D(25, 25)),
                                                    new Crossing(Integer.MAX_VALUE - 2, new Point2D(0, -25)),
                                                    new Crossing(Integer.MAX_VALUE - 3, new Point2D(25, -25)));

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 12;

        // then
        for (int i = 0; i < actualCrossings.size(); i++) {
            assertEquals(expectedCrossings.get(i), actualCrossings.get(i));
        }
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }

    @Test
    public void test_should_createNumberOfRoadsAndCrossings_when_complexRoadLayout() throws IOException {
        // given
        String fileName = "test_files/CrossingFinder_test_files/complex_road_layout.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        crossingFinder = new CrossingFinder(mapData.getHospitals());

        // when
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());

        List<Crossing> actualCrossings = nodeData.getCrossings();
        int actualCrossingsNumber = 0;
        for (Crossing crossing : actualCrossings)
            actualCrossingsNumber++;
        int expectedCrossingsNumber = 10;

        List<Road> actualRoads = nodeData.getFinalRoads();
        int actualRoadsNumber = 0;
        for (Road road : actualRoads)
            actualRoadsNumber++;
        int expectedRoadsNumber = 26;

        // then
        assertEquals(expectedCrossingsNumber, actualCrossingsNumber);
        assertEquals(expectedRoadsNumber, actualRoadsNumber);
    }
}
