package simulator.model;

import javafx.geometry.Point2D;

import java.util.Objects;

public class MapObject {
    private int id;
    private String name;
    private Point2D coordinates;

    public MapObject(int id, String name, Point2D coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        MapObject mapObject = (MapObject) o;
        return id == mapObject.id ||
                Objects.equals(coordinates, mapObject.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinates);
    }
}
