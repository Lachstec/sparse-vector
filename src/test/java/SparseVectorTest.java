import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparseVectorTest {

    @Test
    void setElement() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);
        vec1.setElement(1,6);
        vec1.setElement(2,6);
        assertEquals(6.0 , vec1.getElement(2));
    }

    @Test
    void getElement() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);

        assertEquals(5.0 , vec1.getElement(1));
        assertEquals(0.0 , vec1.getElement(2));
    }

    @Test
    void remove() {
        SparseVector vector = new SparseVector(10);
        SparseVector emptyVector = new SparseVector(10);
        for(int i = 0; i < 10; i += 1) {
            vector.setElement(i, i);
        }
        for(int i = 0; i < 10; i += 1) {
            vector.remove(i);
        }
        assertTrue(vector.equals(emptyVector));
    }

    @Test
    void add() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);
        vec1.setElement(10, 50.0);
        vec1.setElement(100, 500.0);

        SparseVector vec2 = new SparseVector(100);
        vec2.setElement(1, 5.0);
        vec2.setElement(10, 50.0);
        vec2.setElement(100, 500.0);

        SparseVector vec3 = new SparseVector(100);
        vec3.setElement(1, 10.0);
        vec3.setElement(10, 100.0);
        vec3.setElement(100, 1000.0);

        vec1.add(vec2);

        assertTrue(vec3.equals(vec1));
    }

    @Test
    void getLength() {
        SparseVector vec = new SparseVector(1000);
        assertEquals(1000, vec.getLength());

        SparseVector vecWithoutLength = new SparseVector();
        assertEquals(0, vecWithoutLength.getLength());
    }

    @Test
    void testEquals() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);
        vec1.setElement(10, 50.0);
        vec1.setElement(100, 500.0);

        SparseVector vec2 = new SparseVector(100);
        vec2.setElement(1, 5.0);
        vec2.setElement(10, 50.0);
        vec2.setElement(100, 500.0);

        assertTrue(vec1.equals(vec2));
    }

   @Test
    void testInvalidIndex() {
        SparseVector vector = new SparseVector(10);
        assertThrows(IndexOutOfBoundsException.class, () -> vector.getElement(11));
        assertThrows(IndexOutOfBoundsException.class, () -> vector.getElement(-1));
   }

}