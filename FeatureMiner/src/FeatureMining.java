import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import FeatureExtraction.FeatureExtractor;
import FeatureRating.FeatureRater;
import FeatureRating.Opinion;
import FeatureRating.Sentiment;
import FeatureRating.SentimentAnalyser;
import FeatureRating.Summary;
import Tagger.Util;


public class FeatureMining {

	public static void main(String[] args) throws IOException {
		
		// directory containing our review database (json files) 
		String dataPath = "D:\\Sachou\\UCL\\FeatureMiner\\data\\small_laptop_data";
		
		// path to product reviews (several reviews relative to the same product, in a json file)
		String reviewPath = "D:\\Sachou\\UCL\\FeatureMiner\\data\\one_laptop_review\\B0000DHQ23.json";
		
		// extracting the possible features from our database
		FeatureExtractor extractor = new FeatureExtractor();
		ArrayList<String> features = extractor.extractFeatures(dataPath);
		
		for(String feat: features){
			System.out.println(feat);
		}
		
		// loading up the reviews, each one in a string
		ArrayList<String> reviews = getRawReviews(reviewPath);
		
		// finding the opinion for each review
		// The opinion of a review is an arraylist of (sentence, sentiment)
		SentimentAnalyser analyser = new SentimentAnalyser();
		ArrayList<Opinion> opinions = analyser.findOpinion(reviews);
		
		// Finding the features rating given the opinions
		FeatureRater rater = new FeatureRater(features);
		Summary summary = rater.summarize(opinions);

		// Printing out the summary
		for(String feat: summary.keySet()) {
			Opinion op = summary.get(feat);
			System.out.println("### "+feat+" --> "+op.getMean()+" ###");
			for(Sentiment sen: op){
				System.out.println("- "+sen.getSentence());
				System.out.println("--> "+sen.getRating());
			}
		}
		
	}
	
	public static ArrayList<String> getRawReviews(String filePath) {
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

}
