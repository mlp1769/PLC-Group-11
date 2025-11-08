package parserclasses;
import java.util.ArrayList;
import provided.Token;
import provided.TokenType;

public class MathopNode implements ExprNode, OperationNode{

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

    @Override
    public String getExpressionType() throws Exception {
        System.err.println(String.format("Syntax Error %n MATHOP THIS METHOD SHOULD NOT BE CALLED %s %n %s:%d%n",
                    mathOp.getToken(), mathOp.getFilename(), mathOp.getLineNum()));
        throw new Exception();
    }

    @Override
    public Boolean isMathOp() {
        return true;
    }

    @Override
    public Token getOperatorToken() {
        return this.mathOp;
    }
}
