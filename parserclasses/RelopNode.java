package parserclasses;

import provided.TokenType;

public class RelopNode implements ExprNode {
    private Token relOp;

    public RelopNode(Token relOp){
        this.relOp = relOp;
    }

    @Override
    public String convertToJott() {
        return this.relop.getToken();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static parseRelopNode(ArrayList<Tokens> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType == TokenType.REL_OP){
            return new RelopNode(currToken);
        }
        else{
            throw new Exception("Invalid node");
        }

    }
    
}
