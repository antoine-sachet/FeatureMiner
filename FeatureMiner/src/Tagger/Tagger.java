package Tagger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class Tagger {
	//static JSONObject reviews;
	private List<JSONObject> productReviews;
	
	public Tagger(String filePath) {
		productReviews = new ArrayList<JSONObject> ();
		File dir = new File(filePath);
		
		for (File child : dir.listFiles()) {
			if (".".equals(child.getName()) || "..".equals(child.getName())) {
				continue; // Ignore the self and parent aliases.
			}
			String str = new Util().ReadFile(child.toString());
			productReviews.add(JSONObject.fromObject(str));
			//System.out.println("im in");
		}
	}
	
	public ArrayList<ArrayList<Bag>> getReviews(){
		ArrayList<ArrayList<Bag>> pro=new ArrayList<ArrayList<Bag>>();
		for(JSONObject reviews : productReviews) {
			JSONArray array=reviews.getJSONArray("Reviews");
			int size = array.size();
			//System.out.println("Size: " + size);
			
			for(int  i = 0; i < size; i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				//System.out.println("[" + i + "]content=" + jsonObject.get("Content"));
				Tag tag1=new Tag();
				ArrayList<Bag> rev = new ArrayList<Bag>();
				String t1 = tag1.analysis(jsonObject.get("Content").toString());
				//String t3 = jsonObject.get("Content").toString();
				String[] p1 = t1.split("\\.\\_\\.");
							
				for(int y=0;y<p1.length;y++){
					Bag b1=new Bag();
					b1.setID(y);
					b1.setSentence(p1[y]);
					b1.features=tag1.collect(p1[y]);
					rev.add(b1);
				}
							
				pro.add(rev);
			}
		}
		
		
		return pro;
	}

}