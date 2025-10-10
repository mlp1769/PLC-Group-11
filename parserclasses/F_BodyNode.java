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

    public F_bodyNode parseF_bodyNode(ArrayList<Token> tokens){
        
    }
    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
