package parserclasses;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree {

    ExpressionNode expressionNode;

    public ReturnStatementNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    public static ReturnStatementNode parseReturnStatementNode(ArrayList<Token> tokens) throws Exception{
        Token returnToken = tokenizer.remove(0);

        if(!returnToken.getToken().equals("Return")) {
            throw new Exception("Missing return statement");
        }

        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);

        return new ReturnStatementNode(expressionNode);
    }

    @Override
    public String convertToJott() {
        return "";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
