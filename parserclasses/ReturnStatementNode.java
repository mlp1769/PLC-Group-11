package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree {

    private ExprNode expressionNode;

    public ReturnStatementNode(ExprNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    public static ReturnStatementNode parseReturnStatementNode(ArrayList<Token> tokens) throws Exception{
        Token returnToken = tokens.get(0);

        if(!(returnToken.getTokenType().equals(TokenType.ID_KEYWORD) && returnToken.getToken().equals("Return"))) {
            return new ReturnStatementNode(null);
        }
        tokens.remove(0);
        ExprNode expressionNode = ExprNode.parseExprNode(tokens);

        Token semicolonToken = tokens.remove(0);
        if(!semicolonToken.getTokenType().equals(TokenType.SEMICOLON)) {
            System.err.println("Syntax Error:");
            System.err.println("Expected semicolon but got '"+semicolonToken.getTokenType().toString().toLowerCase()+"' for return statement.");
            System.err.println(semicolonToken.getFilename()+":"+semicolonToken.getLineNum());
            throw new Exception();
        }

        String returnType = SymbolTable.getFunction(SymbolTable.getScope());

        if(!expressionNode.getExpressionType().equals(returnType)) {
            System.err.println("Semantic Error:");
            System.err.println("Return type does not match function return type.");
            System.err.println(returnToken.getFilename()+":"+returnToken.getLineNum());
            throw new Exception();
        }

        return new ReturnStatementNode(expressionNode);
    }

    @Override
    public String convertToJott() {
        if (this.expressionNode == null) {
            return "";
        }
        return "Return " + this.expressionNode.convertToJott()+";" ;
    }

    @Override
    public boolean validateTree() throws Exception{
        return expressionNode.validateTree();
    }
}
