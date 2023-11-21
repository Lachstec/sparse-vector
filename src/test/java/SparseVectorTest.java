import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparseVectorTest {

    @Test
    void setElement() {
    }

    @Test
    void getElement() {
    }

    @Test
    void remove() {
    }

    @Test
    void add() {
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
        SparseVector vec2 = new SparseVector(100);
        vec1.setElement(1, 5.0);
        vec1.setElement(10, 50.0);
        vec1.setElement(100, 500.0);
        vec2.setElement(1, 5.0);
        vec2.setElement(10, 50.0);
        vec2.setElement(100, 500.0);

        assertTrue(vec1.equals(vec2));
    }
}