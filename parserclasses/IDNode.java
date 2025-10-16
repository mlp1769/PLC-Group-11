package parserclasses;
import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import parserclasses.*;

public class IDNode implements OperandNode{
    private Token id;

    public IDNode(Token id){
        this.id = id;

    }

    @Override
    public String convertToJott() {
        return this.id.getToken();
    }


    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static IDNode parseIDNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            return new IDNode(currToken);

        }
        else{
            throw new Exception("Token is not an ID");
        }
    }
    
}
