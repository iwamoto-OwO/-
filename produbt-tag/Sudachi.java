package produbt-tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.worksap.nlp.sudachi.Dictionary;
import com.worksap.nlp.sudachi.DictionaryFactory;
import com.worksap.nlp.sudachi.Morpheme;
import com.worksap.nlp.sudachi.Tokenizer;

/** フィード(RSS) の全 item 要素 の description 要素の内容を Sudachi で形態素解析 */

public class Sudachi {
	
	ArrayList<Item> newItemList = new ArrayList<Item>();
	ArrayList<String> blackList;
	
	
	public static void main(String[] args) {
		ItemSearch test = new ItemSearch();
		test.search("書籍");
		ArrayList<Item> itemList = test.getItemList();
		/*
		for(Item item: itemList) {
			System.out.println(item.getTitle());
		}
		*/
		Sudachi sudachi = new Sudachi();
		sudachi.analyze(itemList);
		
	}
	
	public Sudachi() {
		blackList = new ArrayList<String>(
				Arrays.asList(
						"株式会社","変更","情報","合計","ため","名称","入金","配送","送料","詳細","購入履歴","お客様","mm","cm"
				)
			);
	}
	
	public void analyze(ArrayList<Item> itemList) {
		try {

			// 形態素解析器の用意
			Dictionary dictionary = null;
			try {
				// 設定ファイル sudachi.json を用意した場合にはそれを読み込む
				//dictionary = new DictionaryFactory().create(Files.readString(Paths.get("sudachi.json")));
				dictionary = new DictionaryFactory().create();
			}
			catch(Exception e) {
				System.err.println("辞書が読み込めません: " + e);
				System.exit(-1);
			}
			Tokenizer tokenizer = dictionary.create();


			for(Item item : itemList) {
				String text = item.getText();
				ArrayList<String> wordList = new ArrayList<String>();
				//System.out.println();
				//System.out.println("Text: " + text);
				//System.out.println();
				// 形態素解析
				for(List<Morpheme> list: tokenizer.tokenizeSentences(Tokenizer.SplitMode.C, text)) {
					for(Morpheme morpheme: list) {
						if(String.join("-", morpheme.partOfSpeech()).startsWith("名詞")) {
							/*
							System.out.println(morpheme.surface() + "\t" // 表層形
									+ String.join("-", morpheme.partOfSpeech()) + "," // 品詞
									+ morpheme.dictionaryForm() + "," // 原形
									+ morpheme.readingForm() + "," // 読み
									+ morpheme.normalizedForm()); // 正規形
							*/
							
							//System.out.println(morpheme.dictionaryForm());
							if(!morpheme.dictionaryForm().matches("[+-]?\\d*(\\.\\d+)?") && morpheme.dictionaryForm().length() > 1 && !blackList.contains(morpheme.dictionaryForm())) {
								wordList.add(morpheme.dictionaryForm());
							}
						}
					}
					//System.out.println("EOS");	// 文の終端 (End of Sentence)
				}
				item.setWordList(wordList);
				newItemList.add(item);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Item> getItemList(){
		return newItemList;
	}
}

