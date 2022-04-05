package simulator.input;

public interface ReadHelper {
    <T> T ReadObject(String line);

    boolean CanBeEmpty();
}
