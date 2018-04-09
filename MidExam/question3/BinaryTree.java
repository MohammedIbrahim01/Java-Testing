package question3;

/**
 *
 * @author khalil2535
 * @param <E>
 */
public class BinaryTree<E extends Comparable<E>> {

    private Node<E> root;

    public BinaryTree(E rootItem) {
        root = new Node<>(rootItem);
    }

    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    public BinaryTree() {
    }

    public String inOrder() {
        if (root == null) {
            return "";
        } else {
            String myTree = "";
            myTree += " " + new BinaryTree<E>((Node) root.getLeft()).inOrder();
            myTree += root.toString();
            myTree += new BinaryTree<E>((Node) root.getRight()).inOrder() + " ";
            return myTree;
        }
    }

    private Node<E> getParentFor(Node<E> input) {
        if (root == input) {
            return null;
        } else {
            Node<E> curentNode = root;
            while (curentNode.hasAnyChild()) {
                if (input.getItem().equals(curentNode.getLeft().getItem())) {
                    return curentNode;
                } else {
                    curentNode = curentNode.getLeft();
                }
            }
        }
        return null;
    }

    public String inOrderNext(E previous) {
        if (FindItem(previous) == true) {
            return GetNode(previous).hasRight() ? GetNode(previous).getRight().toString() : getParentFor(GetNode(previous)) == null ? "null" : getParentFor(GetNode(previous)).toString();
        } else {
            return null;
        }
    }

    public boolean deleteItem(E item) {
        if (FindItem(item)) {   // To be sure that there is a parent
            String wantedNodeDirection = "";
            Node<E> parentForWantedNode = GetParentNode(item);
            Node<E> wantedNode = GetNode(item);
            if (parentForWantedNode != null && parentForWantedNode.hasLeft() && parentForWantedNode.getLeft() == wantedNode) {
                wantedNodeDirection = "L";
            } else if (parentForWantedNode != null && parentForWantedNode.hasRight() && parentForWantedNode.getRight() == wantedNode) {
                wantedNodeDirection = "R";
            } else { // if the node doesn't have any parent (the node = root)
                if (!root.hasAnyChild()) {  // Case 1
                    root = null;
                    return true;
                } else if (wantedNode.hasLeft() && !wantedNode.hasRight()) { // Case 2 Left
                    root = wantedNode.getLeft();
                    return true;
                } else if (wantedNode.hasRight() && !wantedNode.hasLeft()) {// Case 2 Right
                    root = wantedNode.getRight();
                    return true;
                } else {
                    if (!wantedNode.getLeft().hasRight()) {// Case 2 Left
                        wantedNode.getLeft().setRight(root.getRight());
                        root = wantedNode.getLeft();
                        return true;
                    } else if (!wantedNode.getRight().hasLeft()) {// Case 2 Right
                        wantedNode.getRight().setLeft(root.getLeft());
                        root = wantedNode.getRight();
                        return true;
                    } else {    // Case 3
                        Node searchedNode = wantedNode.getLeft().getRight();
                        if (!searchedNode.hasAnyChild()) {
                            searchedNode.setLeft(wantedNode.getLeft());
                            searchedNode.setRight(wantedNode.getRight());
                            wantedNode.getLeft().setRight(null);
                            root = searchedNode;
                            return true;
                        } else {    // Case 3
                            while (searchedNode.getChild().hasAnyChild()) {
                                searchedNode = searchedNode.getChild();
                            }
                            searchedNode.getChild().setLeft(root.getLeft());
                            searchedNode.getChild().setRight(root.getRight());
                            root = searchedNode.getChild();
                            if (searchedNode.getRight().equals(searchedNode.getChild())) {
                                searchedNode.setRight(null);
                            } else {
                                searchedNode.setLeft(null);
                            }
                            return true;
                        }
                    }
                }
            }
            if (!wantedNode.hasAnyChild()) {  // Case 1
                if (wantedNodeDirection.equals("L")) {
                    return parentForWantedNode.setLeft(null);
                } else {
                    return parentForWantedNode.setRight(null);
                }
            } else if (wantedNode.hasLeft() && !wantedNode.getLeft().hasRight()) { // Case 2
                if (wantedNodeDirection.equals("L")) {    // if the node parent left == the node
                    wantedNode.getLeft().setRight(wantedNode.getRight());
                    return parentForWantedNode.setLeft(wantedNode.getLeft());
                } else {    // if the node parent right == the node
                    wantedNode.getLeft().setRight(wantedNode.getRight());
                    return parentForWantedNode.setRight(wantedNode.getLeft());
                }
            } else if (wantedNode.hasRight() && !wantedNode.getRight().hasLeft()) { // Case 2
                if (wantedNodeDirection.equals("L")) {
                    wantedNode.getRight().setLeft(wantedNode.getLeft());
                    return parentForWantedNode.setLeft(wantedNode.getRight());  // remove the parent node left
                } else {
                    wantedNode.getRight().setLeft(wantedNode.getLeft());
                    return parentForWantedNode.setRight(wantedNode.getRight()); // remove the parent node right
                }
            } else { // Case 3
                Node searchedNode = wantedNode.getLeft().getRight();
                if (!searchedNode.hasAnyChild()) {
                    wantedNode.getLeft().setRight(null);
                    searchedNode.setLeft(wantedNode.getLeft());
                    searchedNode.setRight(wantedNode.getRight());
                    parentForWantedNode = searchedNode;
                } else {    // Case 3
                    while (searchedNode.getChild().hasAnyChild()) {
                        searchedNode = searchedNode.getChild();
                    }
                    searchedNode.getChild().setLeft(wantedNode.getLeft());
                    searchedNode.getChild().setRight(wantedNode.getRight());
                    wantedNode = searchedNode.getChild();
                    if (searchedNode.getRight().equals(searchedNode.getChild())) {
                        searchedNode.setRight(null);
                    } else {
                        searchedNode.setLeft(null);
                    }
                    return true;
                }
                if (wantedNodeDirection.equals("L")) {    // if the node parent left == the node
                    return parentForWantedNode.setLeft(searchedNode);   // remove the parent node left
                } else {// if the node parent right == the node
                    return parentForWantedNode.setRight(searchedNode);  // remove the parent node right
                }
            }
        } else { // we didn't find the node to remove
            return false;
        }
    }

