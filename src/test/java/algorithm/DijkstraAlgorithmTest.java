package algorithm;

import org.junit.Before;
import org.junit.Test;
import simulator.algorithm.CrossingFinder;
import simulator.algorithm.DijkstraAlgorithm;
import simulator.container.MapData;
import simulator.container.NodeData;
import simulator.input.DataReader;
import simulator.input.MapReader;
import simulator.model.Hospital;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DijkstraAlgorithmTest {
    private DijkstraAlgorithm dijkstraAlgorithm;
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new MapReader();
    }

    @Test
    public void test_should_returnNull_when_startingHospitalHasNoRoads() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/starting_hospital_no_roads.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 3;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        // when
        Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
        Hospital expectedNearestHospital = null;

        // then
        assertEquals(expectedNearestHospital, actualNearestHospital);
    }

    @Test
    public void test_should_returnOtherHospital_when_onlyTwoHospitalsConnectedWithOneRoad() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/two_hospitals_one_road.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 1;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        // when
        Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
        int expectedNearestHospitalId = 2;
        Hospital expectedNearestHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == expectedNearestHospitalId) {
                expectedNearestHospital = hospital;
                break;
            }
        }

        // then
        assertEquals(expectedNearestHospital, actualNearestHospital);
    }

    @Test
    public void test_should_returnNearestHospital_evenWhen_distanceToCrossingIsShorter() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/distance_to_crossing_shortest.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 3;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        // when
        Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
        int expectedNearestHospitalId = 4;
        Hospital expectedNearestHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == expectedNearestHospitalId) {
                expectedNearestHospital = hospital;
                break;
            }
        }

        // then
        assertEquals(expectedNearestHospital, actualNearestHospital);
    }

    @Test
    public void test_should_returnNearestHospital_even_moreRoadsShorterDistance() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/more_roads_shorter_distance.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 4;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        // when
        Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
        int expectedNearestHospitalId = 2;
        Hospital expectedNearestHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == expectedNearestHospitalId) {
                expectedNearestHospital = hospital;
                break;
            }
        }

        // then
        assertEquals(expectedNearestHospital, actualNearestHospital);
    }

    @Test
    public void test_should_returnSecondNearestHospital_when_nearestHospitalAlreadyVisited() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/nearest_hospital_visited.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 1;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        int visitedHospitalId = 3;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == visitedHospitalId) {
                hospital.setVisited(true);
                break;
            }
        }

        // when
        Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
        int expectedNearestHospitalId = 2;
        Hospital expectedNearestHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == expectedNearestHospitalId) {
                expectedNearestHospital = hospital;
                break;
            }
        }

        // then
        assertEquals(expectedNearestHospital, actualNearestHospital);
    }

    @Test
    public void test_should_returnAllNearestHospitals_and_nullAtEndWhenAllHospitalsVisited() throws IOException {
        // given
        String fileName = "test_files/DijkstraAlgorithm_test_files/dijkstra_test_file.txt";
        File file = new File(fileName);
        MapData mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        NodeData nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);

        List<Hospital> hospitals = nodeData.getHospitals();
        int startingHospitalId = 1;
        Hospital startingHospital = null;
        for (Hospital hospital : hospitals) {
            if (hospital.getId() == startingHospitalId) {
                startingHospital = hospital;
                break;
            }
        }

        // when
        int[] expectedNearestHospitalsIds = {5, 2, 4, 3};
        List<Hospital> expectedNearestHospitals = new ArrayList<>();
        for (int expectedNearestHospitalsId : expectedNearestHospitalsIds) {
            addHospitalsOfGivenId(hospitals, expectedNearestHospitals, expectedNearestHospitalsId);
        }
        expectedNearestHospitals.add(null);

        // then
        startingHospital.setVisited(true);
        for (Hospital expectedNearestHospital : expectedNearestHospitals) {
            Hospital actualNearestHospital = dijkstraAlgorithm.findNearestHospital(startingHospital);
            if (actualNearestHospital != null)
                actualNearestHospital.setVisited(true);
            assertEquals(expectedNearestHospital, actualNearestHospital);
            startingHospital = actualNearestHospital;
        }
    }

    private void addHospitalsOfGivenId(List<Hospital> hospitals, List<Hospital> expectedNearestHospitals,
                                       int expectedNearestHospitalsId) {
        for (Hospital hospital : hospitals)
            if (hospital.getId() == expectedNearestHospitalsId) {
                expectedNearestHospitals.add(hospital);
                break;
            }
    }
}
