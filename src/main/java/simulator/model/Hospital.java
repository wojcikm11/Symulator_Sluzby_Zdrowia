package simulator.model;

import javafx.geometry.Point2D;
import simulator.graph.Node;

public class Hospital extends Node {
    private String name;
    private int totalBeds;
    private int freeBeds;
    private int patientsInQueue;
    private boolean visited;

    public Hospital(int id, Point2D coordinates, String name, int totalBeds, int freeBeds) {
        super(id, coordinates);
        this.name = name;
        this.totalBeds = totalBeds;
        this.freeBeds = freeBeds;
        this.patientsInQueue = 0;
        this.visited = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public int getFreeBeds() {
        return freeBeds;
    }

    public void setFreeBeds(int freeBeds) {
        this.freeBeds = freeBeds;
    }

    public int getPatientsInQueue() {
        return patientsInQueue;
    }

    public void setPatientsInQueue(int patientsInQueue) {
        this.patientsInQueue = patientsInQueue;
    }

    public boolean visited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
