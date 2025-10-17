package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree {

    private ExpressionNode expressionNode;

    public ReturnStatementNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    public static ReturnStatementNode parseReturnStatementNode(ArrayList<Token> tokens) throws Exception{
        Token returnToken = tokens.remove(0);

        if(!(returnToken.getTokenType().equals(TokenType.ID_KEYWORD) && returnToken.getToken().equals("Return"))) {
            System.err.println("Syntax Error:");
            System.err.println("Expected keyword 'Return' but got '"+returnToken.getTokenType().toString().toLowerCase()+"' for return statement.");
            System.err.println(returnToken.getFilename()+":"+returnToken.getLineNum());
            throw new Exception();
        }

        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);

        Token semicolonToken = tokens.remove(0);
        if(!semicolonToken.getTokenType().equals(TokenType.SEMICOLON)) {
            System.err.println("Syntax Error:");
            System.err.println("Expected semicolon but got '"+semicolonToken.getTokenType().toString().toLowerCase()+"' for return statement.");
            System.err.println(semicolonToken.getFilename()+":"+semicolonToken.getLineNum());
            throw new Exception();
        }

        return new ReturnStatementNode(expressionNode);
    }

    @Override
    public String convertToJott() {
        return "Return " + this.expressionNode.convertToJott()+";" ;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
