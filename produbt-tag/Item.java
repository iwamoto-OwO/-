package produbt-tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Item {
	
	String title;
	String text;
	String url;
	ArrayList<String> wordList = new ArrayList<String>();
	Map<String,Double> tfidfList = new HashMap<String,Double>();
	ArrayList<String> tag = new ArrayList<String>();
	
	public Item(String title, String text, String url) {
		this.title = title;
		this.text = text;
		this.url = url;
	}
	
	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}
	
	public void setTfidfList(Map<String,Double> tfidfList) {
		this.tfidfList = tfidfList;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public String getUrl() {
		return url;
	}
	
	public ArrayList<String> getWordList() {
		return wordList;
	}
	
	public Map<String,Double> getTfidfList() {
		return tfidfList;
	}
	
	public void creatTag() {
		int i = 0;
		double[] a = new double[5];
		for(Map.Entry<String, Double> entry : tfidfList.entrySet()) {
			if(i < 5) {
				tag.add(entry.getKey());
				a[i] = entry.getValue();
			}else{
				double min = a[0];
				int minKey = 0;
				for(int j = 1; j < 5; j++) {
					if(a[j] < min) {
						min = a[j];
						minKey = j;
					}
				}
				if(entry.getValue() < min) {
					tag.set(minKey, entry.getKey());
					a[minKey] = entry.getValue();
				}
			}
		    i++;
		}
	}
	
	public ArrayList<String> getTag() {
		return tag;
	}

	
	
	public void printTag() {
		System.out.println();
		System.out.println("title：" + title);
		System.out.println("url：" + url);
		System.out.println("caption：" + text);
		System.out.print("tag：");
		for(String tagWord: getTag()) {
			System.out.print(tagWord + " ");
		}
		System.out.println();
		System.out.println();
	}
	
}
