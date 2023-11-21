public interface ISparseVector {

    /**
     * Inserts the value at the index specified.
     * @param index, where the element is to be placed
     * @param value, of the element that is to be placed
     */
    void setElement(int index, double value);
    double getElement(int index);
    void remove(int index);
    void add(ISparseVector vector);
    int getLength();
}
