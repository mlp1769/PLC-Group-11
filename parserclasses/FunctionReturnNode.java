package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class FunctionReturnNode implements JottTree {
    private TypeNode type;
    private boolean empty;
    public FunctionReturnNode(TypeNode type){
        this.type = type;
        this.empty = (type == null);
    }

    public static FunctionReturnNode parseFunctionReturnNode(ArrayList<Token> tokens) throws Exception{
        Token n = tokens.get(0);
        if(n.getToken().equals("Void")){
            n = tokens.remove(0);
            return new FunctionReturnNode(null);
        }
        TypeNode type = TypeNode.parseTypeNode(tokens);
        return new FunctionReturnNode(type);
    }

    @Override
    public String convertToJott() {
        if(this.empty) {
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
