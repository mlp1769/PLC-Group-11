package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class FBodyNode implements JottTree {

    private ArrayList<VardecNode> vars;
    private BodyNode body;

    public FBodyNode(ArrayList<VardecNode> vars,BodyNode body){
        this.vars = vars;
        this.body = body;
    }

    public static FBodyNode parseFBodyNode(ArrayList<Token> tokens) throws Exception{
        ArrayList<VardecNode> vars;
        while(tokens.get(0).getToken().equals("Double") || tokens.get(0).getToken().equals("Integer") || tokens.get(0).getToken().equals("String") || tokens.get(0).getToken().equals("Boolean")){
            vars.add(VardecNode.parseVardecNode(tokens));
        }
        return new FBodyNode(vars, BodyNode.parseBodyNode(tokens));
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (VardecNode var : this.vars) {
            text = text + var.convertToJott();
        }
        return text + this.body.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
