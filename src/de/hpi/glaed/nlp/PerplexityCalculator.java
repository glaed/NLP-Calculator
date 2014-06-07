package de.hpi.glaed.nlp;

public class PerplexityCalculator {
    public static void main(String[] args){

        DocumentParser parser = new DocumentParser();
        parser.splitDocumentCollection("E:\\Data\\2014_NLP\\GENIA_treebank_v1\\");
        /*try {
            //parser.parseDocument("E:\\Data\\2014_NLP\\GENIA_treebank_v1\\8877725.xml");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }*/
    }
}
