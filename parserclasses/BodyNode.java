package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class BodyNode implements JottTree{

    private ArrayList<BodyStmtNode> body;
    private ReturnStatementNode rtn;
    
    public BodyNode(ArrayList<BodyStmtNode> body, ReturnStatementNode rtn){
        this.body = body;
        this.rtn = rtn;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception{
        ArrayList<BodyStmtNode> body = new ArrayList<BodyStmtNode>();
        ReturnStatementNode rtn = null;
        while(!tokens.isEmpty()){
            if(tokens.get(0).getToken().equals("Return")||tokens.get(0).getTokenType().equals(TokenType.R_BRACE)){
                rtn = ReturnStatementNode.parseReturnStatementNode(tokens);
                break;
            }else{
                BodyStmtNode stm = BodyStmtNode.parseBodyStmtNode(tokens);
                body.add(stm);
                if(stm instanceof FunctionCallNode){
                    Token semi = tokens.remove(0);
                    if(semi.getTokenType() != TokenType.SEMICOLON){
                        System.err.printf("Syntax Error %n Expected ; got '%s' %n %s:%d%n",
                            semi.getToken(), semi.getFilename(), semi.getLineNum());
                        throw new Exception();
                    }
                }
            }
        }
        return new BodyNode(body, rtn);
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (BodyStmtNode stmt : this.body) {
            if(stmt instanceof FunctionCallNode){
                text = text + stmt.convertToJott() + ";";
            }else{
                text = text + stmt.convertToJott();
            }
        }
        //if(this.rtn != null){
            return text + this.rtn.convertToJott();
        //}
        //return text;
    }

    public ReturnStatementNode getReturnStatementNode(){
        return this.rtn;
    }

    public ArrayList<BodyStmtNode> getBodyStmtNodes(){
        return this.body;
    }

    public Boolean returnsVoid(){
        return this.rtn.returnVoid() && returnsVoidBody();
    }

    public Boolean returnsVoidBody(){
        boolean noReturns = true;
        for (BodyStmtNode bodyStmt : this.body) {
            if(bodyStmt instanceof IfStmtNode){
                for (BodyNode bodyNode :((IfStmtNode) bodyStmt).getBodyNodes()){
                    noReturns = bodyNode.returnsVoid();
                }
            }
        }
        return noReturns;
    }

    @Override
    public boolean validateTree() throws Exception{
        for (BodyStmtNode stmt : this.body) {
            if(!stmt.validateTree()){
              return false;
            }
        }
        return this.rtn.validateTree();
    }
    
}
