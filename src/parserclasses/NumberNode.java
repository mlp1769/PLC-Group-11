package src.parserclasses;
import provided.Token;
import provided.TokenType;

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
        return true;
    }

    @Override
    public Object exicute() throws Exception {
        return null;
    }

    public boolean isInteger(){
        try {
            Integer.parseInt(this.number.getToken());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }  
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) throws Exception {
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.NUMBER){
            return new NumberNode(currToken);
        }else{
            System.err.printf("Syntax Error: %n Expected Number got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
    }

    @Override
    public String getExpressionType() throws Exception {
        return this.isInteger() ? "Integer" : "Double";
    }

    @Override
    public Token getToken() {
        return this.number;
    }
}
