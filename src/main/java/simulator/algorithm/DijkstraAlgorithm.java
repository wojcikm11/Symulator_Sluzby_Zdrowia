package simulator.algorithm;

import simulator.container.NodeData;
import simulator.graph.Crossing;
import simulator.graph.NeighbourNode;
import simulator.graph.Node;
import simulator.model.Hospital;
import simulator.structures.HeapImplementation;
import simulator.structures.Heap;

import java.util.*;

public class DijkstraAlgorithm {
    private HeapImplementation<NeighbourNode> priorityQueue;
    private Map<Integer, Double> dijkstraDistances;
    private List<Node> hospitalsAndCrossings;
    private Map<Integer, Hospital> hospitalsMap;
    private List<Integer> crossingsIds;

    public DijkstraAlgorithm(NodeData nodeData) {
        this.priorityQueue = new Heap<>();
        this.hospitalsAndCrossings = nodeData.getHospitalsAndCrossings();
        initHospitalsMap(nodeData.getHospitals());
        initCrossingsIds(nodeData.getCrossings());
    }

    private void initHospitalsMap(List<Hospital> hospitals) {
        hospitalsMap = new HashMap<>();
        for (Hospital hospital : hospitals) {
            hospitalsMap.put(hospital.getId(), hospital);
        }
    }

    private void initCrossingsIds(List<Crossing> crossings) {
        crossingsIds = new ArrayList<>();
        for (Crossing crossing : crossings)
            crossingsIds.add(crossing.getId());
    }

    private void initDijkstraDistances() {
        dijkstraDistances = new HashMap<>();
        for (Node node : hospitalsAndCrossings) {
            dijkstraDistances.put(node.getId(), Double.MAX_VALUE);
        }
    }

    public Hospital findNearestHospital(Node startNode) {
        initDijkstraDistances();

        double startDistance = 0.0;
        dijkstraDistances.put(startNode.getId(), startDistance);
        NeighbourNode startNeighbourNode = new NeighbourNode(startNode);
        startNeighbourNode.setDistance(startDistance);
        priorityQueue.put(startNeighbourNode);

        while (!priorityQueue.isEmpty()) {

            NeighbourNode currentNode = priorityQueue.pop();
            if (currentNode.getDistance() != dijkstraDistances.get(currentNode.getNode().getId()))
                continue;

            for (NeighbourNode neighbourNode : currentNode.getNode().getNeighbourNodes()) {
                if (pathShorter(currentNode, neighbourNode)) {
                    double shorterDistance = dijkstraDistances.get(currentNode.getNode().getId()) + neighbourNode.getDistance();
                    dijkstraDistances.put(neighbourNode.getNode().getId(), shorterDistance);
                    neighbourNode.setDistance(shorterDistance);
                    priorityQueue.put(neighbourNode);
                }
            }
        }

        resetNodesDistances();
        removeRedundantDijkstraDistances(startNode);
        return getNearestHospital(startNode);
    }

    private boolean pathShorter(NeighbourNode currentNode, NeighbourNode neighbourNode) {
        return dijkstraDistances.get(currentNode.getNode().getId()) + neighbourNode.getDistance() <
                dijkstraDistances.get(neighbourNode.getNode().getId());
    }

    private void resetNodesDistances() {
        for (Node node : hospitalsAndCrossings) {
            for (NeighbourNode neighbourNode : node.getNeighbourNodes()) {
                double originalDistance = neighbourNode.getOriginalDistance();
                neighbourNode.setDistance(originalDistance);
            }
        }
    }

    private void removeRedundantDijkstraDistances(Node startNode) {
        dijkstraDistances.remove(startNode.getId());
        for (Integer crossingId : crossingsIds) {
            dijkstraDistances.remove(crossingId);
        }
    }

    private Hospital getNearestHospital(Node startNode) {
        if (startNode.getNeighbourNodes().isEmpty())
            return null;

        while(!dijkstraDistances.isEmpty()) {
            Map.Entry<Integer, Double> hospitalIdAndDistance = Collections.min(
                    dijkstraDistances.entrySet(), Comparator.comparing(Map.Entry::getValue)
            );

            if (hospitalIdAndDistance == null || hospitalIdAndDistance.getValue().equals(Double.MAX_VALUE))
                return null;

            Hospital nearestHospital = hospitalsMap.get(hospitalIdAndDistance.getKey());

            if (!nearestHospital.visited()) {
                return nearestHospital;
            } else {
                dijkstraDistances.remove(hospitalIdAndDistance.getKey());
            }
        }
        return null;
    }
}