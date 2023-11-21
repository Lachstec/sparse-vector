public interface ISparseVector {
    void setElement(int index, double value);
    double getElement(int index);

    /**
     * @param index
     */
    void remove(int index);

    /**
     * Adds the values in <code>vector</code> to this instance
     * @param vector to add
     */
    void add(ISparseVector vector);
    int getLength();
}
