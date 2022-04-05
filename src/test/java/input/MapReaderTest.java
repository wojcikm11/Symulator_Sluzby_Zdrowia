package input;

import org.junit.Before;
import org.junit.Test;
import simulator.exception.*;
import simulator.input.DataReader;
import simulator.input.MapReader;
import simulator.input.PatientsReader;

import java.io.File;
import java.io.IOException;

public class MapReaderTest {
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new MapReader();
    }

    @Test
    public void test_should_loadFile_when_fileCorrect() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/correct_file.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert true;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_noObjectsFoundInFileException_when_fileEmpty() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/empty_file.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test
    public void test_should_loadFile_when_noObjectsInFile() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/no_objects.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert true;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_noObjectsFoundInFileException_when_noHospitalsInFile() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/no_hospitals.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NoObjectsFoundInFileException.class)
    public void test_should_throw_noObjectsFoundInFileException_when_noRoadsInFile() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/no_roads.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_roadToSameHospital() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/road_to_same_hospital.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_hospitalIdRepeats() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_id_repeats.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_hospitalCoordinatesRepeat() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_coordinates_repeat.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_objectIdRepeats() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/object_id_repeats.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_objectCoordinatesRepeat() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/object_coordinates_repeat.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_objectAndHospitalSameCoordinates() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_object_same_coordinates.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throw_objectDuplicateDataException_when_roadIdRepeats() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/road_id_repeats.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NodeWithIdNotFound.class)
    public void test_should_throw_nodeWithIdNotFound_when_roadHospitalsDoNotExist() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/road_hospital_do_not_exist.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_roadDistanceLessThanZero() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/road_distance_less_than_zero.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_roadDistanceEqualsZero() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/road_distance_equals_zero.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_objectNameContainsDivisionSign() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/object_name_division_sign.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_hospitalNameContainsDivisionSign() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_name_division_sign.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_hospitalBedsLessThanZero() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_beds_less_than_zero.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_hospitalFreeBedsMoreThanTotalBeds() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_free_beds_more_than_total_beds.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_illegalArgumentException_when_hospitalFreeBedsLessThanZero() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/hospital_free_beds_less_than_zero.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_coordinateContainsLetter() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/coordinate_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_idContainsLetter() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/id_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_distanceContainsLetter() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/distance_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_hospitalBedsContainsLetter() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/beds_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_hospitalFreeBedsContainsLetter() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/free_beds_contains_letter.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_idTypeDouble() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/id_type_double.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void test_should_throw_numberFormatException_when_coordinateTypeDouble() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/coordinate_type_double.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ExcessiveParametersException.class)
    public void test_should_loadFile_when_excessiveParameters() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/excessive_parameters.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throwObjectDuplicateDataException_when_roadsHospitalsIdsRepeat() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/roads_hospitals_repeat.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }

    @Test(expected = ObjectDuplicateDataException.class)
    public void test_should_throwObjectDuplicateDataException_when_roadsHospitalsIdsRepeatOrderReversed() throws IOException {
        // given
        String fileName = "test_files/MapReader_test_files/roads_hospitals_repeat_order_reversed.txt";
        File file = new File(fileName);

        // when
        dataReader.importData(file);

        // then
        assert false;
    }
}
