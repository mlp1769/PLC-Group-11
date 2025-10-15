package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class BodyNode implements JottTree{

    private ArrayList<BodyStmtNode> body;
    private ReturnStmtNode rtn;
    
    public BodyNode(ArrayList<BodyStmtNode> body, ReturnStmtNode rtn){
        this.body = body;
        this.rtn = rtn;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens){
        ArrayList<BodyStmtNode> body = new ArrayList<BodyStmtNode>();
        ReturnStmtNode rtn;
        while(!tokens.isEmpty()){
            if(tokens.get(0).getToken().equals("Return")){
                rtn = ReturnStmtNode.parseReturnStmtNode(tokens);
                break;
            }else{
                BodyStmtNode line = new BodyStmtNode();
                body.add(line.parseBodyStmtNode(tokens));
            }
        }
        return new BodyNode(body, rtn);
    }
    @Override
    public String convertToJott() {
        String text = "";
        for (BodyStmtNode stmt : this.body) {
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
