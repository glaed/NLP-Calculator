package de.hpi.glaed.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by Dustin on 29.05.2014.
 */
public class PerplexityCalculator {
    public static void main(String[] args){

        DocumentParser parser = new DocumentParser();
        try {
            parser.parseDocument("E:\\Data\\2014_NLP\\GENIA_treebank_v1\\8877725.xml");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
