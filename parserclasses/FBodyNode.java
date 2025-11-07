package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

import javax.crypto.ExemptionMechanismException;

import org.w3c.dom.events.EventException;

public class FBodyNode implements JottTree {

    private ArrayList<VarDecNode> vars;
    private BodyNode body;

    public FBodyNode(ArrayList<VarDecNode> vars,BodyNode body){
        this.vars = vars;
        this.body = body;
    }

    public static FBodyNode parseFBodyNode(ArrayList<Token> tokens) throws Exception{
        ArrayList<VarDecNode> vars = new ArrayList<VarDecNode>();
        while(tokens.get(0).getToken().equals("Double") || tokens.get(0).getToken().equals("Integer") || tokens.get(0).getToken().equals("String") || tokens.get(0).getToken().equals("Boolean")){
            vars.add(VarDecNode.parseVarDecNode(tokens));
        }
        BodyNode bodyToPass = BodyNode.parseBodyNode(tokens);
        return new FBodyNode(vars, bodyToPass);
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (VarDecNode var : this.vars) {
            text = text + var.convertToJott();
        }
        return text + this.body.convertToJott();
    }
    
    @Override
    public boolean validateTree() throws Exception{
        if(SymbolTable.getFunction(SymbolTable.getScope()).equals("Void")){
            if(!validVoid()){
                throw new ExceptionInInitializerError();
            }
        }else{
            if(!validReturn()){
                throw new ExceptionInInitializerError();
            }
        }
        for (VarDecNode stmt : this.vars) {
            if(!stmt.validateTree()){
              return false;
            }
        }
        return this.body.validateTree();
    }

    private boolean validVoid(){
        return this.body.getReturnStatementNode().returnVoid() && validVoidBody(this.body.getBodyStmtNodes());
    }

    private boolean validVoidBody(ArrayList<BodyStmtNode> body){
        boolean noReturns = true;
        for (BodyStmtNode bodyStmt : body) {
            if(bodyStmt instanceof IfStmtNode){
                for (BodyNode bodyNode :((IfStmtNode) bodyStmt).getBodyNodes()) if(!bodyNode.returnsVoid()) noReturns = false;
            }
        }
        return noReturns;
    }

    private boolean validReturn(){
        return !this.body.getReturnStatementNode().returnVoid() || validReturnBody(this.body.getBodyStmtNodes());
    }

    private boolean validReturnBody(ArrayList<BodyStmtNode> bodyNode){
        Boolean validReturn = false;
        for (BodyStmtNode bodyStmt : bodyNode) {
            if(bodyStmt instanceof IfStmtNode){
                ArrayList<BodyNode> body = ((IfStmtNode)bodyStmt).getBodyNodes();
                if(!body.get(body.size()-1).returnsVoid())
                    for (BodyNode ifBody : body) if(!ifBody.returnsVoid()){validReturn = true;} else{validReturn = validReturnBody(ifBody.getBodyStmtNodes());} //Evil for each if all loop
            }
        }
        return validReturn;
    }
    
}
