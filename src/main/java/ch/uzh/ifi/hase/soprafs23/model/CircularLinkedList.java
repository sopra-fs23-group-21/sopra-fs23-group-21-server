package ch.uzh.ifi.hase.soprafs23.model;

public class CircularLinkedList<T> {
    private Node head;
    private Node tail;

    private int size;


    public void add(T value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        tail.setNext(head);

        size++;

    }

    public int size() {
        return size;
    }

    public T get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node current = head;
        int currentIndex = 0;

        while (currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }

        return current.getValue();
    }

    public Node start(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node current = head;
        int currentIndex = 0;

        while (currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }

        return current;
    }



    public class Node {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        CircularLinkedList<Integer> circularLinkedList = new CircularLinkedList();
        circularLinkedList.add(1);
        circularLinkedList.add(2);
        circularLinkedList.add(3);
        System.out.println(circularLinkedList.size());

        CircularLinkedList<Integer>.Node start  = circularLinkedList.start(2);
        for (int i = 0; i < 15; i++) {
            System.out.println("行" + start.getValue());
            start = start.next;
        }
    }
}
