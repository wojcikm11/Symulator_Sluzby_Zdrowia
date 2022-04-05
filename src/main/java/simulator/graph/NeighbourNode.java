package simulator.graph;

public class NeighbourNode implements Comparable<NeighbourNode> {
    private Node node;
    private double distance;
    private double originalDistance;

    public NeighbourNode(Node node, double distance) {
        this.node = node;
        this.distance = distance;
        this.originalDistance = distance;
    }

    public NeighbourNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getOriginalDistance() {
        return originalDistance;
    }

    public void setOriginalDistance(double originalDistance) {
        this.originalDistance = originalDistance;
    }

    @Override
    public int compareTo(NeighbourNode n) {
        return Double.compare(distance, n.getDistance());
    }
}
