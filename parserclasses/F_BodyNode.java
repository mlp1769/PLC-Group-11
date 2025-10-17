package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class F_BodyNode implements JottTree {

    private ArrayList<Var_decNode> vars;
    private BodyNode body;

    public F_BodyNode(){
        this.vars = new ArrayList<Var_decNode>();
        this.body = new BodyNode();
    }

    public static F_BodyNode parseF_bodyNode(ArrayList<Token> tokens){
        while(tokens.get(0).getToken().equals("Double") || !tokens.get(0).getToken().equals("Integer") || tokens.get(0).getToken().equals("String") || tokens.get(0).getToken().equals("Boolean")){
            Var_decNode var = new Var_decNode();
            this.vars.add(var.parseVar_decNode(tokens));
        }
        this.body.parseBodyNode(tokens);
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (Var_decNode var : this.vars) {
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
