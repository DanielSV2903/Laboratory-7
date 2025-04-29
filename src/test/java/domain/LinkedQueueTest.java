package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LinkedQueueTest {

    @Test
    void contains() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        queue.enQueue(40);
        assertEquals(2,queue.indexOf(20));
        assertEquals(1,queue.indexOf(10));
        assertEquals(4,queue.indexOf(40));
        assertEquals(true,queue.contains(30));
        assertEquals(false,queue.contains(50));
    }
}