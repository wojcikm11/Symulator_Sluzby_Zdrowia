package simulator.structures;

import java.util.ArrayList;
import java.util.List;

public class Heap<T extends Comparable<T>> implements HeapImplementation<T> {

    private List<T> items;

    public Heap() {
        this.items = new ArrayList<>();
    }

    @Override
    public void put(T item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot put null value!");

        items.add(item);
        int lastId = items.size() - 1;
        heapUp(lastId);
    }

    private void heapUp(int lastId) {
        int childId = lastId;
        int parentId = (childId - 1) / 2;

        while (parentId >= 0) {
            if (isChildBiggerThanParent(parentId, childId)) {
                swapItems(parentId, childId);
                childId = parentId;
                parentId = (childId - 1) / 2;
            } else
                break;
        }
    }

    @Override
    public T pop() {
        int n = items.size();

        if (n == 0)
            return null;

        int rootItemId = 0;
        int lastItemId = n - 1;

        swapItems(rootItemId, lastItemId);
        T resultValue = items.remove(n - 1);
        heapDown();

        return resultValue;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    private void heapDown() {
        int n = items.size();
        int parentId = 0;
        int childId = 2 * parentId + 1;

        while (childId < n) {
            if (isRightChildBigger(childId))
                childId++;

            if (isChildBiggerThanParent(parentId, childId)) {
                swapItems(parentId, childId);
                parentId = childId;
                childId = 2 * parentId + 1;
            } else
                break;
        }
    }

    private boolean isRightChildBigger(int leftChildId) {
        int n = items.size();
        int rightChildId = leftChildId + 1;
        boolean rightChildIsBigger = false;

        if (rightChildId < n) {
            T leftChild = items.get(leftChildId);
            T rightChild = items.get(rightChildId);

            rightChildIsBigger = rightChild.compareTo(leftChild) > 0;
        }
        return rightChildIsBigger;
    }

    private void swapItems(int firstId, int secondId) {
        T firstVal = items.get(firstId);
        T secondVal = items.get(secondId);
        items.set(firstId, secondVal);
        items.set(secondId, firstVal);
    }

    private boolean isChildBiggerThanParent(int parentId, int childId) {
        T parentValue = items.get(parentId);
        T childValue = items.get(childId);

        return childValue.compareTo(parentValue) > 0;
    }
}