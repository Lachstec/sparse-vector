public interface ISparseVector {

    /**
     * Inserts the value at the index specified.
     * @param index, where the element is to be placed
     * @param value, of the element that is to be placed
     * @throws IndexOutOfBoundsException when index is greater than length or smaller than 0.
     */
    void setElement(int index, double value);

    /**
     * Finds the element at the specified index
     * @param index, the index of the element
     * @return returns the value of the element
     */
    double getElement(int index);

    /**
     * Removes the element at <code>index</code>
     * @param index of the element to remove
     */
    void remove(int index);

    /**
     * Adds another vector to the values in this instance
     * @param vector to add
     */
    void add(ISparseVector vector);

    /**
     * returns the actual length of the SparseVector.
     * @return returns the actual length of the SparseVector.
     */
    int getLength();
}
