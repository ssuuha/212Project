package ds212;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Loader {
	LinkedList<String> stopWords;
	static Index index1;
	InvertedIndex inverted;
	InverIndexBST invertedBST;
	int numberOfToken = 0;
	LinkedList<String> uniqueWords = new LinkedList<>();

	public Loader() {
		stopWords = new LinkedList<>();
		index1 = new Index();
		inverted = new InvertedIndex();
		invertedBST = new InverIndexBST();
	}

	public void LoadStopWords(String fileName) {
		try {
			File file = new File(fileName);
			Scanner Read = new Scanner(file);
			while (Read.hasNextLine()) {
				String line = Read.nextLine();
				stopWords.insert(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadAllDoc(String fileName) {
		String line = null;
		try {
			File file = new File(fileName);
			Scanner Read = new Scanner(file);

			Read.nextLine(); // to skip firt line
			while (Read.hasNextLine()) {
				line = Read.nextLine();

				if (line.trim().length() < 3) {
					System.out.println("Empty line found, skipping to the next one.");
					break;
				}

				String F = line.substring(0, line.indexOf(',')).trim();
				int id = Integer.parseInt(F);
				System.out.println("Line: " + line);
				String content = line.substring(line.indexOf(',') + 1).trim();
				System.out.println("Conent: " + content);

				LinkedList<String> WordsINDoc = makeLinkedListOfWords(content, id);
				index1.add_document(new Document(id, WordsINDoc, content));
			}
		} catch (Exception e) {
			System.out.println("End of file");
		}
	}

	public LinkedList<String> makeLinkedListOfWords(String content, int id) {
		LinkedList<String> words_in_doc = new LinkedList<String>();
		makeIndexAndInvertedIndex(content, words_in_doc, id);
		return words_in_doc;
	}

	public LinkedList<String> InvertedIndexDoc(String WORDS, int id) {
		LinkedList<String> WordsINDoc = new LinkedList<String>();
		InvertedIndex(WORDS, WordsINDoc, id);
		return WordsINDoc;
	}

	public void InvertedIndex(String WORDS, LinkedList<String> WordsINDoc, int id) {
		WORDS = WORDS.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
		String[] tokens = WORDS.split("\\s+");
		for (String w : tokens) {
			if (!IsStopWord(w)) {
				WordsINDoc.insert(w);
				inverted.addinv(w, id);
				invertedBST.add(w, id);
			}
		}
	}

	public boolean IsStopWord(String word) {
		if (stopWords == null || stopWords.empty())
			return false;
		stopWords.findFirst();

		while (!stopWords.last()) {
			if (stopWords.retrieve().equals(word))
				return true;
			stopWords.findNext();
		}
		if (stopWords.retrieve().equals(word))
			return true;

		return false;
	}

	public void LoadFiles(String stopWordsFile, String doucment) {
		LoadStopWords(stopWordsFile);
		LoadAllDoc(doucment);

	}

	public void displayStopWords() {
		stopWords.display();
	}

	public void displayDocById(LinkedList<Integer> IDs) {
		if (IDs.empty()) {
			System.err.print("IDs list is empty, document doesn't exist.");
			return;
		}
		IDs.findFirst();
		System.out.print("Result: {");
		while (!IDs.last()) {
			index1.getAllDocGivenID(IDs.retrieve());
			System.out.print(",");
			IDs.findNext();
		}
		index1.getAllDocGivenID(IDs.retrieve());
		System.out.println("}");
	}

	public void makeIndexAndInvertedIndex(String content, LinkedList<String> words_in_doc, int id) {
		while (content.contains("-")) {
			if (content.charAt(content.indexOf("-") - 2) == ' ')
				content = content.replaceFirst("-", "");
			else
				content = content.replaceFirst("-", " ");
		}
		// to count unique words
		content = content.replaceAll("\'", "");
		content = content.replaceAll("-", "");
		content = content.replaceAll("'", "");
		content = content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
		String[] tokens = content.split("\\s+");
		numberOfToken += tokens.length;
		for (String w : tokens) {
			if (!uniqueWords.exist(w))
				uniqueWords.insert(w);

			if (!existsIn_stop_words(w)) {
				words_in_doc.insert(w);
				inverted.addinv(w, id);
				invertedBST.add(w, id);
			}
		}
	}

	public boolean existsIn_stop_words(String word) {
		if (stopWords == null || stopWords.empty())
			return false;

		stopWords.findFirst();
		while (!stopWords.last()) {
			if (stopWords.retrieve().equals(word))
				return true;

			stopWords.findNext();
		}

		if (stopWords.retrieve().equals(word))
			return true;
		return false;
	}

	public static void main(String args[]) {
		Loader loader = new Loader();
		loader.LoadFiles("stop.txt", "dataset.csv");
		Scanner read = new Scanner(System.in);
		int ch = 0;
		do {
			System.out.println("===========================menue===========================");
			System.out.println("Please choose from the following: ");
			System.out.println(
					"1-Retrieve a term, there are choices: (Using index with lists - Inverted index with lists - Inverted index with BST");
			System.out.println("2 Boolean Retrieval.");
			System.out.println("3 Ranked Retrieval");
			System.out.println("4 Print all Indexed Documents.");
			System.out.println("5 Print the number of document in the index.");
			System.out.println("6 Print the number of unique words in indexed.");
			System.out.println("7 Show inverted index with list of lists.");
			System.out.println("8 Show inverted index with BST.");
			System.out.println("9 Show the number of vocabulary and tokens in the index (Indexed Tokens).");
			System.out.println("10-Exit.");
			ch = read.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter a term to retrieve");
				String term = read.next();
				term = term.toLowerCase().trim();
				System.out.println("Using index with lists");

				System.out.println("word:" + term);
				LinkedList<Integer> result = Loader.index1.getAllDocGivenTerm(term);
				System.out.print("Document for this term: " + "[");

				result.display();
				System.out.println("]");
				System.out.println("-inverted index with lists");
				boolean found = loader.inverted.searchWordInInverted(term);
				if (found)
					loader.inverted.inverted_index.retrieve().display();
				else
					System.out.println("Not found in inverted index with lists.");

				System.out.println("-inverted index with BST.");
				boolean found2 = loader.invertedBST.searchWordInInverted(term);
				if (found2)
					loader.inverted.inverted_index.retrieve().display();
				else
					System.out.println("Not found in inverted index with lists.");
				break;
			case 2:
				read.nextLine();
				System.out.println("Enter a query to retrieve");
				String query = read.nextLine();
				query = query.toLowerCase();
				query = query.replaceAll(" and ", " AND ");
				query = query.replaceAll(" or ", " OR ");
				System.out.println("""
						Please choose the query method:
						     1-Using index.
						     2-Using inverted index list of lists.
						     3-Using BST.
						     4-exit.
						""");
				int choice = read.nextInt();
				do {
					if (choice == 1) {
						QueryProcessingIndex q = new QueryProcessingIndex(Loader.index1);
						System.out.println("========" + query + "=======");
						LinkedList res1 = QueryProcessingIndex.MixedQuery(query);
						loader.displayDocById(res1);
					} else if (choice == 2) {
						QueryProcessing q = new QueryProcessing(loader.inverted);
						System.out.println("========" + query + "=======");
						LinkedList res1 = QueryProcessing.MixedQuery(query);
						loader.displayDocById(res1);
					} else if (choice == 3) {
						QueryProcessingBST q = new QueryProcessingBST(loader.invertedBST);
						System.out.println("========" + query + "=======");
						LinkedList res1 = QueryProcessingBST.MixedQuery(query);
						loader.displayDocById(res1);
					} else if (choice == 4)
						break;
					else
						System.out.println("invalid query.");

					System.out.println("""
							Please choose the query method:
							     1-Using index.
							     2-Using inverted index list of lists.
							     3-Using BST.
							     4-exit.
							""");
					choice = read.nextInt();
				} while (choice != 4);

				break;
			case 3:
				read.nextLine();
				System.out.println("Enter a query to Rank");
				String query2 = read.nextLine();
				query2 = query2.toLowerCase();
				Rank ranking = new Rank(loader.invertedBST, index1, query2);
				ranking.insert_sorted_list();
				ranking.displayAllDocList();
				break;
			case 4:
				loader.index1.displayDocuments();
				System.out.println("=================================");
				break;
			case 5:
				System.out.println("Number of documents=" + Loader.index1.alldoc.count);
				System.out.println("=================================");
				break;
			case 6:
				System.out.println("Number of unique words without stop words=" + loader.inverted.inverted_index.count);
				System.out.println("=================================");
				break;
			case 7:
				loader.inverted.displayInvertedIndex();
				break;
			case 8:
				loader.invertedBST.display_inverted_index_BST();
				break;
			case 9:
				System.out.println("Number of tokens=" + loader.numberOfToken);
				System.out.println("Number of unique words including stop words=" + loader.uniqueWords.count);
				break;
			case 10:
				System.out.println("==========Thank you ==========");
				break;
			default:
				System.out.println("Error input number, please try again.");
				break;
			}
		} while (ch != 10);
	}

}
