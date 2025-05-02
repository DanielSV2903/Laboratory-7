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

            deQueueBy(q1,q2,"weather");
            System.out.println("Sunny & foggy deQueued:\nQueue 1:\n"+q1+"\nQueue 2:"+q2);
            deQueueBy(q1,q2,"place");
            System.out.println("Paraíso & Liberia deQueued:\nQueue 1:\n"+q1+"\nQueue 2:"+q2);
            deQueueBy2(q1,q2,q3);
            System.out.println("ThunderStorm & Cloudy deQueued:\nQueue 1:\n"+q1+"\nQueue 2:"+q2+"\nQueue 3:"+q3);


        } catch (QueueException e){
            throw new RuntimeException();
        }
    }
    private void deQueueBy(HeaderLinkedQueue origin,HeaderLinkedQueue destiny,String criteria) throws QueueException {
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
        switch (criteria){
            case "weather":
                while (!origin.isEmpty()){
                    Climate climate = (Climate) origin.deQueue();
                    if (climate.getWeather().getDescription().equals("sunny")||climate.getWeather().getDescription().equals("foggy")){
                        destiny.enQueue(climate);
                    }else{
                        aux.enQueue(climate);
                    }
                }
                while (!aux.isEmpty()){
                    origin.enQueue(aux.deQueue());
                }
                case "place":
                    while (!origin.isEmpty()){
                        Climate climate = (Climate) origin.deQueue();
                        if (climate.getPlace().getName().equals("Paraíso")||climate.getPlace().getName().equals("Liberia")){
                            destiny.enQueue(climate);
                        }else{
                            aux.enQueue(climate);
                        }
                    }
                    while (!aux.isEmpty()){
                        origin.enQueue(aux.deQueue());
                    }
        }
    }
    private void deQueueBy2(HeaderLinkedQueue origin,HeaderLinkedQueue destiny,HeaderLinkedQueue destiny2) throws QueueException {
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
        while (!origin.isEmpty()){
            Climate climate = (Climate) origin.deQueue();
            if (climate.getWeather().getDescription().equals("thunderstorm")||climate.getWeather().getDescription().equals("cloudy")){
                destiny.enQueue(climate);
                destiny2.enQueue(climate);
            }else aux.enQueue(climate);
        }
        while (!aux.isEmpty()){
            origin.enQueue(aux.deQueue());
        }
    }
}