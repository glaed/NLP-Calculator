package de.hpi.glaed.nlp;

public class Token {
    String tokenName;
    String posTag;

   public Token(String name, String posTag){
       this.tokenName = name;
       this.posTag = posTag;
   }

   @Override
   public String toString(){
       return tokenName;
       //return String.format("Token[%s, %s]", tokenName, posTag);
   }
}
