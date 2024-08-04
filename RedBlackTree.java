public class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T data;
        Node left, right, parent;
        boolean color;

        Node(T data) {
            this.data = data;
            color = RED;
        }
    }

    private Node root;

    public void insert(T data) {
        Node newNode = new Node(data);
        root = insertRec(root, newNode);
        fixInsert(newNode);
    }

    private Node insertRec(Node root, Node newNode) {
        if (root == null) {
            return newNode;
        }
        if (newNode.data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, newNode);
            root.left.parent = root;
        } else if (newNode.data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, newNode);
            root.right.parent = root;
        }
        return root;
    }

    private void fixInsert(Node node) {
        Node parent, grandParent;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateRight(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            } else {
                Node uncle = grandParent.left;
                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateLeft(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        Node rightNode = node.right;
        node.right = rightNode.left;
        if (node.right != null) {
            node.right.parent = node;
        }
        rightNode.parent = node.parent;
        if (node.parent == null) {
            root = rightNode;
        } else if (node == node.parent.left) {
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }
        rightNode.left = node;
        node.parent = rightNode;
    }

    private void rotateRight(Node node) {
        Node leftNode = node.left;
        node.left = leftNode.right;
        if (node.left != null) {
            node.left.parent = node;
        }
        leftNode.parent = node.parent;
        if (node.parent == null) {
            root = leftNode;
        } else if (node == node.parent.left) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }
        leftNode.right = node;
        node.parent = leftNode;
    }

    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        int[] values = {20, 15, 25, 10, 5, 1};

        for (int value : values) {
            tree.insert(value);
        }

        tree.inorder();
    }
}
