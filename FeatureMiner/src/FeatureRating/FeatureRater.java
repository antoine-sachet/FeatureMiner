package FeatureRating;

import java.util.ArrayList;

public class FeatureRater {

	ArrayList<String> features;

	public FeatureRater(ArrayList<String> features) {
		super();
		this.features = features;
	}

	public Summary analyseReview(ArrayList<Opinion> ops) {
		
		
		for(Opinion op: ops){
			for(String feat: features){
				
			}
		}
		
		return null;
	}
}

