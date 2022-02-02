import org.w3c.dom.ls.LSOutput;

import java.util.*;
public class Tree<E extends Comparable<? super E>> {
    private BinaryTreeNode root;  // Root of tree
    private String name;     // Name of tree
    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        name = label;
    }
    /**
     * Create BST from ArrayList
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }
    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }
    /**
     * Return a string containing the tree contents as a tree with one node per 
     line
     */
    public void toStringHelper(BinaryTreeNode node, StringBuilder sb, int counter) {
        if (node == null) return;
        counter++;
        toStringHelper(node.right, sb, counter);
        String tab = new String(new char[counter]).replace("\0", "  ");
        String noParent = new String("no parent");
        if (node.parent != null) {
            noParent = node.parent.key.toString();
        }
        sb.append(tab + node.key + " [" + noParent + "]" + "\n");
        toStringHelper(node.left, sb, counter);
    }

    public String toString() {
        if (this.root == null) {
            return "Empty Tree";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + "\n");
        toStringHelper(this.root, sb, -1);
        return sb.toString();
    }
    /**
     * Return a string containing the tree contents as a single line
     */
    public void inOrderToStringHelper(BinaryTreeNode node, StringBuilder sb) {
        if (node == null) return;
        inOrderToStringHelper(node.left, sb);
        String noParent = new String("no parent");
        if (node.parent != null) {
            noParent = node.parent.key.toString();
        }
        sb.append(node.key + "  ");
        inOrderToStringHelper(node.right, sb);
    }

    public String inOrderToString() {
        if (this.root == null) {
            return "Empty Tree";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + ": ");
        inOrderToStringHelper(this.root, sb);
        return sb.toString();
    }
    /**
     * reverse left and right children recursively
     */
    public void flipHelper(BinaryTreeNode node) {
        if (node == null) return;
        var holderNode = node.left;
        node.left = node.right;
        node.right = holderNode;
        flipHelper(node.left);
        flipHelper(node.right);
    }

    public void flip() {
        flipHelper(this.root);
    }
    /**
     * Returns the in-order successor of the specified node
     * @param node node from which to find the in-order successor
     */
    public boolean inOrderSuccessorCheckNoSuccessor(BinaryTreeNode node, BinaryTreeNode child) {
        if (node == null) return true;
        if (node.key.compareTo(child.key) < 0)
           return inOrderSuccessorCheckNoSuccessor(node.parent, node.right);
        return false;
    }

    public BinaryTreeNode inOrderSuccessorDownHelper(BinaryTreeNode node) {
        if (node.left == null) return node;
        return inOrderSuccessorDownHelper(node.left);
    }

    public BinaryTreeNode inOrderSuccessorUpHelper(BinaryTreeNode node, BinaryTreeNode child) {
        if (node.left == child) return node;
        return inOrderSuccessorUpHelper(node.parent, node);
    }

    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        if (node.right != null) {
            return inOrderSuccessorDownHelper(node.right);
        }
        if (node.left == null) {
            if (inOrderSuccessorCheckNoSuccessor(node.parent, node)) return null;
        }
        if (node.parent == null) return null;
        if (node.parent.right != node) {
            return node.parent;
        } else {
            return inOrderSuccessorUpHelper(node.parent, node);
        }
    }
    /**
     * Counts number of nodes in specified level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevelHelper(BinaryTreeNode node, int level, int counter) {
        if (node == null) return 0;
        if (level == counter) return 1;
        return nodesInLevelHelper(node.right, level, counter + 1) + nodesInLevelHelper(node.left, level, counter + 1);
    }

    public int nodesInLevel(int level) {
        return nodesInLevelHelper(this.root, level, 0);
    }
    /**
     * Print all paths from root to leaves
     */

    public void printAllPathsHelper(BinaryTreeNode node, ArrayList<E> intList) {
        if (node == null) return;
        intList.add(node.key);
        if (node.left == null && node.right == null) {
            String list = Arrays.toString(intList.toArray()).replace("[", "")
                    .replace("]", "").replace(",", "");
            System.out.println(list);
        }
        printAllPathsHelper(node.left, intList);
        printAllPathsHelper(node.right, intList);
        intList.remove(node.key);
    }

    public void printAllPaths() {
        ArrayList<E> intList = new ArrayList<>();
        printAllPathsHelper(this.root, intList);
    }
    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */

    public int countBSTHelper(BinaryTreeNode node) {
        if (node == null) return 0;
        int tempInt = countBSTHelper(node.left) + countBSTHelper(node.right);
        if (node.left == null && node.right == null) tempInt += 1;
        if (node.left != null) {
            if (node.left.key.compareTo(node.key) < 0) tempInt += 1;
        } else if (node.right != null) {
            if (node.right.key.compareTo(node.key) > 0) tempInt += 1;
        }
        return tempInt;
    }

    public int countBST() {
        return countBSTHelper(this.root);
    }
    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void insert(E x) {
        root = insert(x, root, null);
    }

    public BinaryTreeNode getByKeyHelper(BinaryTreeNode node, E key) {
        if (node == null) return null;
        if (node.key == key) return node;
        var leftNode = getByKeyHelper(node.left, key);
        var rightNode = getByKeyHelper(node.right, key);
        if (leftNode != null) return leftNode;
        if (rightNode != null) return rightNode;
        return null;
    }

    public BinaryTreeNode getByKey(E key) {
        return getByKeyHelper(this.root, key);
    }
    /**
     * Balance the tree
     */
    public void balanceTreeHelperInsert(ArrayList<E> list, int high, int low) {
        if (low > high) return;
        int mid = low + (high - low) / 2;
        insert(list.get(mid));
        balanceTreeHelperInsert(list, mid - 1, low);
        balanceTreeHelperInsert(list, high, mid + 1);
    }

    public ArrayList<E> balanceTreeHelperList(BinaryTreeNode node, ArrayList<E> list) {
        if (node == null) return null;
        balanceTreeHelperList(node.left, list);
        list.add(node.key);
        balanceTreeHelperList(node.right, list);
        return list;
    }

    public void balanceTree() {
        ArrayList<E> list = new ArrayList<>();
        list = balanceTreeHelperList(this.root, list);
        this.root = null;
        balanceTreeHelperInsert(list, list.size() - 1, 0);
    }
    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode insert(E x, BinaryTreeNode t, BinaryTreeNode parent) {
        if (t == null) return new BinaryTreeNode(x, null, null, parent);
        int compareResult = x.compareTo(t.key);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else {
            t.right = insert(x, t.right, t);
        }
        return t;
    }
    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is 
     executed and the work
     * associated with a single call is independent of the size of the tree: a=1, 
     b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryTreeNode t) {
        if (t == null)
            return false;
        int compareResult = x.compareTo(t.key);
        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }
    // Basic node stored in unbalanced binary trees
    public class BinaryTreeNode {
        E key;            // The data/key for the node
        BinaryTreeNode left;   // Left child
        BinaryTreeNode right;  // Right child
        BinaryTreeNode parent; //  Parent node
        // Constructors
        BinaryTreeNode(E theElement) {
            this(theElement, null, null, null);
        }
        BinaryTreeNode(E theElement, BinaryTreeNode lt, BinaryTreeNode rt,
                       BinaryTreeNode pt) {
            key = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(key);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.key);
                sb.append(">");
            }
            return sb.toString();
        }
    }
}