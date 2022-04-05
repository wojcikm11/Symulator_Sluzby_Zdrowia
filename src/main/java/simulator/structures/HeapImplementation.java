package simulator.structures;

public interface HeapImplementation<T extends Comparable<T>> {

    public void put(T item);

    public T pop();

    public boolean isEmpty();
}
