package domain.queue;

public class PriorityLinkedQueue implements Queue {
    private Node front;
    private Node rear;
    private int counter;

    public PriorityLinkedQueue() {
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
        return front==null;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if (isEmpty()) throw new QueueException("Queue is empty");
        if (rear.data.equals(element)) return counter;
        if (front.data.equals(element)) return 1;
        PriorityLinkedQueue aux= new PriorityLinkedQueue();
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
        if(isEmpty()){ //la cola no existe
            rear = newNode;
            //garantizo q anterior quede apuntando al primer nodo
            front=rear; //anterior=posterior
        }else{ //significa q al menos hay un elemento en la cola
            rear.next = newNode; //posterior.sgte = nuevoNodo
            rear = newNode; //posterior = nuevoNodo
        }
        //al final actualizo el contador
        counter++;
    }

    public void enQueue(Object element, int priority) throws QueueException {
        Node newNode = new Node(element, priority);
        if (isEmpty()) {
            rear = newNode;
            front = rear;
        }else{
            Node aux = front;
            Node prev = front;
            while (aux != null && aux.priority >= priority){
                prev = aux; //dejo un apuntador al nodo aterior para ver que esta pasando
                aux = aux.next;
            }
            //se sale cuando alcanza a nulo o la prioridad del objeto es menor
            //primero pregunto si el nuevo elemento tiene una prioridad
            //más alta al elemento del frente de la cola
            if (aux == front){
                newNode.next = front;
                front = newNode;
            } else if (aux == null) {//se encola em forma normal
                prev.next = newNode;
                rear = newNode;
            } else {//el nuevo elemento va a quedar en medio de dos nodos
                prev.next = newNode;
                newNode.next = aux;
            }
            counter++;
        }
    }



    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty())
            throw new QueueException("Priority Linked Queue is Empty");
        Object element = front.data;
        //caso 1. cuando solo hay un elemento
        //cuando estan apuntando al mismo nodo
        if(front==rear){
            clear(); //elimino la cola
        }else{ //caso 2. caso contrario
            front = front.next; //anterior=anterior.sgte
        }
        //actualizo el contador de elementos encolados
        counter--;
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if (isEmpty())
            throw new QueueException("Queue is empty");
         boolean found = false;
         PriorityLinkedQueue aux= new PriorityLinkedQueue();
         while(!isEmpty()){
             if(util.Utility.compare(front(), element)==0){
                 found = true;
             }
             aux.enQueue(deQueue());
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
        PriorityLinkedQueue aux = new PriorityLinkedQueue();

        try{
            while (!isEmpty()){
                result += front() + "\n";
                aux.enQueue(deQueue());
            }

            while (!aux.isEmpty()) {
                enQueue(aux.deQueue());
            }

        } catch (QueueException q) {
            throw new RuntimeException();
        }
        return result;
    }
}
