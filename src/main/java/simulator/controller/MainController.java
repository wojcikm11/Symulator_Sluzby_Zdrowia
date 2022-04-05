package simulator.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulator.algorithm.BorderAlgorithm;
import simulator.algorithm.CrossingFinder;
import simulator.algorithm.DijkstraAlgorithm;
import simulator.algorithm.GrahamAlgorithm;
import simulator.input.DataReader;
import simulator.input.MapReader;
import simulator.input.PatientsReader;
import simulator.container.MapData;
import simulator.container.NodeData;
import simulator.math2D.Math2D;
import simulator.math2D.PatientIdGenerator;
import simulator.model.Hospital;
import simulator.model.MapObject;
import simulator.model.Patient;
import simulator.view.MapView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainController {

    @FXML
    private Button loadMapButton;

    @FXML
    private Button loadPatientsButton;

    @FXML
    private ToggleButton addingPatientsWithMouseToggleButton;

    @FXML
    private ToggleButton startSimulationToggleButton;

    @FXML
    private CheckBox showIdCheckBox;

    @FXML
    private Slider simulationSpeedSlider;

    @FXML
    private TextArea infoTextArea;

    @FXML
    private Canvas mapCanvas;

    private DataReader dataReader;
    private List<Patient> patients;
    private static Number simulationSpeed;
    private DijkstraAlgorithm dijkstraAlgorithm;
    private BorderAlgorithm borderAlgorithm;
    private MapData mapData;
    private NodeData nodeData;
    private boolean isRunning = false;

    private MapView mapView = null;
    private GraphicsContext graphicsContext;
    Thread sim = null;

    public void initialize() {
        configureLoadButtons();
        configureStartSimulationToggleButton();
        configureSimulationSpeedSlider();
        configureCanvas();
        configureIDCheckBox();
    }

    private void configureIDCheckBox() {
        showIdCheckBox.setOnAction(actionEvent -> {
            if (mapData != null)
                redrawMap();
            if (patients != null)
                drawPatients();
        });
    }

    private void redrawMap() {
        clearMap();
        drawMap();
    }

    private void clearMap() {
        graphicsContext.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
    }

    private void configureCanvas() {
        mapView = new MapView();
        graphicsContext = mapCanvas.getGraphicsContext2D();

        mapCanvas.setOnMouseClicked(event -> {
            if (addingPatientsWithMouseToggleButton.isSelected()) {
                if (mapData == null) {
                    infoTextArea.appendText("Aby włączyć dodawanie pacjentów najpierw wczytaj mapę\n");
                    addingPatientsWithMouseToggleButton.setSelected(false);
                } else {
                    Point2D coordinates = new Point2D(event.getX(), event.getY());
                    addPatientWithMouse(mapView.translateCanvasPoint(coordinates));
                }
            }
        });
    }

    private void addPatientWithMouse(Point2D coordinates) {
        if (borderAlgorithm.isInside(coordinates) && !isRunning) {
            Patient patient = new Patient(PatientIdGenerator.generatePatientId(patients), coordinates);
            if (patients == null)
                patients = new ArrayList<>();
            patients.add(patient);

            redrawMap();
            drawPatients();
        }
    }

    private void configureSimulationSpeedSlider() {
        simulationSpeed = simulationSpeedSlider.getValue();
        simulationSpeedSlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                simulationSpeed = newValue;
            }
        });
    }

    private void configureStartSimulationToggleButton() {
        startSimulationToggleButton.setOnAction(actionEvent -> {
            if (startSimulationToggleButton.isSelected()) {
                startSimulationToggleButton.setText("Stop");
                if (patients != null) {
                    if (!patients.isEmpty()) {
                        isRunning = true;
                        startSimulation();
                    }
                }
            } else {
                startSimulationToggleButton.setText("Start");
                if(sim != null) {
                    sim.interrupt();
                    sim = null;
                    isRunning = false;
                }

            }
        });
    }

    private void configureLoadButtons() {
        loadMapButton.setOnAction(actionEvent -> {
            File mapFile = getFileFromUser();
            if (mapFile != null) {
                clearMap();
                loadMap(mapFile);
                clearPatients();
                drawMap();
            }
        });

        loadPatientsButton.setOnAction(actionEvent -> {
            if (mapData != null) {
                File patientsFile = getFileFromUser();
                if (patientsFile != null)
                    loadPatients(patientsFile);
            } else
                infoTextArea.appendText(cannotLoadPatientsErrorMassage());
        });
    }

    private void clearPatients() {
        patients = new ArrayList<>();
    }

    private File getFileFromUser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Txt", "*.txt"));
        return fc.showOpenDialog(new Stage());
    }

    private void loadMap(File mapFile) {
        try {
            setMapDataAndNodeData(mapFile);
            dijkstraAlgorithm = new DijkstraAlgorithm(nodeData);
            borderAlgorithm = new GrahamAlgorithm(getMapPoints());
        } catch (Exception e) {
            infoTextArea.appendText(e.getMessage() + "\n");
        }
    }

    private void setMapDataAndNodeData(File file) throws IOException {
        dataReader = new MapReader();
        mapData = dataReader.importData(file);
        CrossingFinder crossingFinder = new CrossingFinder(mapData.getHospitals());
        nodeData = crossingFinder.findCrossings(mapData.getRoads(), mapData.getHospitals());
        infoTextArea.appendText("Pomyślnie wczytano plik z danymi mapy " + file.getName() + "\n");
    }

    private void loadPatients(File patientsFile) {
        try {

            dataReader = new PatientsReader();
            patients = dataReader.importData(patientsFile);
            deletePatientsOutsideBorders();
            infoTextArea.appendText("Pomyślnie wczytano plik z pacjentami " + patientsFile.getName() + "\n");
            redrawMap();
            drawPatients();
        } catch (Exception e) {
            infoTextArea.appendText(e.getMessage() + "\n");
        }
    }

    private List<Point2D> getMapPoints() {
        List<Point2D> points = new ArrayList<>();
        for (Hospital hospital : mapData.getHospitals())
            points.add(hospital.getCoordinates());
        for (MapObject mapObject : mapData.getMapObject())
            points.add(mapObject.getCoordinates());
        return points;
    }

    public void drawMap() {
        mapView.drawMap(graphicsContext, mapData, nodeData.getFinalRoads(), borderAlgorithm.scan(),
                showIdCheckBox.isSelected());
    }

    public void drawPatients() {
        mapView.drawPatients(graphicsContext, patients, showIdCheckBox.isSelected());
    }

    private void deletePatientsOutsideBorders() {
        Iterator<Patient> patientsIterator = patients.iterator();
        while (patientsIterator.hasNext()) {
            Patient patient = patientsIterator.next();
            if (!borderAlgorithm.isInside(patient.getCoordinates())) {
                patientOutsideBordersInfo(patient);
                patientsIterator.remove();
            }
        }
    }

    public void patientOutsideBordersInfo(Patient patient) {
        infoTextArea.appendText("Pacjent o id = " + patient.getId()
                + " został usunięty, ponieważ znajduje się poza granicami państwa\n");
    }

    public static Number getSimulationSpeed() {
        return simulationSpeed;
    }

    private String cannotLoadPatientsErrorMassage() {
        return "Aby wczytać plik z pacjentami, najpierw dodaj mapę\n";
    }

    public void startSimulation() {
        Thread thread = new Thread(this::simulate);
        sim = thread;
        thread.setDaemon(true);
        thread.start();
    }

    public void simulate() {
        Iterator<Patient> iterator = patients.iterator();
        blankLine();
        Patient patient;
        while (iterator.hasNext()) {

            patient = iterator.next();
            resetVisitedStatus(mapData.getHospitals());
            Hospital firstHospital = nearestHospitalForPatient(mapData.getHospitals(), patient);

            if (firstHospital.getFreeBeds() > 0) {
                firstHospital.setFreeBeds(firstHospital.getFreeBeds() - 1);
                patientHandledInfo(infoTextArea, patient, firstHospital);

            } else {
                patientRejectedInfo(infoTextArea, patient, firstHospital);
                firstHospital.setVisited(true);
                Hospital startHospital = firstHospital;
                Hospital nearestHospital = dijkstraAlgorithm.findNearestHospital(startHospital);

                while (nearestHospital != null) {
                    if (nearestHospital.getFreeBeds() > 0)
                        break;
                    patientRejectedInfo(infoTextArea, patient, nearestHospital);
                    nearestHospital.setVisited(true);
                    startHospital = nearestHospital;
                    nearestHospital = dijkstraAlgorithm.findNearestHospital(startHospital);
                }

                if (nearestHospital == null) {
                    startHospital.setPatientsInQueue(startHospital.getPatientsInQueue() + 1);
                    patientInQueueInfo(infoTextArea, patient, startHospital);
                } else {
                    nearestHospital.setFreeBeds(nearestHospital.getFreeBeds() - 1);
                    patientHandledInfo(infoTextArea, patient, nearestHospital);
                }

            }
            iterator.remove();
            try {
                Thread.sleep((100 - MainController.getSimulationSpeed().longValue()) * 20);
            } catch (InterruptedException e) {
                break;
            }
            Platform.runLater(this::redrawMap);
            Platform.runLater(this::drawPatients);
        }
        isRunning = false;
    }

    private void blankLine() {
        infoTextArea.appendText("\n");
    }

    private Hospital nearestHospitalForPatient(List<Hospital> hospitals, Patient patient) {
        double minDistance = Math2D.squareOfDistance(patient.getCoordinates(), hospitals.get(0).getCoordinates());
        Hospital minHospital = hospitals.get(0);

        for (Hospital hospital : hospitals) {
            double distance = Math2D.squareOfDistance(patient.getCoordinates(), hospital.getCoordinates());
            if (distance < minDistance) {
                minDistance = distance;
                minHospital = hospital;
            }
        }

        return minHospital;
    }

    private void resetVisitedStatus(List<Hospital> hospitals) {
        for(Hospital h : hospitals)
            h.setVisited(false);
    }

    private void patientRejectedInfo(TextArea infoTextArea, Patient patient, Hospital defaultHospital) {
        String message = "Pacjent o id " + patient.getId() + " został przewieziony do szpitala o id " +
                defaultHospital.getId() + " i nie został przyjęty";
        Platform.runLater(() -> {
            infoTextArea.appendText(message + "\n");
        });
    }

    private void patientHandledInfo(TextArea infoTextArea, Patient patient, Hospital hospital) {
        String message = "Pacjent o id " + patient.getId() + " został obsłużony przez szpital "
                + hospital.getName() + " o id " + hospital.getId() + ". Pozostałe wolne łóżka: " + hospital.getFreeBeds();
        Platform.runLater(() -> {
            infoTextArea.appendText(message + "\n");
        });;
    }

    private void patientInQueueInfo(TextArea infoTextArea, Patient patient, Hospital hospital) {
        String message = "Brak wolnych miejsc w szpitalach możliwych do odwiedzenia - pacjent o id " +
                patient.getId() + " oczekuje w kolejce do szpitala o id " + hospital.getId() + ". Miejsce w kolejce: "
                + hospital.getPatientsInQueue();
        Platform.runLater(() -> {
            infoTextArea.appendText(message + "\n");
        });
    }

}

