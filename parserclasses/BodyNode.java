package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class BodyNode implements JottTree{

    private ArrayList<Body_stmtNode> body;
    private Return_stmtNode rtn;
    
    public BodyNode(ArrayList<Body_stmtNode> body, Return_stmtNode rtn){
        this.body = body;
        this.rtn = rtn;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens){
        ArrayList<Body_stmtNode> body = new ArrayList<Body_stmtNode>();
        Return_stmtNode rtn;
        while(!tokens.isEmpty()){
            if(tokens.get(0).getToken().equals("Return")){
                rtn = Return_stmtNode.parseReturn_stmtNode(tokens);
                break;
            }else{
                Body_stmtNode line = new Body_stmtNode();
                body.add(line.parseBody_stmtNode(tokens));
            }
        }
        return new BodyNode(body, rtn);
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (Body_stmtNode stmt : this.body) {
            text = text + stmt.convertToJott();
        }
        return text + this.rtn.convertToJott();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
