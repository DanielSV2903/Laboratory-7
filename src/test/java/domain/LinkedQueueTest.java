package domain;

import domain.queue.LinkedQueue;
import domain.queue.QueueException;
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

        System.out.println(queue);
        System.out.println(queue.size());
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

    @Test
    void test1(){
        LinkedQueue linkedQueue = new LinkedQueue();
        String[] array = {"(())()", "(()", "())(", "", "((()))", "(()(()))"};
        for (String i : array){
            boolean result = isBalanced(linkedQueue, i);
            System.out.println("Expression: " + i + " → " + (result ? " is balanced" : " is not balanced"));
        }

        linkedQueue.clear();

        for (int i = 0; i < 15; i++){
            linkedQueue.enQueue(util.Utility.random(30));
        }
        System.out.println("___With duplicates"+linkedQueue);
        removeDuplicates(linkedQueue);
        System.out.println("___Without duplicates"+linkedQueue);
    }

    boolean isBalanced(LinkedQueue queue, String expression) {
        int counter = 0;
        for (char character : expression.toCharArray()) {
            if (character == '(' || character == ')') {
                queue.enQueue(character);
                if (character == '(') counter++;
                else counter--;
            }
        }
        if (counter == 0) return true;
        return false;
    }

    @Test
    void removeDuplicatesTest(){
        LinkedQueue linkedQueue = new LinkedQueue();

        for (int i = 0; i < 20; i++)
            linkedQueue.enQueue(util.Utility.random(20));
        System.out.println("___With duplicates"+linkedQueue);
        removeDuplicates(linkedQueue);
        System.out.println("___Without duplicates"+linkedQueue);




    }

    void removeDuplicates(LinkedQueue queue){
        LinkedQueue aux = new LinkedQueue();
        for (int i = 0; i<queue.size(); i++){
            int a = i;
            while (a>0){
                aux.enQueue(queue.deQueue());
                a--;
            }
            Object auxOb = queue.front();
            while (!queue.isEmpty()){
                aux.enQueue(queue.deQueue());
                if (util.Utility.compare(auxOb, queue.front())==0){
                    queue.deQueue();
                }
            }
            queue = aux;
        }
    }


}