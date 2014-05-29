package de.hpi.glaed.nlp;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dustin on 29.05.2014.
 */
public class DocumentParser{

    XMLEventReader eventReader;

    private void openStream(String filePath) throws XMLStreamException, IOException {
        System.out.println("Open stream: %s".format(filePath));
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // set isCoalescing so we get complete contents of text tag
        inputFactory.setProperty("javax.xml.stream.isCoalescing", true);
        //InputStream in = filename.openStream();

        InputStream in = new FileInputStream(filePath);

        eventReader = inputFactory.createXMLEventReader(in);
    }

    public Document parseDocument(String filePath) throws IOException, XMLStreamException {
        openStream(filePath);

        Document doc = new Document();
        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();
            event.toString();
            if (event.isStartElement()) {
                if(event.asStartElement().getName().getLocalPart().equals("sentence")){
                    System.out.println(event.asStartElement().getName().getLocalPart());

                    //parseSentence();
                }
            }
            if (event.isCharacters()) {
                System.out.println(event.asCharacters().getData());
            }
        }
        return doc;
    }

    private void parseSentence() {
        while(eventReader.hasNext()){

        }

    }
}