    /**
     *
     * @param root the node which we want to print tree from
     * @param s the String that will be between tree nodes
     * @return all the tree as String
     */
    public String getTreeAsString(Node root, String s) {
        if (root == null) {
            return "";
        } else {
            String Tree = "";
            Tree += root.toString() + "\n";
            if (root.hasLeft() && root.hasRight()) {
                Tree += s + "│\n";
                Tree += s + ("├─L─ " + getTreeAsString(root.getLeft(), s + "  "));
                Tree += s + "│\n";
                Tree += s + ("└─R─ " + getTreeAsString(root.getRight(), s + "  "));
            } else if (root.hasLeft()) {
                Tree += s + ("└─L─ " + getTreeAsString(root.getLeft(), s + "  "));
            } else if (root.hasRight()) {
                Tree += s + ("└─R─ " + getTreeAsString(root.getRight(), s + "  "));
            }

            return Tree;

        }
    }

    @Override
    public String toString() {
        return getTreeAsString(root, " ");
    }

    public boolean addItem(E item) {
        if (root == null) {
            root = new Node<>(item);
        } else {
            Node<E> curentNode = root;
            boolean placeFound = false;
            while (!placeFound) {
                if (item.compareTo(curentNode.getItem()) > 0) {
                    if (!curentNode.hasRight()) {
                        return curentNode.setRight(new Node<>(item));
                    } else {
                        curentNode = curentNode.getRight();
                    }
                } else {
                    if (!curentNode.hasLeft()) {
                        return curentNode.setLeft(new Node<>(item));
                    } else {
                        curentNode = curentNode.getLeft();
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param item
     * @return true if the item found in the tree or false if the item not found
     * in the tree
     */
    public boolean FindItem(E item) {
        if (root == null) {
            return false;
        } else if (root.getItem() != item) {
            Node<E> curentNode = root;
            boolean placeFound = false;
            while (!placeFound) {
                if (item.compareTo(curentNode.getItem()) > 0) {
                    if (curentNode.hasRight()) {
                        if (curentNode.getRight().getItem() == item) {
                            return true;
                        } else {
                            curentNode = curentNode.getRight();
                        }
                    } else {
                        return placeFound;
                    }
                } else {
                    if (curentNode.hasLeft()) {
                        if (curentNode.getLeft().getItem() == item) {
                            return true;
                        } else {
                            curentNode = curentNode.getLeft();
                        }
                    } else {
                        return placeFound;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private Node<E> GetNode(E item) {
        if (root == null) {
            return null;
        } else {
            Node<E> curentNode = root;
            boolean placeFound = false;
            while (!placeFound) {
                if (item.compareTo(curentNode.getItem()) > 0) {
                    if (curentNode.hasRight()) {
                        if (curentNode.getRight().getItem() == item) {
                            return curentNode.getRight();
                        } else {
                            curentNode = curentNode.getRight();
                        }
                    } else {
                        return null;
                    }
                } else if (item.compareTo(curentNode.getItem()) < 0) {
                    if (curentNode.hasLeft()) {
                        if (curentNode.getLeft().getItem() == item) {
                            return curentNode.getLeft();
                        } else {
                            curentNode = curentNode.getLeft();
                        }
                    } else {
                        return null;
                    }
                } else {
                    return curentNode;
                }

            }
        }
        return null;
    }

    private Node<E> GetParentNode(E item) {
        if (root == null) {
            return null;
        } else {
            Node<E> curentNode = root;
            boolean placeFound = false;
            while (!placeFound) {
                if (item.compareTo(curentNode.getItem()) > 0) {
                    if (curentNode.hasRight()) {
                        if (curentNode.getRight().getItem() == item) {
                            return curentNode;
                        } else {
                            curentNode = curentNode.getRight();
                        }
                    } else {
                        return null;
                    }
                } else {
                    if (curentNode.hasLeft()) {
                        if (curentNode.getLeft().getItem() == item) {
                            return curentNode;
                        } else {
                            curentNode = curentNode.getLeft();
                        }
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public String getItemPlace(E item) {
        if (root == null) {
            return null;
        } else {
            String place = "";
            Node<E> curentNode = root;
            boolean placeFound = false;
            while (!placeFound) {
                place += curentNode.toString();
                if (item.compareTo(curentNode.getItem()) > 0) {
                    if (curentNode.hasRight()) {
                        if (curentNode.getRight().getItem() == item) {
                            place += " - R > ";
                            place += curentNode.getRight().toString();
                            placeFound = true;
                        } else {
                            place += " - R > ";
                            curentNode = curentNode.getRight();

                        }
                    }
                } else {
                    if (curentNode.hasLeft()) {
                        if (curentNode.getLeft().getItem() == item) {
                            place += " - L > ";
                            placeFound = true;
                            place += curentNode.getLeft().toString();
                        } else {
                            place += " - L > ";
                            curentNode = curentNode.getLeft();
                        }
                    } else {
                        break;
                    }
                }
            }
            if (placeFound) {
                return place;
            } else {
                return "Place not found";
            }
        }
    }

    public Node<E> getRoot() {
        return root;

    }

    class Node<E extends Comparable<E>> {

        private final E item;
        private Node<E> left;
        private Node<E> right;

        Node(E item) {
            this.item = item;
        }

        public boolean setLeft(Node<E> left) {
            this.left = left;
            return true;
        }

        public boolean setRight(Node<E> right) {
            this.right = right;
            return true;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        @Override
        public String toString() {
            return getItem().toString();
        }

        /**
         * @return the item
         */
        public E getItem() {
            return (E) item;
        }

        public String getAllTreeString() {
            String allTree = "";
            allTree += this.toString();
            if (this.right != null || this.left != null) {
                allTree += "{";
                if (this.right != null) {
                    allTree += "R(" + right.getAllTreeString() + ")";
                }
                if (this.left != null) {
                    allTree += "L(" + left.getAllTreeString() + ")";
                }
                return allTree + "}";
            } else {
                return allTree;
            }
        }

        public boolean hasLeft() {
            return left != null;
        }

        public boolean hasRight() {
            return right != null;
        }

        public boolean hasAnyChild() {
            return hasRight() || hasLeft();
        }

        public Node<E> getChild() {
            if (hasRight()) {
                return this.getRight();
            } else if (hasLeft()) {
                return this.getLeft();
            } else {
                return null;
            }
        }
    }
}
