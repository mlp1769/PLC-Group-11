package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode implements JottTree {

    private Token number;

    public NumberNode(Token number){
        this.number = number;
    }

    public Token getNumber() {
        return number;
    }

    public void setNumber(Token number) {
        this.number = number;
    }

    @Override
    public String convertToJott() {
        return this.number.getToken();
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    static NumberNode parseFunctionCallNode(ArrayList<Token> tokens) throws Exception {
        Token currToken = tokens.get(0);
        if(currToken.getTokenType() == TokenType.NUMBER){
            tokens.remove(0);
            return new NumberNode(currToken);
        }else{
            throw new Exception("Token is not a number");
        }
    }
}
