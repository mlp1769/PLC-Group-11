package parserclasses;

import provided.Token;
import provided.TokenType;

public class MathopNode implements ExprNode{

    private mathOp;

    public MathopNode(Token mathOp){
        this.mathOp = mathOp;

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

    public static parseMathOpNode(ArrayList<Tokens> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.MATH_OP){
            return new MathopNode(currToken);
        }
        else{
            throw new Exception("Invalid Node");
        }
    }
    
}
