package ds212;

class BSTNode<T> {
	public T data;
	public String key;
	public BSTNode<T> left, right;

	public BSTNode(String key, T data) {
		this.data = data;
		this.key = key;
		left = right = null;
	}
}

public class BSTree<T> {
	private BSTNode<T> root, current;

	public BSTree() {
		current = root = null;
	}

	public boolean empty() {
		return root == null;
	}

	public boolean full() {
		return false;
	}

	public T retrieve() {
		return current.data;
	}

	public boolean findKey(String k) {
		BSTNode<T> p = root;
		while (p != null) {
			current = p;
			if (k.compareToIgnoreCase(p.key) == 0)
				return true;
			else if (k.compareToIgnoreCase(p.key) < 0)
				p = p.left;
			else
				p = p.right;
		}
		return false;
	}

	public boolean insert(String k, T val) {
		if (root == null) {
			current = root = new BSTNode<T>(k, val);
			return true;
		}
		BSTNode<T> p = current;
		if (findKey(k)) {
			current = p;
			return false;
		}
		BSTNode<T> tmp = new BSTNode<T>(k, val);
		if (k.compareToIgnoreCase(current.key) < 0)
			current.left = tmp;
		else
			current.right = tmp;
		current = tmp;
		return true;
	}

	public void inOrder() {
		if (root == null)
			System.out.println("Empty tree");
		else
			inOrder(root);
	}

	private void inOrder(BSTNode p) {
		if (p == null)
			return;
		inOrder(p.left);
		System.out.println("Key= " + p.key);
		System.out.println(p.data);
		// ((LinkedList<T>)p.data). display(); //When dealign with linkedlist
		((Word) p.data).display();
		inOrder(p.right);
	}

	public void preOrder() {
		if (root == null)
			System.out.println("Empty tree");
		else
			preOrder(root);
	}

	public void preOrder(BSTNode p) {
		if (p == null)
			return;
		System.out.println("Key= " + p.key);
		System.out.println(p.data.toString());
		preOrder(p.left);
		preOrder(p.right);
	}

}