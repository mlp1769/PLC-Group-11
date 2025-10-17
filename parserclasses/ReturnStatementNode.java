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

        if(!returnToken.getTokenType().equals(TokenType.ID_KEYWORD) || !returnToken.getToken().equals("Return")) {
            throw new Exception("Missing return statement");
        }

        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);

        Token semicolonToken = tokens.remove(0);
        if(!semicolonToken.getTokenType().equals(TokenType.SEMICOLON)) {
            throw new Exception("Missing semicolon");
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
