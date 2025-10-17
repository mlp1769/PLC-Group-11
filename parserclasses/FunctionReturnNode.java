package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class FunctionReturnNode implements JottTree {
    private TypeNode type;
    private Token empty;
    public FunctionReturnNode(TypeNode type, Token empty){
        this.type = type;
        this.empty = empty;
    }

    public static FunctionReturnNode parseFunctionReturnNode(ArrayList<Token> tokens) throws Exception{
        Token n = tokens.get(0);
        if(n.getToken().equals("Void")){
            n = tokens.remove(0);
            return new FunctionReturnNode(null, null);
        }
        TypeNode type = TypeNode.parseTypeNode(tokens);
        return new FunctionReturnNode(type, n);
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
