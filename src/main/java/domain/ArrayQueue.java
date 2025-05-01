package domain;

public class ArrayQueue implements Queue {
    private Object[] queue;
    private int front;
    private int rear;
    private int n;

    public ArrayQueue(int n) {
        if (n<=0) System.exit(1);
        this.n = n;
        queue = new Object[n];
        front = rear = (this.n-1);
        //this.front = rear;
    }
    @Override
    public int size() {
        return rear-front;
    }

    @Override
    public void clear() {
        this.front=this.rear=(this.n-1);
        queue=new Object[this.n];
    }

    @Override
    public boolean isEmpty() {
        return rear==front;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");
        ArrayQueue aux = new ArrayQueue(n);
        int index = 0;
        int foundedIndex = -1;//-1 si el indice no fue encontrado

        while (!isEmpty()) {
            Object current = deQueue();//se guarda en una variable para comparar
            if (foundedIndex == -1 && current.equals(element)) {
                foundedIndex = index;
            }
            aux.enQueue(current);
            index++;
        }
        while (!aux.isEmpty()) {
            enQueue(aux.deQueue());
        }

        return foundedIndex;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        if (size() == n)
            throw new QueueException("Queue is full");

        if (isEmpty()) {
            queue[rear] = element;
            front--;
        } else {
            for (int i = front + 1; i <= rear; i++) {
                queue[i - 1] = queue[i];
            }
            queue[rear] = element;
            front--;
        }
    }

    @Override
    public Object deQueue() throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");
        return queue[++front];
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");

        boolean found = false;
        ArrayQueue aux = new ArrayQueue(n);

        while (!isEmpty()) {
            Object current = deQueue();//guardo en una variable para evaluar
            if (!found && current.equals(element)) {
                found = true;
            }
            aux.enQueue(current);
        }
        while (!aux.isEmpty()) {
            enQueue(aux.deQueue());
        }

        return found;
    }

    @Override
    public Object peek() throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");
        return queue[front+1];
    }

    @Override
    public Object front() throws QueueException {
        return peek();
    }

    @Override
    public String toString() {
        if (isEmpty())
            return "Array Queue is Empty";

        String result = "Array Queue Content \n";
        ArrayQueue aux = new ArrayQueue(size());

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
