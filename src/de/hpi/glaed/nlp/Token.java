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
       if(tokenName.equals("%")){
           tokenName = "<percentage>";
       }
       return "Token{{%s, %s}}".format(tokenName, posTag);
       //return tokenName + " " + posTag; //gives unaltered input
   }
}
