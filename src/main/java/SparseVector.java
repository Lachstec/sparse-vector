import java.util.Optional;

public class SparseVector implements ISparseVector {

    private class LinkedList {
        static class Node {
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

        //TODO: Unit Testing in progress
        public void add(int index, double value) {
            Node newNode = new Node(value, index);
            // if the list is empty the new element becomes the head
            if (head == null || head.index > index) {
                newNode.next = head;
                head = newNode;
            } else if(head.index == index) {
                // check if head and the new element have the same index
                head.value = value;
                // return so we don't increment length
                return;
            } else {
                Node current = head;
                while(current.next != null && current.next.index < index) {
                    current = current.next;
                }
                if(current.next != null && current.next.index == index) {
                    current.next.value = value;
                    return;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
            length += 1;
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
                if(prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
            }
            this.length -= 1;
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

        private Node getHead() {
            return this.head;
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
            if(this.getElement(index) != 0.0) {
                this.remove(index);
            }
        } else {
            this.backingList.add(index, value);
        }
    }

    @Override
    public double getElement(int index) throws IndexOutOfBoundsException{
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        if(this.backingList.length == 0) {
            return 0.0;
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
            double sum = this.getElement(i) + vector.getElement(i);
            this.setElement(i, sum);
        }
    }

    @Override
    public int getLength() {
        return this.length;
    }

    public boolean equals(SparseVector other) {
        if(this.length == other.getLength()) {
            LinkedList.Node node_self = this.backingList.getHead();
            LinkedList.Node node_other = other.backingList.getHead();
            if(node_other == null && node_self == null) {
                return true;
            } else {
                while (node_self.getNext() != null && node_other.getNext() != null) {
                    if (node_self.getIndex() != node_other.getIndex() || node_self.getValue() != node_other.getValue()) {
                        return false;
                    }
                    node_self = node_self.getNext();
                    node_other = node_other.getNext();
                }
            }
            return true;
        }
        return false;
    }
}
