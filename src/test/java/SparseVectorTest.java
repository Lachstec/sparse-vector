import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparseVectorTest {

    @Test
    @DisplayName("Check if setting elements and retrieving them returns the previously set value")
    void setElement() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);
        vec1.setElement(1,6.0);
        vec1.setElement(2,6.0);
        assertEquals(6.0 , vec1.getElement(2));
        assertEquals(6.0, vec1.getElement(1));
    }

    @Test
    @DisplayName("Check if retrieving an Element yields the correct value")
    void getElement() {
        SparseVector vec1 = new SparseVector(100);
        vec1.setElement(1, 5.0);

        assertEquals(5.0 , vec1.getElement(1));
        assertEquals(0.0 , vec1.getElement(2));
    }

    @Test
    @DisplayName("Check if removing an Element truly removes it as expected")
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
    @DisplayName("Test if two vectors where values are at the same indices return the correct value")
    void addSameIndices() {
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
    @DisplayName("Test if two vectors where values are at different indices return the correct value")
    void addDifferentIndices() {
        SparseVector vec1 = new SparseVector(10);
        vec1.setElement(4, 33.0);
        SparseVector vec2 = new SparseVector(10);
        vec2.setElement(1, 12.3);
        SparseVector expected = new SparseVector(10);
        expected.setElement(4, 33.0);
        expected.setElement(1, 12.3);
        vec1.add(vec2);

        assertTrue(vec1.equals(expected));
    }

    @Test
    @DisplayName("Check if adding two vectors with different amount of non-zero rows returns the expected result")
    void addDifferentElemCount() {
        SparseVector vec1 = new SparseVector(10);
        vec1.setElement(4, 33.0);
        SparseVector vec2 = new SparseVector(10);
        vec2.setElement(1, 12.3);
        vec2.setElement(3, 7.4);
        SparseVector expected = new SparseVector(10);
        expected.setElement(4, 33.0);
        expected.setElement(1, 12.3);
        expected.setElement(3, 7.4);

        assertTrue(vec1.equals(expected));
    }

    @Test
    @DisplayName("Check if adding two vectors where adding rows result in a zero row remove the row")
    void addResultingInNullRow() {
        SparseVector vec1 = new SparseVector(1);
        SparseVector vec2 = new SparseVector(1);
        vec1.setElement(0, -4.0);
        vec2.setElement(0, 4.0);
        vec1.add(vec2);
        SparseVector expected = new SparseVector(1);


        assertTrue(vec1.equals(expected));
    }

    @Test
    @DisplayName("Check that attempting to add two vectors with different lengths throws an IllegalArgumentException")
    void addWithDifferentLengths() {
        SparseVector vec1 = new SparseVector(10);
        SparseVector vec2 = new SparseVector(1);
        vec1.setElement(0, 44.0);
        vec2.setElement(0, 22.0);

        assertThrows(IllegalArgumentException.class, () -> vec1.add(vec2));
    }


    @Test
    @DisplayName("Check if the correct Length is returned")
    void getLength() {
        SparseVector vec = new SparseVector(1000);
        assertEquals(1000, vec.getLength());

        SparseVector vecWithoutLength = new SparseVector();
        assertEquals(0, vecWithoutLength.getLength());
    }

    @Test
    @DisplayName("Test that testing vectors for equality returns results as expected")
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
   @DisplayName("Test that accessing an index outside of length throws an IndexOutOfBoundsException")
    void testInvalidIndex() {
        SparseVector vector = new SparseVector(10);
        assertThrows(IndexOutOfBoundsException.class, () -> vector.getElement(11));
        assertThrows(IndexOutOfBoundsException.class, () -> vector.getElement(-1));
   }

}