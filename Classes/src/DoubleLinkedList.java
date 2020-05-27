import java.util.Random;

/**
 * Created by clombardo on 7/30/17.
 */
public class DoubleLinkedList<E> {

    // instance fields
    private Node head;
    private Node tail;
    private int length;
    private E label;

    /**
     * constructs a new doubleLinkedList object
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        length = 0;
        label = null;
    }

    /**
     * constructs a new doubleLinkedList object
     */
    public DoubleLinkedList(E inLabel) {
        head = null;
        tail = null;
        length = 0;
        label = inLabel;
    }

    /**
     * adds a node to the double linked list
     * @param item the data of the node to add
     */
    public void add(E item) {
        Node temp = new Node(item);
        if (isEmpty()) {
            head = temp;
            tail = temp;
        }
        else {
            temp.setPrevious(tail);
            tail.setNext(temp);
            tail = temp;
        }
        length++;
    }

    /**
     * removes a node from the double linked list
     * @param item the data of the node to remove
     */
    public void remove(E item) {
        Node currentNode = findByData(item);
        if (currentNode == null) {
            return;
        }
        if (currentNode.equals(head))
            head = head.getNext();
        else if (currentNode.equals(tail))
            tail = tail.getPrevious();
        Node prevNode = currentNode.getPrevious();
        Node nextNode = currentNode.getNext();
        if (prevNode != null)
            prevNode.setNext(nextNode);
        if (nextNode != null)
            nextNode.setPrevious(prevNode);
    }

    /**
     * finds a node
     * @param item the data of the node to find
     * @return the node being found, null if doesn't exist.
     */
    private Node findByData(E item) {
        Node currentNode = head;
        while (currentNode != null){
            if (currentNode.getData().equals(item))
                return currentNode;
            currentNode = currentNode.getNext();
        }
        return null;
    }

    /**
     * ONLY FOR USE WITH VillageSystem's adjacencyList
     * searches the linked list of linked lists for one with a certain label
     * @param label the label of the desired linked list
     * @return the list being searched for, null if doesn't exist
     */
    public DoubleLinkedList<Village> graphFind(Village label) {
        for(Node currentNode = head; currentNode != null; currentNode = currentNode.getNext()) {
            DoubleLinkedList<Village> currentList = (DoubleLinkedList<Village>) currentNode.getData();
            if (currentList.getLabel().equals(label)) {return currentList;}
        }
        return null;
    }

    /**
     * @return the label of the list
     */
    public E getLabel() {
        return label;
    }

    /**
     * @return the head of this linked list
     */
    public Node getHead() {
        return head;
    }

    /**
     * @return a string version of the double linked list
     * used for testing
     */
    public String toString() {
        String toReturn = "";
        Node currentNode = head;
        while (currentNode != null) {
            toReturn += currentNode.getData() + " ";
            currentNode = currentNode.getNext();
        }
        return toReturn;
    }

    public int getLength() {
        return length;
    }

    /**
     * helper method
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * removes all items from the list
     */
    public void clearList() {
        head = null;
        tail = null;
        length = 0;
    }

    /**
     * @return the data from a random node in the list
     */
    public E getRandomItem() {
        Random r = new Random();

        int location = r.nextInt(length);
        int counter = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (counter == location)
                return (E) currentNode.getData();
            currentNode = currentNode.getNext();
            counter++;
        }
        return null;
    }

}
