package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class NumberNode implements OperandNode {

    private Token number;
    private boolean negative;

    public NumberNode(Token number, boolean negative){
        this.number = number;
        this.negative = negative;
    }

    public Token getNumber() {
        return number;
    }

    public void setNumber(Token number) {
        this.number = number;
    }
    public boolean isNegative(Token number){
        double num = Integer.parseInt(number.getToken());
        if(num < 0){
            return true;
        }
        return false;
    }

    @Override
    public String convertToJott() {
        return this.number.getToken();
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    public NumberNode parseNumberNode(ArrayList<Token> tokens) throws Exception {
        Token currToken = tokens.get(0);
        if(currToken.getTokenType() == TokenType.NUMBER){
            tokens.remove(0);
            this.number = currToken;
            this.negative = isNegative(currToken);
            return this;
        }else{
            throw new Exception("Token is not a number");
        }
    }
}
