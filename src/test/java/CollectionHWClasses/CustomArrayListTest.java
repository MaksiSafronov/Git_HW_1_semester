package CollectionHWClasses;

import com.mipt.maksimsafronov.CollectionHWClasses.CustomArrayList;
import com.mipt.maksimsafronov.CollectionHWClasses.CustomIterator;
import com.mipt.maksimsafronov.CollectionHWClasses.CustomList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    private CustomList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
    }

    @Test
    void testAddAndGet() {
        assertTrue(list.add("A"));
        assertEquals("A", list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void testGetInvalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testRemove() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals("B", list.remove(1));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    void testRemoveInvalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }

    @Test
    void testDynamicExpansion() {
        CustomList<Integer> intList = new CustomArrayList<>(2);

        for (int i = 0; i < 10; i++) {
            intList.add(i);
        }

        assertEquals(10, intList.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, intList.get(i));
        }
    }

    @Test
    void testIterator() {
        list.add("A");
        list.add("B");
        list.add("C");

        CustomIterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorOnEmptyList() {
        CustomIterator<String> iterator = list.iterator();
        assertFalse(iterator.hasNext());
    }
}
