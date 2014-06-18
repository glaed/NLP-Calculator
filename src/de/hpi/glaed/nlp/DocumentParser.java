package de.hpi.glaed.nlp;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DocumentParser{

    XMLEventReader eventReader;
    ParserConfig config;

    File[] currentTestSet;
    File[] currentTrainingSet;

    public DocumentParser(){
        this.config = new ParserConfig();
    }

    private void openStream(String filePath) throws XMLStreamException, IOException {
        //System.out.println(String.format("Open stream: %s", filePath));
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // set isCoalescing so we get complete contents of text tag
        inputFactory.setProperty("javax.xml.stream.isCoalescing", true);

        InputStream in = new FileInputStream(filePath);

        eventReader = inputFactory.createXMLEventReader(in);
    }

    public List<Document> parseDocuments(File[] files) throws IOException, XMLStreamException {
        List<Document> docList = new ArrayList<Document>();

        for (File file : files){
            docList.add(parseDocument(file.getAbsolutePath()));
        }
        return docList;
    }

    public void splitDocumentCollection(String directory){

        FilenameFilter xmlFilter = createXMLFilter();
        File[] files = new File(directory).listFiles(xmlFilter);
        config.setCollectionSize(files.length);

        currentTestSet = new File[config.getTestSetSize()];
        currentTrainingSet = new File[config.getTrainingSetSize()];

        System.out.println("Files in Corpus: " + files.length);
        System.out.println("Test set size: " + config.getTestSetSize());
        System.out.println("Training set size: " + config.getTrainingSetSize());

        Collections.shuffle(Arrays.asList(files));

        for(int i=0; i<config.getTestSetSize(); i++){
            currentTestSet[i] = files[i];
        }
        for(int i=0; i<config.getTrainingSetSize(); i++){
            currentTrainingSet[i] = files[i+config.getTestSetSize()];
        }
    }

    private FilenameFilter createXMLFilter() {
        return new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name){
                return name.endsWith(".xml");
            };
        };
    }

    public Document parseDocument(String filePath) throws IOException, XMLStreamException {
        openStream(filePath);

        Document doc = new Document();
        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                if(event.asStartElement().getName().getLocalPart().equals("sentence")){

                    doc.add(parseSentence());
                }
            }
        }
        return doc;
    }

    private Sentence parseSentence() throws XMLStreamException {
        Sentence sentence = new Sentence();
        sentence.addStartSymbol();

        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()){
                if (event.asStartElement().getName().getLocalPart().equals("tok")){

                    sentence.add(parseToken());
                }
            }
            if (event.isEndElement()){
                if (event.asEndElement().getName().getLocalPart().equals("sentence")){

                    //System.out.println(sentence);
                    break;
                }
            }

        }
        sentence.addStopSymbol();
        return sentence;
    }

    private Token parseToken() throws XMLStreamException {
        String name = "<tokenName>";
        String posTag = "<posTag>";

        //todo: add posTag from attribute
        while(eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            if (event.isCharacters()){
                name = event.asCharacters().getData();
            }
            if (event.isEndElement()) {
                if (event.asEndElement().getName().getLocalPart().equals("tok")) {
                    break;
                }
            }
        }
        return new Token(name, posTag);
    }
}
