package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeaderLinkedQueueTest {
    @Test
    void test(){
        HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
        try {
            for (int i = 0; i < 15; i++)
                headerLinkedQueue.enQueue((util.Utility.random(30)));
            System.out.println(headerLinkedQueue);
        } catch (QueueException q) {
            throw new RuntimeException();
        }





    }

}