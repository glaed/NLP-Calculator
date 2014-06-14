package de.hpi.glaed.nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dustin on 29.05.2014.
 */
public class Document {
    List<Sentence> sentences = new ArrayList<Sentence>();

    public void add(Sentence sentence){
        sentences.add(sentence);
    }
}
