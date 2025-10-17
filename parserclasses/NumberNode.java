package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.nio.channels.NetworkChannel;
import java.util.ArrayList;

public class NumberNode implements OperandNode {

    private Token number;
    private boolean negative;

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

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) throws Exception {
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.NUMBER){
            return new NumberNode(currToken)
        }else{
            throw new Exception("Token is not a number");
        }
    }
}
