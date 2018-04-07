package revision;

import java.util.Arrays;

public class Revision {

    public static void main(String[] args) {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.insertLast("1");
        myList.insertLast("2");
        myList.insertLast("3");
        myList.insertLast("4");
        myList.insertLast("5");

        Stack<String> myStack = new Stack<>(5);
        myStack.push(myList.remove());
        myStack.push(myList.remove());
        myStack.push(myList.remove());
        myStack.push(myList.remove());
        myStack.push(myList.remove());

        String[] array = new String[5];
        int i = 0;
        while (!myStack.isEmpty()) {
            array[i++] = myStack.pop().toString();
        }
        System.out.println(Arrays.toString(array));
    }

}
