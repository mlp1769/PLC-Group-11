package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;


public class FunctionCallNode implements OperandNode, BodyStmtNode, JottTree {
    
    private Token head;
    private IDNode id;
    private Token LB;
    private ParamsNode params;
    private Token RB;
    private Token semi;
    public FunctionCallNode(Token head, IDNode id, Token LB, ParamsNode params, Token RB, Token semi){
        this.head = head;
        this.id = id;
        this.LB = LB;
        this.params = params;
        this.RB = RB;
        this.semi = semi;
    }

    public static FunctionCallNode parseFunctionCallNode (ArrayList<Token> tokens) throws Exception{
        Token head = tokens.remove(0);
        if(head.getTokenType() != TokenType.FC_HEADER){
            System.err.println(String.format("Syntax Error %n Expected Function Header got %s %n %s:%d",head.getToken(),head.getFilename(),head.getLineNum()));
			throw new Exception();
        }
        IDNode id = IDNode.parseIDNode(tokens);
        Token LB = tokens.remove(0);
        if(LB.getTokenType() != TokenType.L_BRACKET){
            System.err.println(String.format("Syntax Error %n Expected Left Brace got %s %n %s:%d",LB.getToken(),LB.getFilename(),LB.getLineNum()));
            throw new Exception(); 
        }
        ParamsNode params = ParamsNode.parseParamsNode(tokens);
        Token RB = tokens.remove(0);
        if(RB.getTokenType() != TokenType.R_BRACKET){
            System.err.println(String.format("Syntax Error %n Expected Right Brace got %s %n %s:%d",RB.getToken(),RB.getFilename(),RB.getLineNum()));
            throw new Exception();
        }
        Token semi = tokens.remove(0);
        if(semi.getTokenType() != TokenType.SEMICOLON){
            System.err.println(String.format("Syntax Error %n Expected ; got %s %n %s:%d",semi.getToken(),semi.getFilename(),semi.getLineNum()));
            throw new Exception();
        }
        return new FunctionCallNode(head, id, LB, params, RB, semi);

    }

    @Override
    public String convertToJott() {
        return this.head.getToken() + this.id.convertToJott() + this.LB.getToken() + this.params.convertToJott() + this.RB.getToken() + this.semi.getToken();
    }
    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
