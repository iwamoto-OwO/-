package produbt-tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

;

public class ItemSearch {
	private ArrayList<Item> itemList = new ArrayList<Item>();
	
	public void search(String keyword) {
		StringBuilder requestURL = new StringBuilder("https://app.rakuten.co.jp/services/api/IchibaItem/Search/20220601?");
		requestURL.append("applicationId=" + "1087373096287122136");
		requestURL.append("&keyword=" + keyword);
		//requestURL.append("&carrier=" + 0);
		//RAKUTEN_API.append("&sort=" + "%2BitemPrice");
		URLConnection connection = null;
		
		
		try {    // ここの部分でリクエスト送信と結果の取得
			URL url = new URL(requestURL.toString());
			connection = url.openConnection();
			BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String tmp = "";
			while((tmp = buf.readLine()) != null) {
				String[] line = tmp.split(",");
				String itemName = "", itemCaption = "", itemUrl = "";
				for(int i = 0; i < line.length; i++) {
					if(line[i].startsWith("\"itemName\":\"")) {
						itemName = line[i].substring(12,line[i].length()-1);
						//System.out.println(itemName);
					}else if(line[i].startsWith("\"itemCaption\":\"")) {
						itemCaption = line[i].substring(15,line[i].length()-1);
						//System.out.println(itemCaption);
					}else if(line[i].startsWith("\"itemUrl\":\"")) {
						itemUrl = line[i].substring(11,line[i].length()-1);
						//System.out.println(itemUrl);
						itemList.add(new Item(itemName, itemCaption, itemUrl));
					
					}
					//System.out.println(line[i]);
				}
	
				System.out.println();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Item> getItemList(){
		return itemList;
	}
	
	public static void main(String args[]){
		ItemSearch test = new ItemSearch();
		test.search("書籍");
		
		System.out.println();
		ArrayList<Item> itemList = test.getItemList();
		for(Item item: itemList) {
			System.out.println(item.getTitle());
		}
	}
}

