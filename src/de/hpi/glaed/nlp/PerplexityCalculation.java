package de.hpi.glaed.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class PerplexityCalculation {

    public List<Document> trainingCorpus;
    public List<Document> testCorpus;

    private LanguageModel languageModel = new LanguageModel();

    public void parseTestAndTrainingSet(String collectionPath) {
        DocumentParser parser = new DocumentParser();
        parser.splitDocumentCollection(collectionPath);

        long start = System.currentTimeMillis();
        try {
            trainingCorpus = parser.parseDocuments(parser.currentTrainingSet);
            testCorpus = parser.parseDocuments(parser.currentTestSet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("parse time: " + time);
    }

    public void buildLanguageModel() {
        languageModel.buildFrom(trainingCorpus);

        languageModel.printBigramOccurrences();
    }

    public void calculatePerplexity(){
        double avgLogProbability = calculateAverageLogProbability();
        System.out.println("avgLogProbability: " + avgLogProbability);

        double perplexity = Math.pow(2, -avgLogProbability);

        System.out.println("Cross-Perplexity on test corpus: " + perplexity);
    }

    private double calculateAverageLogProbability() {
        double probabilitySum = 0;
        for(Document doc : testCorpus){
            for(Sentence sentence : doc.sentences){
               probabilitySum += languageModel.getSentenceLogProbability(sentence);
            }
        }
        System.out.println("probabilitySum: " + probabilitySum);
        return probabilitySum / getTestCorpusWordCount();
    }

    private double getTestCorpusWordCount(){
        int count = 0;
        for(Document doc : testCorpus) {
            for (Sentence sentence : doc.sentences) {
                count += sentence.getTokenList().size();
            }
        }
        return count;
    }
}
