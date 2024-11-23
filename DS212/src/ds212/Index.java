package ds212;

public class Index {
	LinkedList<Document> all_doc;

	public Index() {
		all_doc = new LinkedList<Document>();
	}

	/// helping methods//
	public void add_document(Document d) {
		all_doc.insert(d);
	}

	public void displayDocuments() {
		if (all_doc == null) {
			System.out.println("null docs");
			return;
		} else if (all_doc.empty()) {
			System.out.println("empty docs");
			return;
		}

		all_doc.findFirst();
		while (!all_doc.last()) {
			Document doc = all_doc.retrieve();
			System.out.println("\n ------------------------");
			System.out.println("ID:" + doc.id);
			doc.words.display();
			all_doc.findNext();
		}

		Document doc = all_doc.retrieve();
		System.out.println("\n ------------------------");
		System.out.println("ID:" + doc.id);
		doc.words.display();
	}

	public void findAndDisplayDoc(int id) {
		all_doc.findFirst();
		while (!all_doc.last()) {
			Document doc = all_doc.retrieve();
			if (doc.id == id) {
				System.out.print(id);
				return;
			}
			all_doc.findNext();
		}
	}

//    Method to retrun document given the id, used in class Ranked
	public Document getAllDocGivenID(int id) {
		if (all_doc.empty()) {
			System.out.println("No document exisit");
			return null;
		}

		all_doc.findFirst();
		while (!all_doc.last()) {
			if (all_doc.retrieve().id == id) {
				System.out.print(id);
				return all_doc.retrieve();
			}
			all_doc.findNext();
		}
		if (all_doc.retrieve().id == id) {
			System.out.print(id);
			return all_doc.retrieve();
		}
		return null; // if not found
	}

	public LinkedList<Integer> getAllDocGivenTerm(String term) {
		LinkedList<Integer> result = new LinkedList<>();

		// Check if all_doc is empty, if so, return an empty list
		if (all_doc.empty()) {
			System.out.println("No documents found.");
			return result; // Return an empty list instead of null
		}

		all_doc.findFirst();
		while (!all_doc.last()) {
			if (all_doc.retrieve().words.exist(term.toLowerCase().trim())) {
				result.insert(all_doc.retrieve().id);
			}
			all_doc.findNext();
		}

		// Check the last document
		if (all_doc.retrieve().words.exist(term.toLowerCase().trim())) {
			result.insert(all_doc.retrieve().id);
		}

		return result; // Return the list (it will be empty if no results were found)
	}

}