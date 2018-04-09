package question2;

/**
 *
 * @author khalil2535
 * @param <E>
 */
public class SinglyLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SinglyLinkedList() {
        size = 0;
    }

    public SinglyLinkedList(E item) {
        tail = new Node<>(item);
        head = tail;
        head.setNextx(tail);
        size = 1;
    }

    public SinglyLinkedList(E head, E tail) {
        if (head != null) {
            this.head = new Node<>(head);
            this.tail = new Node<>(tail);
            size = head == tail ? 1 : 2;
            this.head.setNextx(this.tail);
        }
    }

    public void removeFirst() {
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
    }

    /**
     * @deprecated
     */
    public void removeLast() {
        tail.setNextx(null);
        size--;
        if (size == 0) {
            head = null;
        }
    }

    public void insertLast(E item) {
        Node<E> node = new Node<>(item);
        if (size == 0) {
            this.head = node;
            this.tail = node;
            this.size++;
        } else {
            this.tail.setNext(node);
            this.tail = tail.getNext();
            this.size++;
        }

    }

    public void insertFirst(E firstItem) {
        Node<E> newHead = new Node<>(firstItem);
        if (this.size == 0) {
            this.head = newHead;
            this.tail = newHead;
            this.size++;
        } else {
            newHead.setNext(this.head);
            this.head = newHead;
            this.size++;
        }
    }

    public void insertAfter(Node<E> previousNode, Node<E> newNode) {

        if (tail.getItem().equals(previousNode.getItem())) {// insert after tail = insertLast
            insertLast(newNode.getItem());

        } else if (head.getItem().equals(previousNode.getItem())) {// insert newNode After head

            newNode.setNext(head.getNext());
            this.head.setNext(newNode);
            this.size++;

        } else {
            Node<E> currentNode = head; // to get the previousNode
            while (!currentNode.getNext().getItem().equals(previousNode.getItem())) {
                if (!currentNode.hasNext()) {
                    throw new IllegalArgumentException("There isn't any node cotains previousItem");
                }
                currentNode = currentNode.getNext();
            }
            // now we got the previousNode (wanted)
            newNode.setNext(currentNode.getNext());
            currentNode.setNext(newNode);
            size++;
        }
    }

    @Override
    public String toString() {
        String all = "";
        Node<E> currentnode = head;
        while (currentnode != null) {
            all += currentnode.toString();
            currentnode = currentnode.getNext();
            if (currentnode != null) {
                all += " -> ";
            }
        }
        return all;
    }
}

class Node<E> {

    private final E item;
    private Node<E> next;

    Node(E item) {
        this.item = item;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNextx(Node<E> next) {
        this.next = next;
    }

    public E getItem() {
        return item;
    }

    public boolean hasNext() {
        return next != null;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return getItem().toString();
    }
}
