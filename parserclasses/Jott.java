package parserclasses;

import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Jott {

    public static void main(String[] args){
        String fileName = null;
        if(args[0]!=null){
            fileName = args[0];
        }
        try{

            ArrayList<Token> tokens = JottTokenizer.tokenize(fileName);
            JottTree root = JottParser.parse(tokens);
            root.validateTree();


        }catch(Exception e){
            System.out.println("whomp whomp :(");
        }





    }

}
