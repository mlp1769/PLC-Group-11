package parserclasses;
import java.util.ArrayList;
import provided.Token;
import provided.TokenType;

public class MathopNode implements ExprNode{

    private Token mathOp;

    public MathopNode(Token mathOp){
        this.mathOp = mathOp;

    }

    @Override
    public String convertToJott() {
        return this.mathOp.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    public static MathopNode parseMathOpNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if(currToken.getTokenType() == TokenType.MATH_OP){
            return new MathopNode(currToken);
        }
        else{
            System.err.printf("Syntax Error %n Expected Math OP got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
    }
    
}
