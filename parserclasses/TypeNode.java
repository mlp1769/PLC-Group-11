package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class TypeNode implements JottTree {
    private Token type;

    public TypeNode(){}

    public TypeNode parseTypeNode(ArrayList<Token> tokens){
        this.type = tokens.remove(0);
        if(!this.type.getToken().equals("Double") || !this.type.getToken().equals("Integer") || !this.type.getToken().equals("String") || !this.type.getToken().equals("Boolean")){
            System.err.println(String.format("Syntax Error %n Unknown Type %n %s:%d",this.type.getFilename(),this.type.getLineNum()));

        }
        return this;
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
