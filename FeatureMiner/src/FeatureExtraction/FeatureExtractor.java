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
		// TODO Auto-generated method stub
		//extract potential features
		String inputFile = "C:\\Users\\Jason\\Desktop\\grad 2\\data mining\\group\\data\\test";
		Tagger myTagger = new Tagger(inputFile);
		List<ArrayList<Bag>> reviews = myTagger.getReviews();
		List<List<String>> input = new ArrayList<List<String>> ();
		for(ArrayList<Bag> bags : reviews) {
			for(Bag bag : bags) {
				if (bag.features.size() != 0) {
					input.add(bag.features);
					System.out.println(bag.features);
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		/*
		//String inputFilePath = "testOutput.txt";
		
		
		
		//old implementation
		//String input = outputFile.getAbsolutePath();
		//create test input
		List<List<String>> input = new ArrayList<List<String>> ();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line;
		// for each line (transactions) until the end of the file
		while (((line = reader.readLine()) != null)) { 
			// if the line is  a comment, is  empty or is a
			// kind of metadata
			if (line.isEmpty() == true ||
					line.charAt(0) == '#' || line.charAt(0) == '%'
							|| line.charAt(0) == '@') {
				continue;
			}
			// split the line according to spaces
			String[] lineSplited = line.split(",");
			
			List<String> temp = new ArrayList<String>();
			for (int i=0; i< lineSplited.length; i++) { 
				String item = lineSplited[i];
				if(item != null)
					temp.add(item);
			}
			input.add(temp);
		}
		
		//for (List<Integer> ii : input) {
		//	for (Integer iii : ii) {
		//		System.out.println(iii);
		//	}
		//	System.out.println("---------------");
		//}
		
		reader.close();
		*/
		
		Converter converter = new Converter(input, inputFile);
		List<List<Integer>> outputList = converter.getOutputList();
		String output = null;
		
		double minsup = 0.01; // means a minsup of 2 transaction (we used a relative support)
		
		// Applying the Apriori algorithm
		AlgoApriori apriori = new AlgoApriori();
		Itemsets result = apriori.runAlgorithm(minsup, outputList, output, true);
		//apriori.printStats();
		//result.printItemsets(apriori.getDatabaseSize());
		
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
				System.out.print("support :  "
						+ itemset.getRelativeSupportAsString(apriori.getDatabaseSize()));
				patternCount++;
				System.out.println(" ");
				
			}
			levelCount++;
		}
	}
}
