package question2;

/**
 *
 * @author khalil2535
 */
public class Question2 {

    public static void main(String[] args) {
        SinglyLinkedList<String> stringsList = new SinglyLinkedList<>();

        Node<String> helloNode = new Node<>(" hello ");
        Node<String> javaNode = new Node<>(" in DataStuctures !");
        Node<String> forYou = new Node<>(" for you ");

        stringsList.insertFirst(helloNode.getItem());
        stringsList.insertLast(javaNode.getItem());
        stringsList.insertAfter(helloNode, forYou);

        System.out.println(stringsList);

    }

}
