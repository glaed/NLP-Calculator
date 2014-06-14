package de.hpi.glaed.nlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for(Map.Entry entry : probabilities.entrySet()){
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }
}
