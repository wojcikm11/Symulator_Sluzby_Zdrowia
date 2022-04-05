package simulator.input;

import javafx.geometry.Point2D;
import simulator.model.Patient;

import java.io.*;
import java.util.List;

public class PatientsReader extends DataReader {
    PatientHelper patientHelper = new PatientHelper();

    @Override
    public List<Patient> importData(File file) throws IOException {
        List<Patient> patients = null;
        try (
                var fileReader = new FileReader(file);
                var reader = new BufferedReader(fileReader);
        ) {
            fileName = file.getName();
            patients = ReadMultipleObjects(reader,patientHelper);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Nie znaleziono pliku o nazwie " + file);
        } catch (IOException e) {
            throw new IOException("Błąd odczytu danych z pliku " + file);
        }
        return patients;
    }

    public class PatientHelper implements ReadHelper {

        @Override
        public Patient ReadObject(String nextLine) {
            int attributesNumber = 3;
            String[] attributes = getAttributes(nextLine, attributesNumber);

            int id;
            int x;
            int y;

            try {
                id = Integer.parseInt(attributes[0].strip());
                x = Integer.parseInt(attributes[1].strip());
                y = Integer.parseInt(attributes[2].strip());
            } catch (NumberFormatException e) {
                throw new NumberFormatException(fileNameErrorMessage() + "Błąd w danie liczbowej w linijce " + line);
            }

            Point2D coordinates = new Point2D(x, y);
            return new Patient(id, coordinates);
        }

        @Override
        public boolean CanBeEmpty() {
            return false;
        }
    }
}
