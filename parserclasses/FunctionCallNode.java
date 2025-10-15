package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;


public class FunctionCallNode implements Operand, BodyStmtNode JottTree {
    
    private Token head;
    private IDNode id;
    private Token LB;
    private ParamsNode params;
    private Token RB;
    public FunctionCallNode(Token head, IDNode id, Token LB, ParamsNode params, Token RB){
        this.head = head;
        this.id = id;
        this.LB = LB;
        this.params = params;
        this.RB = RB;
    }

    public static FunctionCallNode parseFunctionCallNode (ArrayList<Token> tokens) throws Exception{
        Token head = tokens.remove(0);
        if(head.getTokenType() != TokenType.FC_HEADER){
			System.err.println(String.format("Syntax Error %n No Function Header %n %s:%d",this.head.getFilename(),this.head.getLineNum()));
        }
        IDNode id = IDNode.parseIDNode(tokens);
        Token LB = tokens.remove(0);
        if(LB.getTokenType() != TokenType.L_BRACE){
            System.err.println(String.format("Syntax Error %n No Left Brace %n %s:%d",this.LB.getFilename(),this.LB.getLineNum())); 
        }
        ParamsNode params = ParamsNode.parseParamsNode(tokens);
        Token RB = tokens.remove(0);
        if(RB.getTokenType() != TokenType.R_BRACE){
            System.err.println(String.format("Syntax Error %n No Right Brace %n %s:%d",this.RB.getFilename(),this.RB.getLineNum())); 
        }
        return new FunctionCallNode(head, id, LB, params, RB);

    }

    @Override
    public String convertToJott() {
        return this.head.getToken() + this.id.convertToJott() + this.LB.getToken() + this.params.convertToJott() + this.RB.getToken();
    }
    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
