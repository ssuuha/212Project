package ds212;

public class Index {
	LinkedList<Document> alldoc;

	public Index() {
		alldoc = new LinkedList<Document>();
	}

	public void add_document(Document d) {
		alldoc.insert(d);
	}

	public void displayDocuments() {
		if (alldoc == null) {
			System.out.println("null docs");
			return;
		} else if (alldoc.empty()) {
			System.out.println("empty docs");
			return;
		}

		alldoc.findFirst();
		while (!alldoc.last()) {
			Document doc = alldoc.retrieve();
			System.out.println("\n ------------------------");
			System.out.println("ID:" + doc.id);
			doc.words.display();
			alldoc.findNext();
		}

		Document doc = alldoc.retrieve();
		System.out.println("\n ------------------------");
		System.out.println("ID:" + doc.id);
		doc.words.display();
	}

	public void findAndDisplayDoc(int id) {
		alldoc.findFirst();
		while (!alldoc.last()) {
			Document doc = alldoc.retrieve();
			if (doc.id == id) {
				System.out.print(id);
				return;
			}
			alldoc.findNext();
		}
	}

	public Document getAllDocGivenID(int id) {
		if (alldoc.empty()) {
			System.out.println("No document exisit");
			return null;
		}

		alldoc.findFirst();
		while (!alldoc.last()) {
			if (alldoc.retrieve().id == id) {
				System.out.print(id);
				return alldoc.retrieve();
			}
			alldoc.findNext();
		}
		if (alldoc.retrieve().id == id) {
			System.out.print(id);
			return alldoc.retrieve();
		}
		return null;
	}

	public LinkedList<Integer> getAllDocGivenTerm(String term) {
		LinkedList<Integer> result = new LinkedList<>();

		if (alldoc.empty()) {
			System.out.println("No documents found.");
			return result;
		}

		alldoc.findFirst();
		while (!alldoc.last()) {
			if (alldoc.retrieve().words.exist(term.toLowerCase().trim())) {
				result.insert(alldoc.retrieve().id);
			}
			alldoc.findNext();
		}
		if (alldoc.retrieve().words.exist(term.toLowerCase().trim())) {
			result.insert(alldoc.retrieve().id);
		}

		return result;
	}
}