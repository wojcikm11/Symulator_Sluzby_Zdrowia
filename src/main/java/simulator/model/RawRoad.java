package simulator.model;

import java.util.Objects;

public class RawRoad {
    private int id;
    private int hospital1ID;
    private int hospital2ID;
    private double distance;

    public RawRoad(int id, int hospital1ID, int hospital2ID, double distance) {
        this.id = id;
        this.hospital1ID = hospital1ID;
        this.hospital2ID = hospital2ID;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartHospitalID() {
        return hospital1ID;
    }

    public int getEndHospitalID() {
        return hospital2ID;
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
        RawRoad rawRoad = (RawRoad) o;
        return id == rawRoad.id ||
                hospital1ID == rawRoad.hospital1ID &&
                hospital2ID == rawRoad.hospital2ID ||
                hospital1ID == rawRoad.hospital2ID &&
                hospital2ID == rawRoad.hospital1ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hospital1ID, hospital2ID);
    }
}