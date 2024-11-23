package ds212;

//
class Doc_Rank {
	int id;
	int rank;

	public Doc_Rank(int id, int rank) {
		this.id = id;
		this.rank = rank;
	}

	public void display() {
		System.out.printf("%-8d%-8d\n", id, rank);
	}
}

//Using sorted list with ordering ids
public class Ranking {
	static String Query;
	static InvertedIndexBST inverted;
	static Index index1;
	static LinkedList<Integer> queryDocs;
	static LinkedList<Doc_Rank> rankedDocs;

	public Ranking(InvertedIndexBST inverted, Index index1, String Query) {
		this.inverted = inverted;
		this.index1 = index1;
		this.Query = Query;
		queryDocs = new LinkedList<Integer>();
		rankedDocs = new LinkedList<Doc_Rank>();
	}

	public void displayAllDocList() {
		System.out.println("Displaying documents with scores...");

		if (rankedDocs.empty()) {
			System.out.println("Empty list, no ranked documents found.");
			return;
		}

		System.out.printf("%-8s%-8s\n", "DocID", "Score");
		rankedDocs.findFirst();
		while (!rankedDocs.last()) {
			rankedDocs.retrieve().display();
			rankedDocs.findNext();
		}
		rankedDocs.retrieve().display();
		System.out.println("Displaying last document with scores...");
	}

	public static Document getDocGivenID(int id) {
		return index1.getAllDocGivenID(id);
	}

	// عدد مرات تكرار الكلمة بالملف
	public static int termFrequency(Document document, String term) {
		int frequency = 0;
		LinkedList<String> words = document.words;
		if (words.empty())
			return 0;
		words.findFirst();
		while (!words.last()) {
			if (words.retrieve().equalsIgnoreCase(term))
				frequency++;
			words.findNext();
		}
		if (words.retrieve().equalsIgnoreCase(term)) // last wors
			frequency++;
		return frequency;
	}

	public static int getRankScore(Document document, String Query) {
		if (Query.isEmpty())
			return 0;
		String terms[] = Query.split("\\s+");
		int totalFreq = 0;
		for (int i = 0; i < terms.length; i++) // loops to all words and count word frequency
			totalFreq += termFrequency(document, terms[i].trim().toLowerCase());
		return totalFreq;
	}

	public static void RankQuery(String Query) {
		LinkedList<Integer> docIDs = new LinkedList<Integer>();
		if (Query.isEmpty())
			return;

		String[] terms = Query.split("\\s+"); // if there is more than one space
		boolean found = false;

		for (int i = 0; i < terms.length; i++) {
			found = inverted.searchWordInInverted(terms[i].trim().toLowerCase());
			if (found)
				docIDs = inverted.inverted_index.retrieve().doc_IDS;
			AddingInListSorted(docIDs);

		}
	}

	public static void AddingInListSorted(LinkedList<Integer> A) { // Addtpquerydoc
		if (A.empty())
			return;

		A.findFirst();
		while (!A.last()) {
			if (!existInResult(queryDocs, A.retrieve()))
				insertInSortedList(A.retrieve());

			A.findNext();
		}
		if (!existInResult(queryDocs, A.retrieve()))
			insertInSortedList(A.retrieve());
	}

	public static boolean existInResult(LinkedList<Integer> result, Integer id) {
		if (result.empty())
			return false;
		result.findFirst();
		while (!result.last()) {
			if (result.retrieve().equals(id))
				return true;
			result.findNext();
		}
		if (result.retrieve().equals(id))
			return true;
		return false;
	}

	public static void insertInSortedList(Integer id) {
		if (queryDocs.empty()) {
			queryDocs.insert(id);
			return;
		}

		queryDocs.findFirst();
		while (!queryDocs.last()) {
			if (id < queryDocs.retrieve()) {
				Integer id1 = queryDocs.retrieve();
				queryDocs.update(id);
				queryDocs.insert(id1);
				return;
			} else
				queryDocs.findNext();
		}
		if (id < queryDocs.retrieve()) {
			Integer id1 = queryDocs.retrieve();
			queryDocs.update(id);
			queryDocs.insert(id1);
			return;
		} else
			queryDocs.insert(id);

	}

	public static void insert_sorted_list() {
		RankQuery(Query);// Finding queryDocs

		if (queryDocs.empty()) {
			System.out.println("No matches for this query.");
			return;
		}
		queryDocs.findFirst();
		while (!queryDocs.last()) {
			Document document = getDocGivenID(queryDocs.retrieve());
			int Rank = getRankScore(document, Query);
			insert_sorted_list(new Doc_Rank(queryDocs.retrieve(), Rank)); // Adding the document in order
			queryDocs.findNext();
		}
		Document document = getDocGivenID(queryDocs.retrieve());
		int Rank = getRankScore(document, Query);
		insert_sorted_list(new Doc_Rank(queryDocs.retrieve(), Rank));

	}

	public static void insert_sorted_list(Doc_Rank documentRanked) {
		if (rankedDocs.empty()) {
			rankedDocs.insert(documentRanked);
			return;
		}
		rankedDocs.findFirst();
		while (!rankedDocs.last()) {
			if (documentRanked.rank > rankedDocs.retrieve().rank) {
				Doc_Rank documentRanked1 = rankedDocs.retrieve();
				rankedDocs.update(documentRanked);
				rankedDocs.insert(documentRanked1);
				return;
			} else
				rankedDocs.findNext();
		}
		if (documentRanked.rank > rankedDocs.retrieve().rank) {
			Doc_Rank documentRanked1 = rankedDocs.retrieve();
			rankedDocs.update(documentRanked);
			rankedDocs.insert(documentRanked1);
			return;
		} else
			rankedDocs.insert(documentRanked);
	}
}