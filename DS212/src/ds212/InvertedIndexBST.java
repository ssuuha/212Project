package ds212;

//inverted by binary search tree
public class InvertedIndexBST {
	BSTree<Word> inverted_index;

	public InvertedIndexBST() {
		inverted_index = new BSTree<Word>();
	}

	public void add(String text, int id) {
		// If the word isn't found
		if (!searchWordInInverted(text)) {
			Word word = new Word(text);
			word.doc_IDS.insert(id);
			inverted_index.insert(text, word);
		} else {
			Word existingWord = inverted_index.retrieve();
			existingWord.add_Id(id);
		}
	}

	public void add_from_invertedList(InvertedIndex inverted) {
		if (inverted.inverted_index.empty())
			return;

		inverted.inverted_index.findFirst();
		while (!inverted.inverted_index.last()) {
			inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());

			inverted.inverted_index.findNext();
		}

		inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());
	}

	public boolean searchWordInInverted(String word) {
		return inverted_index.findKey(word);
	}

	public void display_inverted_index_BST() {
		if (inverted_index == null) {
			System.out.println("Null inverted index");
			return;
		} else if (inverted_index.empty()) {
			System.out.println("Empty inverted index");
			return;
		}
		inverted_index.inOrder();
	}

}
