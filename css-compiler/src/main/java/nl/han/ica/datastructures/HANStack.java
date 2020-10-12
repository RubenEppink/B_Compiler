package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    IHANLinkedList<T> linkedList = new HANLinkedList<T>();

    @Override
    public void push(T value) {
        linkedList.addFirst(value);
    }

    @Override
    public T pop() {
        T value = linkedList.getFirst();
        linkedList.removeFirst();
        return value;
    }

    @Override
    public T peek() {
      return linkedList.getFirst();
    }
}
