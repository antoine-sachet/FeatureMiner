package FeatureRating;

import java.util.ArrayList;
import java.util.Properties;

import FeatureExtraction.Pair;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzer {
	 
	 Properties props;
	 StanfordCoreNLP pipeline;
     
	public SentimentAnalyzer() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
	}
	
    public ArrayList<Pair<String, Integer>> findSentiment(String line) {
    	ArrayList<Pair<String, Integer>> sen = new ArrayList<Pair<String, Integer>>();
        if (line != null && line.length() > 0) {
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                //System.out.println(partText+" : "+sentiment);
                sen.add(new Pair(partText, sentiment));
            }
        }
        return sen;
 
    }
}