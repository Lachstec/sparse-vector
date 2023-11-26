import java.util.Random;

/**
 * SparseVector with a LinkedList as backing Datastructure.
 * The LinkedList only contains the non-zero rows of the vector
 */
public class SparseVector implements ISparseVector {
    /*
        Supporting Linked List Construct. Usually this would be in an extra file
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
         *  Adds an element to the list at <code>index</code>
         * @param index where to insert the value
         * @param value to insert
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
         *  Remove an element at a given <code>index</code> from the list.
         * @param index of the element to remove
         */
        public void remove(int index) {
            if(head == null) {
                return;
            }
            Node current = this.head;
            Node prev = null;
            /*
                Iterate through list until we find an Element with given index
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
         *  Retrieve the value at <code>index</code>
         * @param index of the desired value
         * @return the value at <code>index</code>
         */
        public double get(int index) {
            Node current = this.head;
            /*
                Iterate through list until we find an Element with given index
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

    /**
     * Inserts the value at the index specified.
     * @param index, where the element is to be placed
     * @param value, of the element that is to be placed
     * @throws IndexOutOfBoundsException when index is greater than length or smaller than 0.
     */
    @Override
    public void setElement(int index, double value) throws IndexOutOfBoundsException {
        //if the index is impossible, an exception is thrown
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

    /**
     * Finds the element at the specified index
     * @param index, the index of the element
     * @return returns the value of the element
     */
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

    /**
     * Removes the element at <code>index</code>
     * @param index of the element to remove
     */
    @Override
    public void remove(int index) throws IndexOutOfBoundsException {
        //if the index is impossible, an exception is thrown
        if(index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %d out of bounds for size %d", index, this.length));
        }
        //else, the specified index is removed
        this.backingList.remove(index);
    }

    /**
     * Adds another vector to the values in this instance
     * @param vector to add
     */
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

    /**
     * returns the actual length of the SparseVector.
     * @return returns the actual length of the SparseVector.
     */
    @Override
    public int getLength() {
        return this.length;
    }

    /**
     * Checks if the passed vector is equal to the given vector.
     * Two vectors are considered equal to each other when they have the same length
     * and the same elements at the same indices.
     * @param other vector to compare to
     * @return true when both vectors are equal, false when not
     */
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

    /**
     * Performs a Test on a vector with 100000 elements. It asserts that elements get deleted
     * when they add up to 0 and that elements are correctly added to the vector when using {@link #add(ISparseVector)}.
     */
    public static void test() {
        SparseVector vec1 = new SparseVector(100000);
        SparseVector vec2 = new SparseVector(100000);
        SparseVector vec3 = new SparseVector(100000);
        // Random to generate values for our vector
        Random r = new Random();
        // Setup one element to add up to zero to test this case
        vec1.setElement(0, -10.0);
        // fill our vector with elements
        for(int i = 2; i < 100000; i += 1) {
            if(i % 2 == 0) {
                double value = 1000 * r.nextDouble();
                vec1.setElement(i, value);
                vec3.setElement(i, value);
            }
        }
        vec2.setElement(0, 10.0);
        vec2.setElement(1, 20.0);
        // add vectors together
        vec1.add(vec2);
        // assert that everything worked properly
        assert vec1.equals(vec3);
    }
}
