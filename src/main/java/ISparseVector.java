public interface ISparseVector {

    /**
     * Inserts the value at the index specified.
     * @param index, where the element is to be placed
     * @param value, of the element that is to be placed
     */
    void setElement(int index, double value);

    /**
     * Finds the element at the specified index
     * @param index, the index of the element
     * @return returns the value of the element
     */
    double getElement(int index);
    void remove(int index);
    void add(ISparseVector vector);

    /**
     * returns the actual length of the SparseVector.
     * @return int
     */
    int getLength();
}
