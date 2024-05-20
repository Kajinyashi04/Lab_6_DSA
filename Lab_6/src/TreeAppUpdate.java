import java.util.Stack;

import java.util.Scanner;

class TreeApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int value;
        Tree theTree = new Tree();

        theTree.insert(50, 1.5);
        theTree.insert(25, 1.2);
        theTree.insert(75, 1.7);
        theTree.insert(12, 1.5);
        theTree.insert(37, 1.2);
        theTree.insert(43, 1.7);
        theTree.insert(30, 1.5);
        theTree.insert(33, 1.2);
        theTree.insert(87, 1.7);
        theTree.insert(93, 1.5);
        theTree.insert(97, 1.5);

        while (true) {
            System.out.print("\nEnter first letter of show, ");
            System.out.print("insert, find, delete, traverse, height, count, leaves, balanced, identical, or quit: ");
            char choice = getChar();
            switch (choice) {
                case 's':
                    System.out.print("horizontal or vertical (1 or 2)? ");
                    int value1 = getInt();
                    if (value1 == 1) {
                        System.out.println();
                        showTree(0, theTree.root);
                    } else
                        theTree.displayTree();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    theTree.insert(value, value + 0.9);
                    System.out.println("Comparisons = " + theTree.comps);
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getInt();
                    Node found = theTree.find(value);
                    if (found != null) {
                        System.out.print("Found: ");
                        found.displayNode();
                        System.out.print("\n");
                    } else {
                        System.out.print("Could not find ");
                        System.out.println(value);
                    }
                    System.out.println("Comparisons = " + theTree.comps);
                    break;
                case 'd':
                    System.out.print("Enter value to delete: ");
                    value = getInt();
                    boolean didDelete = theTree.delete(value);
                    if (didDelete)
                        System.out.print("Deleted " + value + '\n');
                    else {
                        System.out.print("Could not delete ");
                        System.out.println(value);
                    }
                    System.out.println("Comparisons = " + theTree.comps);
                    break;
                case 't':
                    System.out.print("Enter type 1, 2 or 3: ");
                    value = getInt();
                    theTree.traverse(value);
                    break;
                case 'h':
                    int treeHeight = theTree.height();
                    System.out.println("Height of the tree: " + treeHeight);
                    break;
                case 'c':
                    int numElements = theTree.countElements();
                    System.out.println("Number of elements in the tree: " + numElements);
                    break;
                case 'l':
                    int numLeaves = theTree.countLeaves();
                    System.out.println("Number of leaves in the tree: " + numLeaves);
                    break;
                case 'b':
                    boolean isBalanced = theTree.isBalanced();
                    System.out.println("Is the tree fully balanced? " + isBalanced);
                    break;
                // Populate otherTree as needed
                case 'q':
                    return;
                default:
                    System.out.print("Invalid entry\n");
            }  // end switch
        }  // end while
    }  // end main()

    public static String getString() {
        return scanner.nextLine();
    }

    public static char getChar() {
        String s = getString();
        return s.charAt(0);
    }

    public static int getInt() {
        return scanner.nextInt();
    }

    public static Node node(int data, Node l, Node r) {
        Node a = new Node();
        a.iData = data;
        a.leftChild = l;
        a.rightChild = r;
        return a;
    }

    public static void showTree(int n, Node t) {
        tab(n);
        if (t == null)
            System.out.println("*");
        else {
            n = n + 3;
            System.out.println(t.iData);
            if (t.leftChild == null && t.rightChild == null) return;
            showTree(n, t.leftChild);
            showTree(n, t.rightChild);
        }
    }

    public static void tab(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }
}


class Node {
    public int iData;              // data item (key)
    public double dData;           // data item
    public Node leftChild;         // this node's left child
    public Node rightChild;        // this node's right child

    public void displayNode()      // display ourself
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
}

class Tree {
    int comps = 0;
    Node root;             // first node of tree

    public Tree()                  // constructor
    {
        root = null;
    }            // no nodes in tree yet

