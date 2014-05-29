package de.hpi.glaed.nlp;

import java.util.List;

/**
 * Created by Dustin on 29.05.2014.
 */
public class Sentence {
    private List<Token> token;

    public Sentence() { }

    public void add(Token token){
        this.token.add(token);
    }
}
