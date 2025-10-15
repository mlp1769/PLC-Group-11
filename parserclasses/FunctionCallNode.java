package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;


public class FunctionCallNode implements BodyStmt,OperandNode {
    
    private Token head;
    private IDNode id;
    private Token LB;
    private ParamsNode params;
    private Token RB;
    public FunctionCallNode(){
        this.id = new IDNode();
        this.params = new ParamsNode();
    }

    public FunctionCallNode parseFunctionCallNode (ArrayList<Token> tokens) throws Exception{
        this.head = tokens.remove(0);
        if(head.getTokenType() != TokenType.FC_HEADER){
			System.err.println(String.format("Syntax Error %n No Function Header %n %s:%d",this.head.getFilename(),this.head.getLineNum()));
        }
        this.id.parseIDNode(tokens);
        this.LB = tokens.remove(0);
        if(head.getTokenType() != TokenType.L_BRACE){
            System.err.println(String.format("Syntax Error %n No Left Brace %n %s:%d",this.LB.getFilename(),this.LB.getLineNum())); 
        }
        this.params.parseParamsNode(tokens);
        this.RB = tokens.remove(0);
        if(head.getTokenType() != TokenType.R_BRACE){
            System.err.println(String.format("Syntax Error %n No Right Brace %n %s:%d",this.RB.getFilename(),this.RB.getLineNum())); 
        }
        return this;

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
