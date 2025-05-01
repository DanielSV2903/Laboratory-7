package domain;

public class LinkedQueue implements Queue {
    private Node front;
    private Node rear;
    private int counter;

    public LinkedQueue() {
        front =rear= null;
        counter = 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
        front = rear = null;
        counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return counter==0;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");
        if (rear.data.equals(element)) return counter;
        if (front.data.equals(element)) return 1;
        LinkedQueue aux= new LinkedQueue();
        int index=1;
            while(peek()!=element){
                aux.enQueue(deQueue());
                index++;
            }
            while(!isEmpty()){
                aux.enQueue(deQueue());
            }
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
        return index;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        if (isEmpty()) {
            front = rear = newNode;
            counter=1;
        }else{
            rear.next = newNode;
            rear = newNode;
            counter++;
        }
    }

    @Override
    public Object deQueue() throws QueueException {
        if (isEmpty())throw new QueueException("Queue is empty");
        Object element = front.data;
        front = front.next;
        counter--;
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if (isEmpty())
            throw new QueueException("Queue is empty");
         boolean found = false;
         LinkedQueue aux= new LinkedQueue();
         while(!isEmpty()){
             aux.enQueue(deQueue());
             if (aux.rear.data.equals(element)) found = true;
         }
         while(!aux.isEmpty()){
             enQueue(aux.deQueue());
         }
        return found;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty())throw new QueueException("Queue is empty");
        return front.data;
    }

    @Override
    public Object front() throws QueueException {
        return peek();
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "Linked Queue is Empty";

        String result = "Linked Queue Content \n";
        LinkedQueue aux = new LinkedQueue();

        try{
            while (!isEmpty()){
                result += front() + "\n";
                aux.enQueue(deQueue());
            }

            while (!isEmpty()){
                enQueue(aux.deQueue());
            }
        } catch (QueueException q) {
            throw new RuntimeException();
        }
        return result;
    }
}
