package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        Node<T> newNode = new Node(value);
        if ((index == 0 && size == 0) || index == size) {
            add(value);
        } else if (index == 0 && size > 0) {
            newNode.next = first;
            newNode.prev = null;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> oldNode = first;
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            addItem(oldNode, newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return (T) node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findNode = findByIndex(index);
        T oldNode = findNode.item;
        findNode.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T node = deliteNode(findByIndex(index));
        return node;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        Node<T> node = first;
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (object == node.item) {
                    deliteNode(node);
                    return true;
                }
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(node.item)) {
                    deliteNode(node);
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.item = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private T deliteNode(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (node.prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private Node<T> findByIndex(int index) {
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
    }

    private void addItem(Node<T> oldNode, Node<T> newNode) {
        Node<T> oldNodePrevios = oldNode.prev;
        newNode.next = oldNode;
        newNode.prev = oldNodePrevios;
        oldNodePrevios.next = newNode;
        oldNode.prev = newNode;
    }
}
