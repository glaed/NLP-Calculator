package de.hpi.glaed.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerplexityCalculator {
    public static void main(String[] args){
        //String collectionPath = "E:\\Data\\2014_NLP\\GENIA_treebank_v1\\";
        String collectionPath = "E:\\Data\\2014_NLP\\SingleDocumentCorpus\\";

        List<Document> trainingCorpus = parseTrainingSet(collectionPath);

        LanguageModel lm = new LanguageModel();
        lm.buildLanguageModel(trainingCorpus);

        lm.printProbabilities();
    }

    private static List<Document> parseTrainingSet(String collectionPath) {
        DocumentParser parser = new DocumentParser();
        parser.splitDocumentCollection(collectionPath);

        List<Document> documentCollection = new ArrayList<Document>();

        long start = System.currentTimeMillis();
        try {
            documentCollection = parser.parseDocuments(parser.currentTrainingSet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("parse time: " + time);

        return documentCollection;
    }
}
