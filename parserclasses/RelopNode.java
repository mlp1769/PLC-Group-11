package parserclasses;

import provided.TokenType;
import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

public class RelopNode implements ExprNode {
    private Token relOp;

    public RelopNode(Token relOp){
        this.relOp = relOp;
    }

    @Override
    public String convertToJott() {
        return this.relOp.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    public static RelopNode parseRelopNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.REL_OP){
            return new RelopNode(currToken);
        }
        else{
            System.err.printf("Syntax Error %n Real OP String got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }

    }
    
}
