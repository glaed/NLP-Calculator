package de.hpi.glaed.nlp;

import java.util.*;

public class LanguageModel {

    private HashMap<Bigram, Integer> bigramOccurrences = new HashMap<Bigram, Integer>();
    private HashMap<String, Integer> unigramOccurrences = new HashMap<String, Integer>();

    public void buildLanguageModel(List<Document> trainingCorpus){

        for (Document doc : trainingCorpus){

            for (Sentence sentence : doc.sentences){
                String lastWord = "<START>";
                String currentWord = "";

                addUnigramOccurrence("<Start>");

                for (Token token : sentence.getTokenList()) {
                    currentWord = token.tokenName;

                    addUnigramOccurrence(currentWord);
                    addBigramOccurrence(lastWord, currentWord);
                    lastWord = currentWord;
                }
                addUnigramOccurrence("<STOP>");
                addBigramOccurrence(lastWord, "<STOP>");
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

    private double getSentenceProbability(Sentence sentence){
        double probability = 1.0;

        String lastWord = "<START>";
        String currentWord = "";

        for (Token token : sentence.getTokenList()){
            currentWord = token.tokenName;
            probability *= getBigramProbability(new Bigram(lastWord, currentWord));

            lastWord = currentWord;
        }
        probability *= getBigramProbability(new Bigram(lastWord, "<STOP>"));
        assert(probability > 0);

        return probability;
    }

    private double getBigramProbability(Bigram bigram){
        double bigramCount = bigramOccurrences.getOrDefault(bigram, 0);
        double baseCount = unigramOccurrences.getOrDefault(bigram.getFirst(), 0);

        double probability = (bigramCount + 1) / (baseCount + unigramOccurrences.size());
        assert(probability > 0);

        return probability;
    }

    public void printBigramOccurrences(){
        for(Map.Entry<Bigram, Integer> entry : sortBigramOccurrencesByCount(bigramOccurrences).entrySet()){
            if(entry.getValue() > 2){
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
