package FeatureRating;

import java.util.HashMap;

import FeatureExtraction.Pair;

/**
 * A summary matches each feature (key) to its:
 * rating (double)
 * opinions (i.e. which phrases were about the feature, how they were rated)
 * @author Antoine Sachet
 *
 */
@SuppressWarnings("serial")
public class Summary extends HashMap<String, Pair<Double, Opinion>> {

	public Summary() {
		super();
	}
	
	public void put(String feature, Opinion op) {
		this.put(feature, new Pair(op.getMean(), op));
	}

}
