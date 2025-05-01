package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityLinkedQueueTest {

    @Test
    void test(){
        try {
            PriorityLinkedQueue priorityLinkedQueue = new PriorityLinkedQueue();
            priorityLinkedQueue.enQueue("Juan", 1);
            priorityLinkedQueue.enQueue("Pedro", 2);
            priorityLinkedQueue.enQueue("Maria", 3);
            priorityLinkedQueue.enQueue("Julio", 3);

            System.out.println(priorityLinkedQueue);
        } catch (Exception q) {
            throw new RuntimeException();
        }
    }

}