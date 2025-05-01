package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    @Test
    void size() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(10);
        queue.enQueue(10);
        assertTrue(queue.size()==2);
    }

    @Test
    void clear() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(10);
        queue.enQueue(10);
        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    void isEmpty() {
        ArrayQueue queue=new ArrayQueue(10);
        assertTrue(queue.isEmpty());
    }

    @Test
    void indexOf() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.enQueue(4);
        assertEquals(1,queue.indexOf(2));
        assertEquals(2,queue.indexOf(3));
    }

    @Test
    void enQueue() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        assertEquals(2,queue.size());
        assertEquals(1,queue.peek());
        assertEquals(1,queue.indexOf(2));
    }

    @Test
    void deQueue() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.deQueue();
        queue.deQueue();
        assertFalse(queue.contains(1));
        assertFalse(queue.contains(2));
        assertTrue(queue.size()==1);
    }

    @Test
    void contains() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        assertTrue(queue.contains(1));
        assertTrue(queue.contains(2));
        assertFalse(queue.contains(3));
    }

    @Test
    void front() {
        ArrayQueue queue=new ArrayQueue(10);
        queue.enQueue("a");
        queue.enQueue("b");
        assertEquals("a",queue.front());
    }

    @Test
    void test(){
        ArrayQueue arrayQueue = new ArrayQueue(20);
        try {
            for (int i = 0; i < 15; i++)
                arrayQueue.enQueue((util.Utility.random(30)));
            System.out.println(arrayQueue);
        } catch (QueueException q) {
            throw new RuntimeException();
        }
    }
}