    public Node find(int key)      // find node with given key
    {
        comps = 0;                         // (assumes non-empty tree)
        Node current = root;               // start at root
        while (current.iData != key)        // while no match,
        {
            comps++;
            if (key < current.iData)         // go left?
                current = current.leftChild;
            else                            // or go right?
                current = current.rightChild;
            if (current == null)             // if no child,
                return null;                 // didn't find it
        }
        return current;                    // found it
    }  // end find()

    public void insert(int id, double dd) {
        comps = 0;
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        newNode.dData = dd;
        if (root == null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node current = root;       // start at root
            Node parent;
            while (true)                // (exits internally)
            {
                parent = current;
                comps++;
                if (id < current.iData)  // go left?
                {
                    current = current.leftChild;
                    if (current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if (current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()

    public boolean delete(int key) // delete node with given key
    {
        comps = 0;                           // (assumes non-empty list)
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key)        // search for node
        {
            comps++;
            parent = current;
            if (key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            } else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete

        // if no children, simply delete it
        if (current.leftChild == null &&
                current.rightChild == null) {
            if (current == root)             // if root,
                root = null;                 // tree is empty
            else if (isLeftChild)
                parent.leftChild = null;     // disconnect
            else                            // from parent
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)
        return true;                                // success
    }  // end delete()

    // returns node with next-highest value after delNode
    // goes to right child, then right child's left descendents
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;   // go to right child
        while (current != null)               // until no more
        {                                 // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // if successor not
        if (successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal:  ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    public void displayTree() {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');

            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if (temp.leftChild != null ||
                            temp.rightChild != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            }  // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()

    // Method to count the elements in the tree
    public int countElements() {
        return countNodes(root);
    }

    // Helper method to recursively count nodes in the tree
    private int countNodes(Node node) {
        if (node == null)
            return 0;
        return 1 + countNodes(node.leftChild) + countNodes(node.rightChild);
    }

    // Method to calculate the height of the tree
    public int height() {
        return calculateHeight(root);
    }

    // Helper method to recursively calculate height of the tree
    private int calculateHeight(Node node) {
        if (node == null)
            return -1; // Height of an empty tree is -1

        int leftHeight = calculateHeight(node.leftChild);
        int rightHeight = calculateHeight(node.rightChild);

        // Height of the tree is the maximum of the heights of its left and right subtrees
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Method to count the leaves in the tree
    public int countLeaves() {
        return countLeavesHelper(root);
    }

    // Helper method to recursively count leaves in the tree
    private int countLeavesHelper(Node node) {
        if (node == null)
            return 0;

        if (node.leftChild == null && node.rightChild == null)
            return 1;

        return countLeavesHelper(node.leftChild) + countLeavesHelper(node.rightChild);
    }

    // Method to check if the tree is fully balanced
    public boolean isBalanced() {
        return checkBalanced(root);
    }

    // Helper method to recursively check if the tree is fully balanced
    private boolean checkBalanced(Node node) {
        if (node == null)
            return true;

        int leftHeight = calculateHeight(node.leftChild);
        int rightHeight = calculateHeight(node.rightChild);

        // If the difference between heights of left and right subtrees is more than 1, the tree is not fully balanced
        if (Math.abs(leftHeight - rightHeight) > 1)
            return false;

        // Recursively check if left and right subtrees are fully balanced
        return checkBalanced(node.leftChild) && checkBalanced(node.rightChild);
    }

    // Method to check if two trees are identical
    public boolean areIdentical(Tree otherTree) {
        return areIdenticalTrees(root, otherTree.root);
    }

    // Helper method to recursively check if two trees are identical
    private boolean areIdenticalTrees(Node node1, Node node2) {
        // If both nodes are null, they are identical
        if (node1 == null && node2 == null)
            return true;

        // If one node is null and the other is not, they are not identical
        if (node1 == null || node2 == null)
            return false;

        // Check if data of current nodes are equal and recursively check their left and right subtrees
        return (node1.iData == node2.iData) &&
                areIdenticalTrees(node1.leftChild, node2.leftChild) &&
                areIdenticalTrees(node1.rightChild, node2.rightChild);
    }
}
