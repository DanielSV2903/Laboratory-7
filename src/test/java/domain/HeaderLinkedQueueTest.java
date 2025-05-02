package domain;

import domain.person.Climate;
import domain.person.Place;
import domain.person.Weather;
import domain.queue.HeaderLinkedQueue;
import domain.queue.QueueException;
import org.junit.jupiter.api.Test;

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

    @Test
    void testPlaceWeather(){
        try {
            HeaderLinkedQueue q1 = new HeaderLinkedQueue();
            HeaderLinkedQueue q2 = new HeaderLinkedQueue();
            HeaderLinkedQueue q3 = new HeaderLinkedQueue();

            for (int i = 0; i < 20; i++)
                q1.enQueue(new Climate(new Place(util.Utility.randomGetPlace()), new Weather(util.Utility.randomGetWeather())));

            System.out.println(q1);

        } catch (QueueException e){
            throw new RuntimeException();
        }



    }
}