package ds212;

public class AVLTree<K extends Comparable<K>, T> {
    class AVLNode<K extends Comparable<K>, T> {
        K key;
        T data;
        AVLNode<K, T> left, right, parent;
        int balanceFactor;

        public AVLNode(K key, T data) {
            this.key = key;
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.balanceFactor = 0; }
    }

    private AVLNode<K, T> root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;}

    public boolean isEmpty() { return root == null;}

   public int size() { return size; }

    public void clear() {
        root = null;
        size = 0; }

    public boolean insert(K key, T data) {
        AVLNode<K, T> newNode = new AVLNode<>(key, data);
        if (root == null) {
            root = newNode;
            size++;
            return true;
        }
        root = insert(root, newNode);
        size++;
        return true;
    }

    private AVLNode<K, T> insert(AVLNode<K, T> current, AVLNode<K, T> newNode) {
        if (current == null) {
            return newNode; }
            
        if (newNode.key.compareTo(current.key) < 0) {
            current.left = insert(current.left, newNode);
            current.left.parent = current;
            } 
        else if (newNode.key.compareTo(current.key) > 0) {
               current.right = insert(current.right, newNode);
               current.right.parent = current; } 
             else {
            current.data = newNode.data; 
            size--;
            return current; }
            
        return rebalance(current);
        }

    public boolean delete(K key) {
        if (find(key)) {
            root = delete(root, key);
            size--;
            return true;
        }
        return false;
    }

    private AVLNode<K, T> delete(AVLNode<K, T> node, K key) {
        if (node == null) return null;

        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else 
            if (key.compareTo(node.key) > 0) {
               node.right = delete(node.right, key);}
            else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            }
            else {
                AVLNode<K, T> minNode = findMin(node.right);
                node.key = minNode.key;
                node.data = minNode.data;
                node.right = delete(node.right, minNode.key);
               }
            }
        if (node != null) {
            return rebalance(node);
        }
        return null;
    }

    private AVLNode<K, T> rebalance(AVLNode<K, T> node) {
        updateBalanceFactor(node);

        if (node.balanceFactor < -1) { 
            if (balanceFactor(node.left) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (node.balanceFactor > 1) {
            if (balanceFactor(node.right) < 0) {
                node.right = rotateRight(node.right);}
            return rotateLeft(node);
           }
        return node;
    }

    private void updateBalanceFactor(AVLNode<K, T> node) {
        node.balanceFactor = balanceFactor(node.right) - balanceFactor(node.left);
    }

    private int balanceFactor(AVLNode<K, T> node) {
        if (node == null) return 0;
        return height(node.right) - height(node.left);
    }

    private int height(AVLNode<K, T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private AVLNode<K, T> rotateLeft(AVLNode<K, T> node) {
        AVLNode<K, T> newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) {
            newRoot.left.parent = node; }
            
        newRoot.left = node;
        newRoot.parent = node.parent;
        node.parent = newRoot;

        updateBalanceFactor(node);
        updateBalanceFactor(newRoot);
        return newRoot;
    }

    private AVLNode<K, T> rotateRight(AVLNode<K, T> node) {
        AVLNode<K, T> newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) {
            newRoot.right.parent = node;
        }
        newRoot.right = node;
        newRoot.parent = node.parent;
        node.parent = newRoot;

        updateBalanceFactor(node);
        updateBalanceFactor(newRoot);

        return newRoot;
    }

    private AVLNode<K, T> findMin(AVLNode<K, T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean find(K key) {
        return search(root, key) != null;
    }

    public T retrieve(K key) {
        AVLNode<K, T> node = search(root, key);
        return (node != null) ? node.data : null;
    }

    private AVLNode<K, T> search(AVLNode<K, T> node, K key) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) return search(node.left, key);
        if (key.compareTo(node.key) > 0) return search(node.right, key);
        return node;
    }

    public void traverse() {
        inorderTraversal(root);
        System.out.println();
    }

    private void inorderTraversal(AVLNode<K, T> node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.key + " ");
            inorderTraversal(node.right);
        }
    }
}
