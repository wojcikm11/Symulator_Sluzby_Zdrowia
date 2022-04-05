package simulator.container;

import simulator.graph.Crossing;
import simulator.model.Hospital;
import simulator.graph.Node;
import simulator.model.Road;

import java.util.ArrayList;
import java.util.List;

public class NodeData {
    private List<Road> finalRoads;
    private List<Hospital> hospitals;
    private List<Crossing> crossings;
    private List<Node> hospitalsAndCrossings;

    public NodeData(List<Road> finalRoads, List<Hospital> hospitals, List<Crossing> crossings) {
        this.finalRoads = finalRoads;
        this.hospitals = hospitals;
        this.crossings = crossings;
        this.hospitalsAndCrossings = getHospitalsAndCrossings(crossings, hospitals);
    }

    private List<Node> getHospitalsAndCrossings(List<Crossing> crossings, List<Hospital> hospitals) {
        List<Node> hospitalsAndCrossings = new ArrayList<>();
        hospitalsAndCrossings.addAll(hospitals);
        hospitalsAndCrossings.addAll(crossings);
        return hospitalsAndCrossings;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<Crossing> getCrossings() {
        return crossings;
    }

    public List<Node> getHospitalsAndCrossings() {
        return hospitalsAndCrossings;
    }

    public List<Road> getFinalRoads() {
        return finalRoads;
    }
}
