package de.hpi.glaed.nlp;

import javax.print.Doc;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerplexityCalculation {

    public List<Document> trainingCorpus;
    public List<Document> testCorpus;

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
}
