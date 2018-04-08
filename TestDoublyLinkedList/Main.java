package TestDoublyLinkedList;


/**
 *
 * @author khalil2535
 */
public class Main {

    public static void main(String[] args) {
        DoublyLinkedList<Object> x = new DoublyLinkedList<>();
        x.addFirst("1");
        System.out.println(x.header.getElement());

    }

}
