package simulator.structures;

public interface StackImplementation <T> extends Cloneable {
    void push(T element);
    T pop();
    T lookUpTop();
    T lookUpUnderTheTop();
    boolean isEmpty();
}
