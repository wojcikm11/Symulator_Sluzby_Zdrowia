package simulator.view;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulator.container.MapData;
import simulator.model.Hospital;
import simulator.model.MapObject;
import simulator.model.Patient;
import simulator.model.Road;

import java.util.List;

public class MapView {

    private final float pointSize = 8;
    private final Color hospitalColor = Color.GREEN;
    private final Color mapObjectColor = Color.BLUE;
    private final Color patientColor = Color.RED;
    private final  Color roadDistanceColor = Color.ORANGERED;
    private final float roadWidth = 5;
    private final Color roadColor = Color.BLACK;
    private final float borderWidth = 3;
    private final Color borderColor = Color.GRAY;
    private final float outline = 70;
    private  double canvasWidth;
    private double canvasHeight;
    private double minX, maxX, minY, maxY;
    private final float idOffset = 8;

    public void initBorder(GraphicsContext gc, List<Point2D> border) {
        gc.setLineWidth(borderWidth);
        gc.setStroke(borderColor);

        int i = 0;
        Point2D begin;
        Point2D end = border.get(i);
        for(Point2D point : border) {
            if(i != 0) {
                begin = end;
                end = point;
                drawLine(gc, begin, end);
            }
            i++;
        }
        begin = end;
        end = border.get(0);
        drawLine(gc, begin, end);
    }
    public void drawHospitals(GraphicsContext gc, List<Hospital> hospitalList, boolean displayID) {
        gc.setFill(hospitalColor);

        for (Hospital h : hospitalList) {
            Point2D coordinates = h.getCoordinates();
            gc.fillOval(calculatePointX(coordinates.getX()) - pointSize / 2, calculatePointY(coordinates.getY()) - pointSize / 2, pointSize , pointSize);
            if(displayID) {
                gc.fillText(h.getId() + " - " + h.getFreeBeds() + " / " + h.getTotalBeds(), calculatePointX(coordinates.getX()) + idOffset, calculatePointY(coordinates.getY()) - idOffset);
            }
        }
    }

    public void drawMapObjects(GraphicsContext gc, List<MapObject> mapObjects, boolean displayID) {
        gc.setFill(mapObjectColor);

        for (MapObject o : mapObjects) {
            Point2D coordinates = o.getCoordinates();
            gc.fillOval(calculatePointX(coordinates.getX()) - pointSize / 2, calculatePointY(coordinates.getY()) - pointSize / 2, pointSize , pointSize);
            if(displayID) {
                gc.fillText(String.valueOf(o.getId()), calculatePointX(coordinates.getX()) + idOffset, calculatePointY(coordinates.getY()) - idOffset);
            }
        }
    }

    public void drawRoads(GraphicsContext gc, List<Road> roads, List<Road> finalRoads, boolean showDistances) {
        gc.setStroke(roadColor);
        gc.setLineWidth(roadWidth);

        for (Road r : roads) {
            Point2D start = r.getStartNode().getCoordinates();
            Point2D end = r.getEndNode().getCoordinates();

            drawLine(gc, start, end);
        }

        if (showDistances) {
            gc.setFill(roadDistanceColor);
            for (Road finalRoad : finalRoads) {
                double finalRoadDistance = Math.round(finalRoad.getDistance() * 100) / 100.0;
                if (finalRoadDistance > 0)
                    gc.fillText(String.valueOf(finalRoadDistance), calculatePointX(getMiddleXOfRoad(finalRoad)),
                            calculatePointY(getMiddleYOfRoad(finalRoad)));
            }
        }
    }

    private double getMiddleXOfRoad(Road road) {
        return (road.getStartNode().getCoordinates().getX() + road.getEndNode().getCoordinates().getX()) / 2;
    }

    private double getMiddleYOfRoad(Road road) {
        return (road.getStartNode().getCoordinates().getY() + road.getEndNode().getCoordinates().getY()) / 2;
    }

    private void getAllMinAndMax(GraphicsContext gc, MapData mapData) {
        canvasHeight = gc.getCanvas().getHeight();
        canvasWidth = gc.getCanvas().getWidth();
        Point2D coordinates = mapData.getHospitals().get(0).getCoordinates();
        minX = coordinates.getX();
        maxX = minX;
        minY = coordinates.getY();
        maxY = minY;
        for (Hospital h : mapData.getHospitals()) {
            coordinates = h.getCoordinates();
            setMinMax(coordinates);
        }
        for (MapObject o : mapData.getMapObject()) {
            coordinates = o.getCoordinates();
            setMinMax(coordinates);
        }
    }

    private void drawBorders(GraphicsContext gc) {
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.strokeRect(0, 0, canvasWidth, canvasHeight);
    }

    private void setMinMax(Point2D coordinates) {
        if(coordinates.getX() < minX) {
            minX = coordinates.getX();
        }
        else if(coordinates.getX() > maxX) {
            maxX = coordinates.getX();
        }
        if(coordinates.getY() < minY) {
            minY = coordinates.getY();
        }
        else if(coordinates.getY() > maxY) {
            maxY = coordinates.getY();
        }
    }

    private double calculatePointX(double x) {
        return outline + (x - minX) / (maxX-minX) * (canvasWidth -  2 * outline);
    }

    private double calculatePointY(double y) {
        return outline + (y - minY) / (maxY-minY) * (canvasHeight - 2 * outline);
    }

    public void drawMap(GraphicsContext gc, MapData mapData, List<Road> finalRoads, List<Point2D> border,
                        boolean displayID) {
        getAllMinAndMax(gc, mapData);
        initBorder(gc, border);
        drawBorders(gc);
        drawRoads(gc, mapData.getRoads(), finalRoads, displayID);
        drawHospitals(gc, mapData.getHospitals(), displayID);
        drawMapObjects(gc, mapData.getMapObject(), displayID);
    }

    public void drawPatients(GraphicsContext gc, List<Patient> patients, boolean displayID) {
        gc.setFill(patientColor);

        for(Patient patient : patients) {
            Point2D coordinates = patient.getCoordinates();
            gc.fillOval(calculatePointX(coordinates.getX()) - pointSize / 2, calculatePointY(coordinates.getY()) - pointSize / 2, pointSize , pointSize);
            if(displayID) {
                gc.fillText(String.valueOf(patient.getId()), calculatePointX(coordinates.getX()) + idOffset, calculatePointY(coordinates.getY()) - idOffset);
            }
        }
    }

    public void drawLine(GraphicsContext gc, Point2D p1, Point2D p2) {
        gc.strokeLine(calculatePointX(p1.getX()), calculatePointY(p1.getY()), calculatePointX(p2.getX()), calculatePointY(p2.getY()));
    }

    public Point2D translateCanvasPoint(Point2D coordinates) {
        double x = (coordinates.getX() - outline) * (maxX-minX) / (canvasWidth - 2 * outline) + minX;
        double y = (coordinates.getY() - outline) * (maxY-minY) / (canvasHeight - 2 * outline) + minY;
        return new Point2D(x,y);
    }
}
