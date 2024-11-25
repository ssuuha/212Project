package ds212;

public class InvertedIndex {
	LinkedList<Word> inverted_index;

	public InvertedIndex() {
		inverted_index = new LinkedList<Word>();
	}

	public void addinv(String text, int id) {
		
		if (!searchWordInInverted(text)) {
			Word word = new Word(text);
			word.doc_IDS.insert(id);
			inverted_index.insert(word);
		} else {
			Word existingWord = inverted_index.retrieve();
			existingWord.add_Id(id);
		}
	}

	public boolean searchWordInInverted(String word) {
		if (inverted_index == null || inverted_index.empty())
			return false;
		inverted_index.findFirst();
		while (!inverted_index.last()) {
			if (inverted_index.retrieve().text.equals(word))
				return true;
			inverted_index.findNext();
		}
		if (inverted_index.retrieve().equals(word)) // For the last word
			return true;
		return false;
	}

	public void displayInvertedIndex() {
		if (inverted_index == null) {
			System.out.println("Null inverted index");
			return;
		} else if (inverted_index.empty()) {
			System.out.println("Empty inverted index");
			return;
		}
		inverted_index.findFirst();
		while (!inverted_index.last()) {
			inverted_index.retrieve().display();
			inverted_index.findNext();
		}
				inverted_index.retrieve().display();

	}

	public Word retrieve(String term) {
		inverted_index.findFirst();
		while (!inverted_index.last()) {
			if (inverted_index.retrieve().text.equals(term)) {
				return inverted_index.retrieve();
			}
			inverted_index.findNext();
		}
		if (inverted_index.retrieve().text.equals(term)) {
			return inverted_index.retrieve();
		}
		return null;
	}

}