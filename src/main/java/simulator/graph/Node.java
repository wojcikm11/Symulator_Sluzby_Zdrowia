package simulator.graph;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Node {
    private int id;
    private Point2D coordinates;
    private List<NeighbourNode> neighbourNodes;

    public Node(int id, Point2D coordinates) {
        this.id = id;
        this.coordinates = coordinates;
        this.neighbourNodes = new ArrayList<>();
    }

    public void addNeighbourNodeAndDistance(Node nodeToAdd, double distance) {
        neighbourNodes.add(new NeighbourNode(nodeToAdd, distance));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point2D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point2D coordinates) {
        this.coordinates = coordinates;
    }

    public List<NeighbourNode> getNeighbourNodes() {
        return neighbourNodes;
    }

    public void setNeighbourNodes(List<NeighbourNode> neighbourNodes) {
        this.neighbourNodes = neighbourNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id ||
                Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinates);
    }
}
