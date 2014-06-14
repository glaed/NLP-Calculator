package de.hpi.glaed.nlp;

import java.util.*;

public class LanguageModel {

    private HashMap<Bigram, Integer> probabilities = new HashMap<Bigram, Integer>();

    public HashMap<Bigram, Integer> getProbabilities() {
        return probabilities;
    }

    public void buildLanguageModel(List<Document> trainingCorpus){

        for (Document doc : trainingCorpus){

            for (Sentence sentence : doc.sentences){
                String lastWord = "<START>";
                String currentWord = "";

                for (Token token : sentence.getTokenList()) {
                    currentWord = token.tokenName;

                    addProbabilities(lastWord, currentWord);
                    lastWord = currentWord;
                }
                addProbabilities(lastWord, "<STOP>");
            }
        }
    }

    private void addProbabilities(String lastWord, String currentWord) {
        Bigram bigram = new Bigram(lastWord, currentWord);
        Integer count = probabilities.get(bigram);
        if (count != null) {
            probabilities.put(bigram, count+1);
        } else {
            probabilities.put(bigram, 1);
        }
    }

    public void printProbabilities(){
        for(Map.Entry entry : sortProbabilitiesByCount(probabilities).entrySet()){
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }

    private Map<Bigram, Integer> sortProbabilitiesByCount(Map<Bigram, Integer> map){
        List<Map.Entry<Bigram, Integer>> entries = new ArrayList<Map.Entry<Bigram, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Bigram, Integer>>() {
            public int compare(Map.Entry<Bigram, Integer> a, Map.Entry<Bigram, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            }
        });
        Map<Bigram, Integer> sortedMap = new LinkedHashMap<Bigram, Integer>();
        for (Map.Entry<Bigram, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
