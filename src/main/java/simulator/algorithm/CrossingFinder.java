package simulator.algorithm;


import javafx.geometry.Point2D;
import simulator.container.NodeData;
import simulator.graph.Crossing;
import simulator.graph.Node;
import simulator.math2D.Math2D;
import simulator.model.Hospital;
import simulator.model.Road;
import simulator.math2D.MapIdGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CrossingFinder {
    private final MapIdGenerator mapIdGenerator;

    public CrossingFinder(List<Hospital> hospitals) {
        this.mapIdGenerator = new MapIdGenerator(hospitals);
    }

    public NodeData findCrossings(List<Road> roads, List<Hospital> hospitals) {
        List<Crossing> crossings = new ArrayList<>();
        List<List<Crossing>> roadsCrossings = new ArrayList<>();

        initializeRoadsCrossings(roadsCrossings, roads.size());
        findAndSetCrossings(roads, roadsCrossings, crossings);
        List<Road> finalRoads = getFinalRoads(roads, roadsCrossings);
        setNeighbourNodes(finalRoads);

        return new NodeData(finalRoads, hospitals, crossings);
    }

    private void initializeRoadsCrossings(List<List<Crossing>> roadsCrossings, int roadsNumber) {
        for (int i = 0; i < roadsNumber; i++)
            roadsCrossings.add(new ArrayList<>());
    }

    private void findAndSetCrossings(List<Road> roads, List<List<Crossing>> roadsCrossings, List<Crossing> crossings) {
        for (int i = 0; i < roads.size(); i++) {
            Road firstRoadToCheck = roads.get(i);
            for (int j = i + 1; j < roads.size(); j++) {
                Road secondRoadToCheck = roads.get(j);
                Point2D[] roadsCoordinates = new Point2D[]{firstRoadToCheck.getStartNode().getCoordinates(),
                                                           firstRoadToCheck.getEndNode().getCoordinates(),
                                                           secondRoadToCheck.getStartNode().getCoordinates(),
                                                           secondRoadToCheck.getEndNode().getCoordinates()};

                Point2D crossingCoordinates = getCrossingCoordinates(roadsCoordinates);
                if (crossingCoordinates != null) {
                    Crossing crossing = new Crossing(getIdToSetToCrossing(), crossingCoordinates);
                    crossings.add(crossing);
                    roadsCrossings.get(i).add(crossing);
                    roadsCrossings.get(j).add(crossing);
                }
            }
        }
    }

    private static Point2D getCrossingCoordinates(Point2D[] roadsCoordinates) {
        return Math2D.findIntersectionPoint(roadsCoordinates[0], roadsCoordinates[1], roadsCoordinates[2],
                                            roadsCoordinates[3]);
    }

    private List<Road> getFinalRoads(List<Road> roads, List<List<Crossing>> roadsCrossings) {
        List<Road> finalRoads = new ArrayList<>();
        for (int i = 0; i < roadsCrossings.size(); i++) {
            Road originalRoad = roads.get(i);
            List<Crossing> roadCrossings = roadsCrossings.get(i);
            Point2D startingPoint = originalRoad.getStartNode().getCoordinates();
            roadCrossings.sort(new CrossingComparator(startingPoint));
            Node previousNode = originalRoad.getStartNode();

            for (Crossing crossing : roadCrossings) {
                double roadDistance = calculateRoadToAddDistance(originalRoad, previousNode, crossing);
                Road roadToAdd = new Road(getIdToSetToRoad(), previousNode, crossing, roadDistance);
                finalRoads.add(roadToAdd);
                previousNode = crossing;
            }

            double lastRoadToAddDistance = calculateRoadToAddDistance(originalRoad, previousNode, originalRoad.getEndNode());
            Road lastRoadToAdd = new Road(getIdToSetToRoad(), previousNode, roads.get(i).getEndNode(), lastRoadToAddDistance);
            finalRoads.add(lastRoadToAdd);
        }
        return finalRoads;
    }

    private void setNeighbourNodes(List<Road> roadsWithCrossings) {
        for (Road road: roadsWithCrossings) {
            Node startNode = road.getStartNode();
            Node endNode = road.getEndNode();
            double distance = road.getDistance();
            startNode.addNeighbourNodeAndDistance(endNode, distance);
            endNode.addNeighbourNodeAndDistance(startNode, distance);
        }
    }

    private int getIdToSetToCrossing() {
        return mapIdGenerator.getIdToSetToCrossing();
    }

    private int getIdToSetToRoad() {
        return mapIdGenerator.getIdToSetToRoad();
    }

    private double calculateRoadToAddDistance(Road mainRoad, Node startNode, Node endNode) {
        double roadToAddEuclideanLength = calculatePointsEuclideanLength(startNode.getCoordinates(),
                                                                         endNode.getCoordinates());
        double mainRoadEuclideanLength = calculatePointsEuclideanLength(mainRoad.getStartNode().getCoordinates(),
                                                                        mainRoad.getEndNode().getCoordinates());

        double roadToAddFraction = roadToAddEuclideanLength / mainRoadEuclideanLength;
        double roadToAddDistance = roadToAddFraction * mainRoad.getDistance();

        return roadToAddDistance;
    }

    private double calculatePointsEuclideanLength(Point2D firstPoint, Point2D secondPoint) {
        return Math2D.calculateEuclideanLength(firstPoint, secondPoint);
    }

    private class CrossingComparator implements Comparator<Crossing> {

        private Point2D referencePoint;

        public CrossingComparator(Point2D point2D) {
            this.referencePoint = point2D;
        }

        @Override
        public int compare(Crossing o1, Crossing o2) {
            return Double.compare(calculatePointsEuclideanLength(referencePoint, o1.getCoordinates()),
                                  calculatePointsEuclideanLength(referencePoint, o2.getCoordinates()));
        }
    }
}