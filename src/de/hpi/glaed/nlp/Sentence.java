package de.hpi.glaed.nlp;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private List<Token> tokenList;

    public Sentence() {
        tokenList = new ArrayList<Token>();
    }

    public void add(Token token){
        this.tokenList.add(token);
    }

    @Override
    public String toString(){
        return tokenList.toString();
    }
}
