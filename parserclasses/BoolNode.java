package parserclasses;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;

public class BoolNode implements ExprNode{
    private Token bool;

    public BoolNode(Token bool){
        this.bool = bool;

    }

    @Override
    public String convertToJott() {
        return this.bool.getToken();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getToken() == "True" || currToken.getToken() == "False"){
            return new BoolNode(currToken);

        }
        else{
            throw new Exception("Invalid Node");
        }
       
    }
    
}
