package simulator.input;

import simulator.exception.ExcessiveParametersException;
import simulator.exception.NoObjectsFoundInFileException;
import simulator.exception.MissingParametersException;
import simulator.exception.ObjectDuplicateDataException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataReader {

    protected static final int MAX_LINES = 100000;
    protected int line;
    protected String fileName;

    public DataReader() {
        line = 0;
    }

    public abstract <T> T importData(File file) throws IOException;

    protected <T> List<T> ReadMultipleObjects(BufferedReader reader, ReadHelper readHelper) throws IOException {
        List<T> objects = new ArrayList<>();
        T object;
        readBlankLines(reader);

        while (true) {
            String nextLine = readLine(reader);
            if (nextLine == null)
                break;
            if (nextLine.isBlank())
                break;

            object = readHelper.ReadObject(nextLine);

            if (objects.contains(object))
                throw new ObjectDuplicateDataException(fileNameErrorMessage() + "Zduplikowany identyfikator lub koordynaty " +
                        "obiektu w linii " + line);
            objects.add(object);
        }

        if (objects.size() == 0 && !readHelper.CanBeEmpty())
            throw new NoObjectsFoundInFileException(fileNameErrorMessage() + "Nie znaleziono obiektów w pliku");

        return objects;
    }

    protected String readLine(BufferedReader bufferedReader) throws IOException {
        line++;
        return bufferedReader.readLine();
    }

    protected void readBlankLines(BufferedReader reader) throws IOException {
        while (true) {
            String nextLine = readLine(reader);
            if (nextLine == null)
                break;
            else if (nextLine.isBlank())
                continue;
            else if (nextLine.stripLeading().startsWith("#"))
                break;
        }
    }

    protected String[] getAttributes(String currentLine, int attributesNumber) {
        String[] attributes;

        attributes = currentLine.split(" \\| ");
        if (attributes.length < attributesNumber || invalidParams(attributes, attributesNumber))
            throw new MissingParametersException(fileNameErrorMessage() + "W podanej linii: " + currentLine +
                    " pewne dane są puste lub nie istnieją. Błąd w linijce " + line);
        if (attributes.length > attributesNumber) {
            throw new ExcessiveParametersException(fileNameErrorMessage() + "W linii " + currentLine + " oczekiwano "
                    + attributesNumber + " atrybutów, a otrzymano " + attributes.length);
        }

        return attributes;
    }

    protected boolean invalidParams(String[] attributes, int attributesNumber) {
        for (int i = 0; i < attributesNumber; i++) {
            if (attributes[i] == null || attributes[i].isBlank())
                return true;
        }
        return false;
    }

    protected String fileNameErrorMessage() {
        return "Błąd w pliku " + fileName + ". ";
    }
}
