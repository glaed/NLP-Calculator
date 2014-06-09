package de.hpi.glaed.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class PerplexityCalculator {
    public static void main(String[] args){

        DocumentParser parser = new DocumentParser();
        parser.splitDocumentCollection("E:\\Data\\2014_NLP\\GENIA_treebank_v1\\");

        long start = System.currentTimeMillis();
        try {
            parser.parseDocuments(parser.currentTrainingSet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("parse time: " + time);
    }
}
