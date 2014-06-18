package de.hpi.glaed.nlp;

public class PerplexityCalculator {
    public static void main(String[] args){
        String collectionPath = "E:\\Data\\2014_NLP\\GENIA_treebank_v1\\";
        //String collectionPath = "E:\\Data\\2014_NLP\\SingleDocumentCorpus\\";

        PerplexityCalculation calc = new PerplexityCalculation();
        calc.parseTestAndTrainingSet(collectionPath);
        calc.buildLanguageModel();
        calc.calculatePerplexity();
    }


}
