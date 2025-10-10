package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class FunctionReturnNode implements JottTree {
    private TypeNode type;
    private Token empty;
    public FunctionReturnNode(){
        this.type = New TypeNode();
    }

    public FunctionCallNode parseFunctionReturnNode(ArrayList<Token> tokens){
        Token n = tokens.get(0);
        if(n.getToken().equals("Void")){
            this.empty = tokens.remove(0);
            return this;
        }
        this.type = this.type.parseTypeNode(tokens);
        return this;
    }

    @Override
    public String convertToJott() {
        if(this.empty == null) {
            return "Void";
        }
        return this.type.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
