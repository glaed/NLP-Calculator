package de.hpi.glaed.nlp;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;

public class DocumentParser{

    XMLEventReader eventReader;
    ParserConfig config;

    public DocumentParser(){
        this.config = new ParserConfig();
    }

    private void openStream(String filePath) throws XMLStreamException, IOException {
        System.out.println("Open stream: %s".format(filePath));
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // set isCoalescing so we get complete contents of text tag
        inputFactory.setProperty("javax.xml.stream.isCoalescing", true);

        InputStream in = new FileInputStream(filePath);

        eventReader = inputFactory.createXMLEventReader(in);
    }

    public void splitDocumentCollection(String directory){

        FilenameFilter xmlFilter = createXMLFilter();
        File[] files = new File(directory).listFiles(xmlFilter);
        //config.setCollectionSize(files.length);

        File[] testSet = new File[config.getTestSetSize()];
        File[] trainingSet = new File[config.getTrainingSetSize()];

        System.out.println(files.length);
        System.out.println(config.getTestSetSize());
        System.out.println(config.getTrainingSetSize());

        //Collections.shuffle(Arrays.asList(files)); //todo: test calculation with randomized sets

        for(int i=0; i<config.getTestSetSize(); i++){
            testSet[i] = files[i];
        }
        for(int i=0; i<config.getTrainingSetSize(); i++){
            trainingSet[i] = files[i+config.getTestSetSize()];
        }
        System.out.println(testSet[config.getTestSetSize()-1]);
        System.out.println(trainingSet[config.getTrainingSetSize()-1]);
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
                    System.out.println(event.asStartElement().getName().getLocalPart());

                    parseSentence();
                }
            }
        }
        return doc;
    }

    private Sentence parseSentence() throws XMLStreamException {
        Sentence sentence = new Sentence();

        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()){
                if (event.asStartElement().getName().getLocalPart().equals("tok")){
                    sentence.add(parseToken());
                }
            }
            if (event.isEndElement()){
                if (event.asEndElement().getName().getLocalPart().equals("sentence")){
                    System.out.println(sentence);
                    break;
                }
            }

        }
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
