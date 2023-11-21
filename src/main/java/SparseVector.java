import java.util.Optional;

public class SparseVector implements ISparseVector {

    private class LinkedList {
        private static class Node {
            private int index;
            private double value;
            private Node next;

            public Node(double value, int index) {
                this.value = value;
                this.index = index;
                this.next = null;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }
        }
        private Node head;
        private int length;

        public LinkedList() {
            this.length = 0;
            this.head = null;
        }

        //TODO: Unit Testing
        public void add(int index, double value) {
            Node newNode = new Node(value, index);
            if (this.head == null) {
                this.head = newNode;
            } else {
                Node current = head;
                while(current.next != null && index < current.next.getIndex()) {
                    current = current.next;
                }
                if(current.next.getIndex() == index) {
                    current.next.setValue(value);
                } else {
                    newNode.next = current.next;
                    current.next = newNode;
                }
            }
            this.length += 1;
        }

        public void remove(int index) {
            if(head == null) {
                return;
            }
            Node current = this.head;
            Node prev = null;
            while(current.getIndex() != index && current.next != null) {
                prev = current;
                current = current.next;
            }
            if(current.getIndex() == index) {
                prev.next = current.next;
            }
        }

        public double get(int index) {
            Node current = this.head;
            while(current.getIndex() != index && current.next != null) {
                current = current.next;
            }
            if(current.getIndex() == index) {
                return current.getValue();
            } else {
                return 0.0;
            }
        }
    }

    private LinkedList backingList;
    private int length;

    public SparseVector() {
        this.length = 0;
        backingList = new LinkedList();
    }

    public SparseVector(int length) {
        this.length = length;
        backingList = new LinkedList();
    }

    @Override
    public void setElement(int index, double value) throws IndexOutOfBoundsException {
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        if(value == 0.0) {
            this.remove(index);
        }
        this.backingList.add(index, value);
    }

    @Override
    public double getElement(int index) throws IndexOutOfBoundsException{
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        return this.backingList.get(index);
    }

    @Override
    public void remove(int index) throws IndexOutOfBoundsException {
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        this.backingList.remove(index);
    }

    @Override
    public void add(ISparseVector vector) throws IllegalArgumentException {
        if(vector.getLength() != this.length) {
            throw new IllegalArgumentException(String.format("cannot add vector of length %d to vector of length %d", vector.getLength(), this.length));
        }
        for(int i = 0; i < vector.getLength(); i += 1) {
            this.setElement(i, (this.backingList.get(i) + vector.getElement(i)));
        }
    }

    @Override
    public int getLength() {
        return this.length;
    }
}
