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

    @Test
    void size() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        assertEquals(2,queue.size());
    }

    @Test
    void clear() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    void isEmpty() {
        LinkedQueue queue = new LinkedQueue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void indexOf() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        queue.enQueue(40);
        assertEquals(2,queue.indexOf(20));
        assertEquals(1,queue.indexOf(10));
        assertEquals(4,queue.indexOf(40));
    }

    @Test
    void enQueue() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        queue.enQueue(40);
        assertEquals(true,queue.contains(20));
        assertEquals(true,queue.contains(10));
        assertEquals(true,queue.contains(40));
        assertEquals(false,queue.contains(50));
    }

    @Test
    void deQueue() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        queue.enQueue(40);
        queue.deQueue();
        queue.deQueue();
        assertEquals(false,queue.contains(10));
        assertEquals(false,queue.contains(20));
    }

    @Test
    void front() {
        LinkedQueue queue = new LinkedQueue();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        assertEquals(10,queue.front());
    }

    @Test
    void test(){
        LinkedQueue linkedQueue = new LinkedQueue();
        try {
            for (int i = 0; i < 15; i++)
                linkedQueue.enQueue((util.Utility.random(30)));
            System.out.println(linkedQueue);
        } catch (QueueException q) {
            throw new RuntimeException();
        }
    }
}