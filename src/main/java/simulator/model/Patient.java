package simulator.model;

import javafx.geometry.Point2D;

import java.util.Objects;

public class Patient {
    private int id;
    private Point2D coordinates;

    public Patient(int id, Point2D coordinates) {
        this.id = id;
        this.coordinates = coordinates;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id ||
                Objects.equals(coordinates, patient.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinates);
    }
}
