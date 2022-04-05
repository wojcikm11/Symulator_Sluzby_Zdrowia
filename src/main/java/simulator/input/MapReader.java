package simulator.input;

import javafx.geometry.Point2D;
import simulator.container.MapData;
import simulator.exception.NodeWithIdNotFound;
import simulator.exception.ObjectDuplicateDataException;
import simulator.graph.Node;
import simulator.model.Hospital;
import simulator.model.MapObject;
import simulator.model.RawRoad;
import simulator.model.Road;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapReader extends DataReader {

    HospitalReader hospitalReader = new HospitalReader();
    MapObjectReader mapObjectReader = new MapObjectReader();
    RoadReader roadReader = new RoadReader();


    public MapReader() {
        line = 0;
    }

    @Override
    public MapData importData(File file) throws IOException {
        List<Hospital> hospitals = new ArrayList<>();
        List<MapObject> objects = new ArrayList<>();
        List<RawRoad> rawRoads = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        try (
                var fileReader = new FileReader(file);
                var reader = new BufferedReader(fileReader);
        ) {
            fileName = file.getName();
            hospitals = ReadMultipleObjects(reader, hospitalReader);
            objects = ReadMultipleObjects(reader, mapObjectReader);
            CheckCoordinatesDuplication(hospitals, objects);
            rawRoads = ReadMultipleObjects(reader, roadReader);
            roads = SetRoadsNodes(rawRoads, hospitals);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Nie znaleziono pliku o nazwie " + file);
        } catch (IOException e) {
            throw new IOException("Błąd odczytu danych z pliku " + file);
        }

        MapData mapData = new MapData(hospitals,objects,roads);

        return mapData;
    }

    private void CheckCoordinatesDuplication(List<Hospital> hospitals, List<MapObject> objects) {
        List<Point2D> coordinates = new ArrayList<>();
        for (Hospital h:hospitals) {
            if(coordinates.contains(h.getCoordinates())) {
                throw new ObjectDuplicateDataException(fileNameErrorMessage() + "Zduplikowane koordynaty w szpitalu o id " + h.getId());
            }
            else {
                coordinates.add(h.getCoordinates());
            }
        }
        for (MapObject o:objects) {
            if(coordinates.contains(o.getCoordinates())) {
                throw new ObjectDuplicateDataException(fileNameErrorMessage() + "Zduplikowane koordynaty w obiekcie o id " + o.getId());
            }
            else {
                coordinates.add(o.getCoordinates());
            }
        }
    }

    private List<Road> SetRoadsNodes(List<RawRoad> rawRoads, List<Hospital> hospitals) {
        List<Road> roads = new ArrayList<>();

        for (RawRoad rawRoad : rawRoads)
        {
            Node startNode = FindHospitalWithID(rawRoad.getStartHospitalID(), hospitals);
            Node endNode = FindHospitalWithID(rawRoad.getEndHospitalID(), hospitals);
            if(startNode == null || endNode == null) {
                throw new NodeWithIdNotFound("Nie znaleziono jednego z szpitali drogi o id " + rawRoad.getId());
            }
            Road road = new Road(rawRoad.getId(),startNode,endNode,rawRoad.getDistance());
            roads.add(road);
        }

        return roads;
    }

    private Hospital FindHospitalWithID(int id, List<Hospital> hospitals) {
        Hospital hospital = null;
        for(Hospital h : hospitals)
        {
                if(h.getId() == id)
                {
                    hospital = h;
                    break;
                }
        }
        return hospital;
    }

    public class HospitalReader implements ReadHelper {

        @Override
        public Hospital ReadObject(String nextLine) {
            int attributesNumber = 6;
            String[] attributes = getAttributes(nextLine, attributesNumber);

            int id, x, y, totalBeds, freeBeds;
            String name;

            try {
                id = Integer.parseInt(attributes[0].strip());
                name = attributes[1].strip();
                x = Integer.parseInt(attributes[2].strip());
                y = Integer.parseInt(attributes[3].strip());
                totalBeds = Integer.parseInt(attributes[4].strip());
                freeBeds = Integer.parseInt(attributes[5].strip());
            } catch (NumberFormatException e) {
                throw new NumberFormatException(fileNameErrorMessage() + "Błąd w danie liczbowej w linijce " + line);
            }
            if(name.contains("|")) {
                throw new IllegalArgumentException(fileNameErrorMessage() + "Nazwa szpitala o id " + id + " zawiera nieprawidłowy znak ('|')");
            }
            if(freeBeds < 0 || totalBeds < 0) {
                throw new IllegalArgumentException(fileNameErrorMessage() + "Liczba łóżek nie może być ujemna, linia " + line);
            }
            if(freeBeds > totalBeds) {
                throw new IllegalArgumentException(fileNameErrorMessage() + "Liczba wolnych łóżek nie może być większa od całkowitej liczby łóżek");
            }

            Point2D coordinates = new Point2D(x, y);
            return new Hospital(id,coordinates,name,totalBeds,freeBeds);
        }

        @Override
        public boolean CanBeEmpty() {
            return false;
        }
    }

    public class MapObjectReader implements ReadHelper {

        @Override
        public MapObject ReadObject(String nextLine) {
            int attributesNumber = 4;
            String[] attributes = getAttributes(nextLine, attributesNumber);

            int id, x, y;
            String name;

            try {
                id = Integer.parseInt(attributes[0].strip());
                name = attributes[1].strip();
                x = Integer.parseInt(attributes[2].strip());
                y = Integer.parseInt(attributes[3].strip());
            } catch (NumberFormatException e) {
                throw new NumberFormatException(fileNameErrorMessage() + "Błąd w danie liczbowej w linijce " + line);
            }

            if(name.contains("|")) {
                throw new IllegalArgumentException(fileNameErrorMessage() + "Nazwa szpitala o id " + id + " zawiera nieprawidłowy znak ('|')");
            }

            Point2D coordinates = new Point2D(x, y);
            return new MapObject(id,name,coordinates);
        }

        @Override
        public boolean CanBeEmpty() {
            return true;
        }
    }

    public class RoadReader implements ReadHelper {

        @Override
        public RawRoad ReadObject(String nextLine) {
            int attributesNumber = 4;
            String[] attributes = getAttributes(nextLine, attributesNumber);

            int id, hospitalID1, hospitalID2;
            double distance;

            try {
                id = Integer.parseInt(attributes[0].strip());
                hospitalID1 = Integer.parseInt(attributes[1].strip());;
                hospitalID2 = Integer.parseInt(attributes[2].strip());
                if(hospitalID1 == hospitalID2) {
                    throw new ObjectDuplicateDataException("Droga o id " + id + " ma te samo id szpitala początkowego i końcowego");
                }
                distance = Double.parseDouble(attributes[3].strip());
            } catch (NumberFormatException e) {
                throw new NumberFormatException(fileNameErrorMessage() + "Błąd w danie liczbowej w linijce " + line);
            }

            if(distance<=0) {
                throw new IllegalArgumentException(fileNameErrorMessage() + "Nieprawidłowa długość drogi, linia " + line);
            }

            return new RawRoad(id,hospitalID1,hospitalID2,distance);
        }

        @Override
        public boolean CanBeEmpty() {
            return false;
        }
    }
}