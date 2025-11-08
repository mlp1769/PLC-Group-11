package src.parserclasses;

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
        return true;
    }

    public static StringLiteralNode parseStringLiteralNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.STRING){
            return new StringLiteralNode(currToken);

        }
        else{
            System.err.printf("Syntax Error: %n Expected String got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
         
    }

    @Override
    public String getExpressionType() throws Exception {
        return "String";
    }
}
