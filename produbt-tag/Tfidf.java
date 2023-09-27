package produbt-tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tfidf {
	public static void main(String[] args) {
		//APIから書籍情報を取得
		ItemSearch itemSearch = new ItemSearch();
		itemSearch.search("書籍");
		ArrayList<Item> itemList = itemSearch.getItemList();
		
		//書籍のキャプションを形態素解析
		//for(Item item: itemList) {
		//	System.out.println(item.getTitle());
		//}
		Sudachi sudachi = new Sudachi();
		sudachi.analyze(itemList);
		itemList = sudachi.getItemList();
		System.out.println(itemList.get(0).getTitle());
		
		//tf-idf解析
		Tfidf tfidf = new Tfidf();
		ArrayList<Map<String,Double>> tfidfList = tfidf.calc(itemList);

		
	}

	public ArrayList<Map<String,Double>> calc(ArrayList<Item> itemList) {
		ArrayList<Map<String,Double>> alltfidfList = new ArrayList<>();
		for(int i = 0; i < itemList.size(); i++) {
			Map<String,Double> tfidfList = new HashMap<>();
			ArrayList<String> wordList = itemList.get(i).getWordList();
			for(String word:wordList) {
				if(!tfidfList.containsKey(word)) {
					double tf = (double) countWord(word,wordList) / (double) wordList.size();
			        double idf = Math.log((double) itemList.size() / ((double) countItem(word,itemList) + 1));
			        tfidfList.put(word,tf/idf);
			        //System.out.println(countItem(word,itemList) + ", " + itemList.size() );
			        //System.out.println(word + "：" + tf + ", " + idf + ", " + tf/idf);
				}
			}
			alltfidfList.add(tfidfList);
		}
		
		return alltfidfList;
	}
	
	public int countItem(String target, ArrayList<Item> itemList) {
		int counter = 0;
		for(int i = 0; i < itemList.size(); i++) {
			ArrayList<String> wordList = itemList.get(i).getWordList();
			for(String word: wordList) {
				if(word.equals(target)) {
					counter++;
					break;
				}
			}
		}
		return counter;
	}
	
	public int countWord(String target, ArrayList<String> wordList) {
		int counter = 0;
		for(String word:wordList) {
			if(word.equals(target)) {
				counter++;
			}
		}
		return counter;
	}
}
