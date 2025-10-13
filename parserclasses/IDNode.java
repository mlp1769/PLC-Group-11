package parserclasses;
import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class IDNode implements JottTree{
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
        Token currToken = tokens.get(0);
        
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            IDNode id = new IDNode(currToken);
            return id;

        }
        else{
            throw new Exception("Token is not an ID");
        }
    }
    
}
