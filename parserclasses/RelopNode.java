package parserclasses;

import provided.TokenType;
import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;

public class RelopNode implements ExprNode, OperationNode {
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

    @Override
    public String getExpressionType() throws Exception {
        System.err.println(String.format("Syntax Error %n RELOP THIS METHOD SHOULD NOT BE CALLED %s %n %s:%d%n",
                    relOp.getToken(), relOp.getFilename(), relOp.getLineNum()));
        throw new Exception();
    }

    @Override
    public Boolean isMathOp() {
        return false;
    }

    @Override
    public Token getOperatorToken() {
        return this.relOp;
    }
}
