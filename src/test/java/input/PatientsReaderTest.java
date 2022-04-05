package input;

import org.junit.Before;
import org.junit.Test;
import simulator.exception.*;
import simulator.input.DataReader;
import simulator.input.PatientsReader;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class PatientsReaderTest {
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new PatientsReader();
    }

    @Test
    public void test_should_loadFile_when_fileCorrect() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/correct_file.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert true;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_noObjectsFoundInFileException_when_fileEmpty() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/empty_file.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_noObjectsFoundInFileException_when_noPatientsInFile() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/no_patients.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test
    public void test_should_loadFile_when_onePatientInFile() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/one_patient.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert true;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_coordinateContainsLetters() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/coordinate_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_idContainsLetters() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/id_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = MissingParametersException.class)
    public void test_should_throw_missingParametersException_when_idMissing() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/id_missing.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = MissingParametersException.class)
    public void test_should_throw_missingParametersException_when_coordinateMissing() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/coordinate_missing.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_doubleParameter() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/double_parameter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ExcessiveParametersException.class)
    public void test_should_throw_ExcessiveParametersException_when_excessiveParameters() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/excessive_parameters.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_divisionSignInParameter() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/division_sign_in_parameter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_patientIdRepeats() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/id_repeats.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_patientCoordinatesRepeat() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/coordinates_repeat.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_missingParametersException_when_blankLineAtBeginning() throws IOException {
        // given
        String fileName = "test_files/PatientsReader_test_files/blank_line.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }
}
