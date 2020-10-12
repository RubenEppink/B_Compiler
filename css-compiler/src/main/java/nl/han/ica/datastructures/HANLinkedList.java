package nl.han.ica.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class HANLinkedList<T> implements IHANLinkedList<T>, Iterable<T>{
    private Node<T> head = null;
    private int size = 0;

    @Override
    public void addFirst(T value) {
        Node<T> node = new Node<T>(value);
        if (head != null) {
            node.setNext(head);
        }
        head = node;
        size++;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        if (!isNodeIndex(index)) {
            throw new IndexOutOfBoundsException(String.format("Size: %d index: %d", size, index));
        }

        Node<T> beforePos = getNode(index - 1);

        Node<T> node = new Node<T>(value);
        node.setNext(beforePos.getNext());
        beforePos.setNext(node);
        size++;
    }

    @Override
    public void delete(int pos) {
        if (pos == 0) {
            removeFirst();
            return;
        }

        if (!isNodeIndex(pos)) {
            throw new IndexOutOfBoundsException(String.format("Size: %d index: %d", size, pos));
        }

        Node<T> beforePos = getNode(pos - 1);
        Node<T> node = beforePos.getNext();
        Node<T> afterPos = node.getNext();

        beforePos.setNext(afterPos);
        size--;


    }

    @Override
    public T get(int pos) {
        if (!isNodeIndex(pos)) {
            throw new IndexOutOfBoundsException(String.format("Size: %d index: %d", size, pos));
        }
        return getNode(pos).getValue();
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        head = head.getNext();
        size--;
    }

    @Override
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        return head.getValue();
    }


    @Override
    public int getSize() {
        return size;
    }

    private Node<T> getNode(int index) {

        if (index == 0) return head;

        Node<T> node = head;

        int counter = 0;

        while (counter++ < index) {
            node = node.getNext();
        }
        return node;
    }

    private boolean isNodeIndex(int index) {
        return index >= 0 && index < size;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }
}
