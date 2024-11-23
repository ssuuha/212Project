package ds212;

//mainly for inverted

public class Word {
	String text;
	LinkedList<Integer> doc_IDS;

	public Word(String W) {
		text = W;
		doc_IDS = new LinkedList<Integer>();

	}

	public void add_Id(int id) {
		if (!existsIn_doc_IDS(id))
			doc_IDS.insert(id);

	}

	public boolean existsIn_doc_IDS(Integer id) {
		if (doc_IDS.empty())
			return false;

		doc_IDS.findFirst();
		while (!doc_IDS.last()) {
			if (doc_IDS.retrieve().equals(id)) {
				return true;
			}
			doc_IDS.findNext();
		}

		if (doc_IDS.retrieve().equals(id)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// Format Word object for meaningful output
		return "";
	}

	public void display() {
		System.out.println("\n--------------------------");
		System.out.println("Word: " + text);
		System.out.print("Document IDs: [");

		// Display doc_IDS contents
		doc_IDS.findFirst();
		while (!doc_IDS.last()) {
			System.out.print(doc_IDS.retrieve() + ", ");
			doc_IDS.findNext();
		}
		System.out.print(doc_IDS.retrieve()); // Last ID without a trailing comma
		System.out.println("]");
	}

}
