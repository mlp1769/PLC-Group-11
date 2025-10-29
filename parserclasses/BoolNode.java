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
        return true;
    }

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getToken().equals("True") || currToken.getToken().equals("False")){
            return new BoolNode(currToken);

        }
        else{
            System.err.printf("Syntax Error %n Expected Bool got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
       
    }
    
}
