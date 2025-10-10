package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class BodyNode implements JottTree{

    private ArrayList<Body_stmtNode> body;
    private Return_stmtNode rtn;
    
    public BodyNode(){
        this.body = new ArrayList<Body_stmtNode>();
        this.rtn = new Return_stmtNode();
    }

    public BodyNode parseBodyNode(ArrayList<Token> tokens){
        while(!tokens.isEmpty()){
            if(tokens.get(0).getToken().equals("Return")){
                this.rtn.parseReturn_stmtNode(tokens);
                break;
            }else{
                Body_stmtNode line = new Body_stmtNode();
                this.body.add(line.parseBody_stmtNode(tokens));
            }
        }
        return this;
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (Body_stmtNode stmt : this.body) {
            text = text + stmt.convertToJott;
        }
        return text + this.rtn.convertToJott;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
