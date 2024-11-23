package ds212;

public class InvertedIndexAVL {
    private AVLTree<String, Word> invertedIndexAVL; 
    private int count;

    public InvertedIndexAVL() {
        this.invertedIndexAVL = new AVLTree<>();
        this.count = 0;}

    public boolean addWord(String word, int docID) {
        if (!invertedIndexAVL.find(word)) {
            Word newWord = new Word(word);
            newWord.add_Id(docID);
            invertedIndexAVL.insert(word, newWord);
            count++;
            return true;} 
        else {
            Word existingWord = invertedIndexAVL.retrieve(word);  
            existingWord.add_Id(docID); 
            invertedIndexAVL.insert(word, existingWord); 
            return false;}
      }

    
    public boolean found(String word) {
        return invertedIndexAVL.find(word); }

    public void display() {
        invertedIndexAVL.traverse(); }

    public Word retrieve(String word) {
        if (invertedIndexAVL.find(word)) {
            return invertedIndexAVL.retrieve(word);
        }
        return null;
    }

    public int size() {
        return invertedIndexAVL.size();
    }
}
