package simulator.model;

import simulator.graph.Node;

import java.util.Objects;

public class Road {
    private int id;
    private Node startNode;
    private Node endNode;
    private double distance;

    public Road(int id, Node startNode, Node endNode, double distance) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return Objects.equals(startNode, road.startNode) &&
                Objects.equals(endNode, road.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode);
    }
}
