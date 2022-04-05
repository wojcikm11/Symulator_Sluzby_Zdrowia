package simulator.structures;

public class DynamicTableStack<T> implements StackImplementation<T> {
    private T[] array;
    private int n;

    public DynamicTableStack() {
        this.n = 0;
        this.array = (T[]) new Object[2];
    }

    @Override
    public void push(T element) {

        if(n + 1 > array.length) {
            T[] newArray = (T[]) new Object[2 * n];
            copyArrayContent(newArray, array);
            array = newArray;
        }
        array[n] = element;
        n++;
    }

    @Override
    public T pop() {
        if(n < 1)
            throw new NullPointerException("Cannot pop element from empty stack!");
        T popped = array[--n];
        return popped;
    }

    @Override
    public T lookUpTop() {
        if(n < 1)
            throw new NullPointerException("Cannot look up the top of an empty stack!");
        return array[n-1];
    }

    @Override
    public T lookUpUnderTheTop() {
        if(n < 2)
            throw new NullPointerException("Cannot look up elements of an empty stack!");
        return array[n-2];
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    private void copyArrayContent(T[] destination, T[] source) {
        if(source.length > destination.length)
            throw new IllegalArgumentException("Source array cannot be larger than destination array");

        for(int i = 0; i < n; i++)
            destination[i] = source[i];
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
