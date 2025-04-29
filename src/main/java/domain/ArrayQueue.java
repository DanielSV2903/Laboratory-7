package domain;

public class ArrayQueue implements Queue {
    private Object[] queue;
    private int front;
    private int rear;
    private int n;

    public ArrayQueue(int n) {
        this.n = n;
        queue = new Object[n];
        front = rear = (this.n-1);
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
        ArrayQueue aux=new ArrayQueue(size());
        int index=front+1;
        if (isEmpty()) throw new QueueException("Queue is empty");
        if (peek()==element) return index;
        while(queue[index]!=element) {
            aux.enQueue(deQueue());
            index++;
        }
        while (!isEmpty()) {
            aux.enQueue(deQueue());
        }
        while (!aux.isEmpty()) {
            enQueue(aux.deQueue());
        }
        return index;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        if (size()==n)
            throw new QueueException("Queue is full");
        if (isEmpty()) {
            front = rear = (this.n-1);
            queue[rear] = element;
            front--;
        }
        else {
            if (size()<n){
                for (int i=rear; i<front; i--){
                    Object temp =queue[i];
                    queue[rear]=element;
                    queue[i-1]=temp;
                }
                front--;
            }
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
        boolean found=false;
        ArrayQueue aux=new ArrayQueue(size());
        while(!isEmpty()) {
            aux.enQueue(deQueue());//traspasa elemento a elemento hacia la cola auxiliar,
            //para comparar si existe el elemento buscado
            if (aux.peek().equals(element)) found=true;
        }
        while (!aux.isEmpty()) {
            enQueue(aux.deQueue());//devuelve la cola a su estado original
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

}
