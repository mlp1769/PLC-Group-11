package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class TypeNode implements JottTree {
    private Token type;

    public TypeNode(Token type){
        this.type = type;
    }

    public static TypeNode parseTypeNode(ArrayList<Token> tokens){
        Token type = tokens.remove(0);
        if(!type.getToken().equals("Double") || !type.getToken().equals("Integer") || !type.getToken().equals("String") || !type.getToken().equals("Boolean")){
            System.err.println(String.format("Syntax Error %n Unknown Type %n %s:%d",type.getFilename(),type.getLineNum()));

        }
        return new TypeNode(type);
    }

    @Override
    public String convertToJott() {
        return this.type.getToken();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
