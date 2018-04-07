package revision;

public class Stack<T> {

    private final T items[];
    private int pointer;

    public Stack(int capacity) {
        items = (T[]) new Object[capacity];
        pointer = -1;
    }

    public boolean isEmpty() {
        return (pointer == -1);
    }

    public boolean isFull() {
        return (pointer == getItems().length - 1);
    }

    public void push(T element) {
        if (isFull()) {
            throw new StackOverflowError("The stack is full");
        } else {
            items[++pointer] = element;
        }

    }

    public T pop() {
        if (!isEmpty()) {
            return getItems()[pointer--];
        } else {
            throw new ArrayStoreException("There is no Elements to pop");
        }
    }

    public T showTop() {
        if (!isEmpty()) {
            return getItems()[pointer];
        } else {
            throw new ArrayStoreException("There is no Elements to show");
        }
    }

    public int size() {
        return pointer + 1;
    }

    public T[] getItems() {
        return items;
    }
}
