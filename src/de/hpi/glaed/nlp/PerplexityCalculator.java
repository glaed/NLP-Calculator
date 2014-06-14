package de.hpi.glaed.nlp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerplexityCalculator {
    public static void main(String[] args){
        String collectionPath = "E:\\Data\\2014_NLP\\GENIA_treebank_v1\\";
        //String collectionPath = "E:\\Data\\2014_NLP\\SingleDocumentCorpus\\";

        PerplexityCalculation calc = new PerplexityCalculation();
        calc.parseTestAndTrainingSet(collectionPath);

        LanguageModel lm = new LanguageModel();
        lm.buildLanguageModel(calc.trainingCorpus);

        lm.printBigramOccurrences();
    }


}
