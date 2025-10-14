package parserclasses;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;

public class StringLiteralNode implements ExprNode{
    private Token literal;
    public StringLiteralNode(Token literal){
        this.literal = literal;


    }

    @Override
    public String convertToJott() {
        return this.literal.getToken();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public StringLiteralNode parseStringLiteralNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.get(0);
        if(currToken.getTokenType() == TokenType.STRING){
            this.literal = currToken;
            return this;

        }
        else{
            throw new Exception("Invalid Token");
        }
         
    }
    
}
