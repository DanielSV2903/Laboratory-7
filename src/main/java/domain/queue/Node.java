package domain.queue;

public class Node {
    public int priority; //1.low, 2.medium, 3.high
    public Object data;
    public Node next; //apuntador al nodo siguiente

    //Constructor 1

    public Node(Object data) {
        this.data = data;
        this.next = null; //puntero al sgte nodo es nulo por default
    }

    //Constructor 2
    public Node() {
        this.next = null;
    }

    public Node(Object element, int priority) {
        this.data = element;
        this.priority = priority;
        this.next = null;
    }
}
