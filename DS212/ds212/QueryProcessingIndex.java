package ds212;

public class QueryProcessingIndex {

	static Index index;

	public QueryProcessingIndex(Index index1) {
		index = index1;
	}

	public static LinkedList<Integer> MixedQuery(String Query) {
		LinkedList<Integer> listA = new LinkedList<Integer>();
		LinkedList<Integer> listB = new LinkedList<Integer>();
		if (Query.length() == 0)
			return listA;
		String ORs[] = Query.split("OR"); // less priorty than AND

		listA = AndQuery(ORs[0]);
		for (int i = 1; i < ORs.length; i++) {
			listB = AndQuery(ORs[i]);
			listA = ORQueryUnion(listA, listB);
		}
		return listA;
	}

	public static LinkedList<Integer> AndQuery(String Q) {// Q=Query
		LinkedList<Integer> listA = new LinkedList<Integer>();// first list A
		LinkedList<Integer> listB = new LinkedList<Integer>();// second list B
		String terms[] = Q.split("AND");

		if (terms.length == 0)
			return listA;
		listA = index.getAllDocGivenTerm(terms[0].trim().toLowerCase()); // search
		for (int i = 1; i < terms.length; i++) {
			listB = index.getAllDocGivenTerm(terms[i].trim().toLowerCase()); // search
			listA = AndQueryIntersection(listA, listB);
		}
		return listA;
	}

	public static LinkedList<Integer> AndQueryIntersection(LinkedList<Integer> listA, LinkedList<Integer> listB) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (listA.empty() || listB.empty())
			return result;
		listA.findFirst();
		while (true) {
			boolean found = existsIn_result(result, listA.retrieve());
			if (!found) {
				listB.findFirst();
				while (true) {
					if (listB.retrieve().equals(listA.retrieve())) {
						result.insert(listA.retrieve());
						break;
					}
					if (!listB.last())
						listB.findNext();
					else
						break;
				} // end S loop

			} // not found loop
			if (!listA.last())
				listA.findNext();
			else
				break;
		}
		return result;
	}

	public static LinkedList<Integer> ORQuery(String Q) {// Q=Query
		LinkedList<Integer> listA = new LinkedList<Integer>();// first list A
		LinkedList<Integer> listB = new LinkedList<Integer>();// second list B
		String terms[] = Q.split("OR");

		if (terms.length == 0)
			return listA;
		listA = index.getAllDocGivenTerm(terms[0].trim().toLowerCase()); // search
		for (int i = 1; i < terms.length; i++) {
			listB = index.getAllDocGivenTerm(terms[i].trim().toLowerCase()); // search

			listA = ORQueryUnion(listA, listB);
		}
		return listA;
	}

	public static LinkedList<Integer> ORQueryUnion(LinkedList<Integer> listA, LinkedList<Integer> listB) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (listA.empty() && listB.empty())
			return result;
		listA.findFirst();
		while (!listA.empty()) {
			boolean found = existsIn_result(result, listA.retrieve());
			if (!found) {
				result.insert(listA.retrieve());
			}
			if (!listA.last()) {
				listA.findNext();
			} else
				break;
		}
		listB.findFirst();
		while (!listB.empty()) {
			boolean found = existsIn_result(result, listB.retrieve());
			if (!found) {
				result.insert(listB.retrieve());
			}
			if (!listB.last()) {
				listB.findNext();
			} else
				break;
		}
		return result;
	}

	public static boolean existsIn_result(LinkedList<Integer> result, Integer id) {
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
}