package de.hpi.glaed.nlp;

import java.util.*;

public class LanguageModel {

    private HashMap<Bigram, Integer> bigramOccurrences = new HashMap<Bigram, Integer>();
    private HashMap<String, Integer> unigramOccurrences = new HashMap<String, Integer>();

    public void buildFrom(List<Document> trainingCorpus){

        for (Document doc : trainingCorpus){

            for (Sentence sentence : doc.sentences){
                String lastWord = null;
                String currentWord = "";

                for (Token token : sentence.getTokenList()) {
                    currentWord = token.tokenName;

                    addUnigramOccurrence(currentWord);

                    if (lastWord != null){
                        addBigramOccurrence(lastWord, currentWord);
                    }
                    lastWord = currentWord;
                }
            }
        }
    }

    private void addBigramOccurrence(String lastWord, String currentWord) {
        Bigram bigram = new Bigram(lastWord, currentWord);

        int count = bigramOccurrences.getOrDefault(bigram, 0);
        bigramOccurrences.put(bigram, count + 1);
    }

    private void addUnigramOccurrence(String word) {
        int count = unigramOccurrences.getOrDefault(word, 0);
        unigramOccurrences.put(word, count + 1);
    }

    public double getSentenceLogProbability(Sentence sentence){
        double probability = 1.0;

        String lastWord = null;
        String currentWord = "";

        for (Token token : sentence.getTokenList()){
            currentWord = token.tokenName;

            if (lastWord != null) {
                //the log of a product is the sum of the logs
                probability += getBigramLogProbability(new Bigram(lastWord, currentWord));
            }

            lastWord = currentWord;
        }
        assert(probability > 0);
        assert(probability < 1.0);

        return probability;
    }

    private double getBigramProbability(Bigram bigram){
        double bigramCount = bigramOccurrences.getOrDefault(bigram, 0);
        double baseCount = unigramOccurrences.getOrDefault(bigram.getFirst(), 0);

        double probability = (bigramCount + 1) / (baseCount + unigramOccurrences.size());
        assert(probability > 0);

        return probability;
    }

    private double getBigramLogProbability(Bigram bigram){
        double bigramCount = bigramOccurrences.getOrDefault(bigram, 0);
        double baseCount = unigramOccurrences.getOrDefault(bigram.getFirst(), 0);

        //the log of a division is the difference of the logs
        double probability = log2(bigramCount + 1) - log2(baseCount + unigramOccurrences.size());
        assert(probability > 0);

        return probability;
    }

    private double log2(double x){
        return Math.log(x)/Math.log(2);
    }

    public void printBigramOccurrences(){
        for(Map.Entry<Bigram, Integer> entry : sortBigramOccurrencesByCount(bigramOccurrences).entrySet()){
            if(entry.getValue() > 10){
                System.out.println(entry.getValue() + " " + entry.getKey());
            }
        }
    }

    private Map<Bigram, Integer> sortBigramOccurrencesByCount(Map<Bigram, Integer> map){
        List<Map.Entry<Bigram, Integer>> entries = new ArrayList<Map.Entry<Bigram, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Bigram, Integer>>() {
            public int compare(Map.Entry<Bigram, Integer> a, Map.Entry<Bigram, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            }
        });
        Map<Bigram, Integer> sortedProbabilities = new LinkedHashMap<Bigram, Integer>();
        for (Map.Entry<Bigram, Integer> entry : entries) {
            sortedProbabilities.put(entry.getKey(), entry.getValue());
        }
        return sortedProbabilities;
    }
}
