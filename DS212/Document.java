package ds212;

public class Document {
	LinkedList<String> words = new LinkedList<>();
	int id;
	String content;

	public Document(int id, LinkedList<String> words, String content) {
		this.id = id;
		this.words = words;
		this.content = content;
	}

	public int getId() {
		return id;
	}

}
