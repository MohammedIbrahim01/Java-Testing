package question3;

/**
 *
 * @author khalil2535
 */
public class Question3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BinaryTree<Integer> bT = new BinaryTree<>();
        bT.addItem(2);
        bT.addItem(1);
        bT.addItem(3);

        System.out.println("Binary Tree");
        System.out.println(bT);

        System.out.println("In Order :-");
        System.out.println(bT.inOrder());

        System.out.println("");
        System.out.print("the Inorder Next for 2 is : ");
        System.out.println(bT.inOrderNext(2));
        System.out.print("the Inorder Next for 1 is : ");
        System.out.println(bT.inOrderNext(1));
        System.out.print("the Inorder Next for 3 is : ");
        System.out.println(bT.inOrderNext(3));

    }

}
