package de.hpi.glaed.nlp;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private List<Token> tokenList;

    public Sentence() {
        tokenList = new ArrayList<Token>();
    }

    //todo: automatically put START and STOP symbols to each sentence
    public void add(Token token){
        this.tokenList.add(token);
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    @Override
    public String toString(){
        return tokenList.toString();
    }

    public void addStartSymbol() {
        assert(tokenList.isEmpty());
        tokenList.add(new Token("<START>", "<posSTART>"));
    }

    public void addStopSymbol(){
        assert(tokenList.size() > 0);
        tokenList.add(new Token("<STOP>", "<posSTOP>"));
    }
}
