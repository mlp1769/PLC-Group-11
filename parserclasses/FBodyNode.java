package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

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
        return new FBodyNode(vars, BodyNode.parseBodyNode(tokens));
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
        for (VarDecNode stmt : this.vars) {
            if(!stmt.validateTree()){
              return false;
            }
        }
        return this.body.validateTree();
    }
    
}
