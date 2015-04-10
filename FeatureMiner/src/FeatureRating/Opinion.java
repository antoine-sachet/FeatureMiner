package FeatureRating;

import java.util.ArrayList;

import FeatureExtraction.Pair;

/**
 * An opinion is the sentiment of every sentence, in the form of an arraylist of (sentence, sentiment)
 * The sentiment is an integer from 1 to 5, 3 being neutral.
 * @author Antoine Sachet
 *
 */
@SuppressWarnings("serial")
public class Opinion extends ArrayList<Pair<String, Integer>> {

	public Opinion() {
		super();
	}

	public void add(String str, Integer sen){
		this.add(new Pair<String, Integer>(str, sen));
	}

	public double getMean() {
		double mean = 0;
		for(Pair<String, Integer> op: this){	
			mean += op.getRight();
		}
		return (mean/this.size());
	}
}