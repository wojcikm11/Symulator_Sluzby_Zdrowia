package simulator.container;

import simulator.model.Hospital;
import simulator.model.MapObject;
import simulator.model.Road;

import java.util.List;

public class MapData {
    private List<Hospital> hospitals;
    private List<MapObject> mapObject;
    private List<Road> roads;

    public MapData(List<Hospital> hospitals, List<MapObject> mapObject, List<Road> roads) {
        this.hospitals = hospitals;
        this.mapObject = mapObject;
        this.roads = roads;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<MapObject> getMapObject() {
        return mapObject;
    }

    public List<Road> getRoads() {
        return roads;
    }
}
