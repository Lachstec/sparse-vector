public class SparseVector implements ISparseVector {
    /*
        Supporting Linked List Construct usually this would be in an extra file
     */
    private static class LinkedList {
        static class Node {
            private final int index;
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

        /**
         *
         * @param index
         * @param value
         */
        public void add(int index, double value) {
            Node newNode = new Node(value, index);
            // if the list is empty the new element becomes the head
            if (head == null || head.index > index) {
                newNode.setNext(head);
                head = newNode;
            } else if(head.index == index) {
                // check if head and the new element have the same index
                head.setValue(value);
                // return so we don't increment length
                return;
            } else {
                Node current = head;
                /*
                    Iterate through list as long as the Index of the Element we add
                    is higher than the index of the current Element
                 */
                while(current.next != null && current.next.index < index) {
                    current = current.getNext();
                }
                // Add the new Element to existing index
                if(current.next != null && current.next.index == index) {
                    current.getNext().setValue(value);
                    return;
                }
                // Add new Element to List
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
            length += 1;
        }

        /**
         *
         * @param index
         */
        public void remove(int index) {
            if(head == null) {
                return;
            }
            Node current = this.head;
            Node prev = null;
            /*
                Iterate through list until we we find an Element with given index
             */
            while(current.getIndex() != index && current.next != null) {
                prev = current;
                current = current.next;
            }
            // First check to prevent NullPointerException
            if(current.getIndex() == index) {
                // if true remove first Element else remove other element
                if(prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
            }
            this.length -= 1;
        }

        /**
         *
         * @param index
         * @return
         */
        public double get(int index) {
            Node current = this.head;
            /*
                Iterate through list until we we find an Element with given index
             */
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

    private final LinkedList backingList;
    private final int length;

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
        //if the index is impossible, an exception is thrown
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        //if the actual length of the list is 0, we know that only values == 0 can be present, so we return 0.0
        if(this.backingList.length == 0) {
            return 0.0;
        }
        //else, we return the index
        return this.backingList.get(index);
    }

    @Override
    public void remove(int index) throws IndexOutOfBoundsException {
        //if the index is impossible, an exception is thrown
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        //else, the specified index is removed
        this.backingList.remove(index);
    }

    @Override
    public void add(ISparseVector vector) throws IllegalArgumentException {
        //if the vectors are not equal in length, they cannot be added; an exception is thrown
        if(vector.getLength() != this.length) {
            throw new IllegalArgumentException(String.format("cannot add vector of length %d to vector of length %d", vector.getLength(), this.length));
        }
        //vectors are added, node by node; getElement() and setElement() is used
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
        //both vectors need to be equal in length
        if(this.length == other.getLength()) {
            LinkedList.Node node_self = this.backingList.getHead();
            LinkedList.Node node_other = other.backingList.getHead();
            //if they are equal in length, and one vector is 0 in length, both must be
            if(node_self == null) {
                return node_other == null;
            } else {
                //we step through the vector one by one until the end is reached
                while (node_self.getNext() != null && node_other.getNext() != null) {
                    //if an inequality is found, false will be returned
                    if (node_self.getIndex() != node_other.getIndex() || node_self.getValue() != node_other.getValue()) {
                        return false;
                    }
                    //the nodes are incremented
                    node_self = node_self.getNext();
                    node_other = node_other.getNext();
                }
            }
            return true;
        }
        return false;
    }
}
