package FeatureExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AprioriAlgorithm.AlgoApriori;
import AprioriAlgorithm.Itemset;
import AprioriAlgorithm.Itemsets;
import Tagger.Bag;
import Tagger.Tagger;

public class FeatureExtractor {

	public static void main(String[] args) throws IOException {
		String inputPath = "D:\\Sachou\\UCL\\FeatureMiner\\data\\small_laptop_data";
		FeatureExtractor extractor = new FeatureExtractor();
		Converter converter = new Converter();
		AlgoApriori apriori = new AlgoApriori();
		Itemsets result = extractor.extract(inputPath, converter, apriori);
		
		System.out.println("--------------");
		int patternCount = 0;
		int levelCount = 0;
		List<List<Itemset>> levels = result.getLevels();

		//print the result
		for (List<Itemset> level : levels) {
			// print how many items are contained in this level
			System.out.println("  L" + levelCount + " ");
			// for each itemset
			for (Itemset itemset : level) {
				Arrays.sort(itemset.getItems());
				// print the itemset
				System.out.print("  pattern " + patternCount + ":  ");
				//itemset.print();
				int[] items = itemset.getItems();
				for(int i = 0; i < items.length; i++) {
					String word = converter.lookUpDictionary(items[i]);
					System.out.print(word + " ");
				}
				// print the support of this itemset
				System.out.print("\t\tsupport :  "
						+ itemset.getRelativeSupportAsString(apriori.getDatabaseSize()));
				patternCount++;
				System.out.println(" ");
				
			}
			levelCount++;
		}
	}
	
	public Itemsets extract(String inputFile, Converter converter, AlgoApriori apriori) throws IOException {
		//extract potential features
				Tagger myTagger = new Tagger(inputFile);
				List<ArrayList<Bag>> reviews = myTagger.getReviews();
				List<List<String>> input = new ArrayList<List<String>> ();
				for(ArrayList<Bag> bags : reviews) {
					for(Bag bag : bags) {
						if (bag.features.size() != 0) {
							input.add(bag.features);
							//System.out.println(bag.features);
						}
					}
				}
				
				converter.ini(input, inputFile);
				List<List<Integer>> outputList = converter.getOutputList();
				String output = null;
				
				double minsup = 0.01; // means a minsup of 2 transaction (we used a relative support)
				
				// Applying the Apriori algorithm
				Itemsets result = apriori.runAlgorithm(minsup, outputList, output, true);
				//apriori.printStats();
				//result.printItemsets(apriori.getDatabaseSize());
				return(result);

	}
	
	public Itemsets extract(String inputPath) throws IOException{
		return(extract(inputPath, new Converter(), new AlgoApriori()));
	}
}
