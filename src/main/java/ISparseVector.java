public interface ISparseVector {
    void setElement(int index, double value);
    double getElement(int index);
    void remove(int index);
    void add(ISparseVector vector);
    int getLength();
}
