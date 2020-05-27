/**
 * Created by clombardo on 7/30/17.
 */
public class Node<E> {

    // instance fields
    private Node next;
    private Node previous;
    private E data;

    /**
     * constructs a new node
     * @param inData the data of the node
     */
    public Node(E inData) {
        data = inData;
        next = null;
        previous = null;
    }

    /**
     * @return the data of the node
     */
    public E getData() {
        return data;
    }

    /**
     * changes the next of this node
     * @param next the node to make the next of this node
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * changes the previous of this node
     * @param previous the node to make the previous of this node
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * @return the next of this node
     */
    public Node getNext() {
        return next;
    }

    /**
     * @return the previous of this node
     */
    public Node getPrevious() {
        return previous;
    }
}
