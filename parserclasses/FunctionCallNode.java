package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;


public class FunctionCallNode implements OperandNode, BodyStmtNode{
    
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
            System.err.println(String.format("Syntax Error %n Expected Function Header got %s %n %s:%d",head.getToken(),head.getFilename(),head.getLineNum()));
			throw new Exception();
        }
        IDNode id = IDNode.parseIDNode(tokens);
        Token LB = tokens.remove(0);
        if(LB.getTokenType() != TokenType.L_BRACKET){
            System.err.println(String.format("Syntax Error %n Expected Left Brace got %s %n %s:%d",LB.getToken(),LB.getFilename(),LB.getLineNum()));
            throw new Exception(); 
        }

        Token scope = new Token(SymbolTable.getScope(),"s",1,TokenType.COLON);
        SymbolTable.changeScope(id.getID());
        ParamsNode params = ParamsNode.parseParamsNode(tokens);
        SymbolTable.changeScope(scope);
        Token RB = tokens.remove(0);
        if(RB.getTokenType() != TokenType.R_BRACKET){
            System.err.println(String.format("Syntax Error %n Expected Right Brace got %s %n %s:%d",RB.getToken(),RB.getFilename(),RB.getLineNum()));
            throw new Exception();
        }
        return new FunctionCallNode(head, id, LB, params, RB);

    }
    public Token getFunctionName(){
        return this.id.getID();
    }

    @Override
    public String convertToJott() {
        return this.head.getToken() + this.id.convertToJott() + this.LB.getToken() + this.params.convertToJott() + this.RB.getToken();
    }
    @Override
    public boolean validateTree() throws Exception{
        if(SymbolTable.getFunction(this.id.getID().getToken()) == null){
            System.err.println(String.format("Semantic Error: %n Function %s not declared %n %s:%d%n",
                    this.id.getID().getToken(), this.id.getID().getFilename(), this.id.getID().getLineNum()));
            throw new Exception();
        }else{
            return this.id.validateTree() && this.params.validateTree();
        }
    }

    @Override
    public String getExpressionType() throws Exception {
        return SymbolTable.getFunction(this.id.getID().getToken());
    }

    @Override
    public Token getToken() {
        return this.head;
    }
}
