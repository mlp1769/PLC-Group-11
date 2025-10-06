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
        if(this.type.getToken() != "Double" || this.type.getToken() != "Integer" || this.type.getToken() != "String" || this.type.getToken() != "Boolean"){
            System.err.println(String.format("Syntax Error %n Unknown Type %n %s:%d",this.head.getFilename,this.head.getLineNum));

        }
        return this;
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
