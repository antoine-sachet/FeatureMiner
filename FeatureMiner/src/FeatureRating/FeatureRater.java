package FeatureRating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import FeatureExtraction.FeatureExtractor;
import FeatureExtraction.Pair;
import Tagger.Util;

public class FeatureRater {

	ArrayList<String> features;

	public FeatureRater(ArrayList<String> features) {
		super();
		this.features = features;
	}
	
	public static void main(String[] args) throws IOException{
		
		String dataPath = "D:\\Sachou\\UCL\\FeatureMiner\\data\\small_laptop_data";
		String reviewPath = "D:\\Sachou\\UCL\\FeatureMiner\\data\\one_laptop_review\\B0000DHQ23.json";
		FeatureExtractor extractor = new FeatureExtractor();
		FeatureRater rater = new FeatureRater(extractor.extractFeatures(dataPath));
		
		ArrayList<String> reviews = rater.getRawReviews(reviewPath);
		for(String rev: reviews){
			System.out.println(rev);
		}
		String str = "This laptop is great but the screen is too small. The keyboard is well designed. The battery life is about 3 hours gaming, which is rather good.";
		SentimentAnalyzer ana = new SentimentAnalyzer();
		ArrayList sen = ana.findSentiment(str);
	}

	public ArrayList<String> getRawReviews(String filePath) {
		String str = new Util().ReadFile(filePath);
		JSONObject raw = JSONObject.fromObject(str);
		ArrayList<String> reviews = new ArrayList<String>();
		JSONArray array=raw.getJSONArray("Reviews");
		int size = array.size();
		for(int  i = 0; i < size; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			String rev = jsonObject.get("Content").toString();
			reviews.add(rev);
		}
		return(reviews);
	}

	
	public HashMap<String, Pair<String, Integer>> analyseReview() {
		return null;
		
	}
	
}
