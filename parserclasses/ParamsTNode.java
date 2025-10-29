package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {

    private ExprNode expr;

    public ParamsTNode(ExprNode expr){
        this.expr = expr;
    }


    public static ParamsTNode parseParamsTNode (ArrayList<Token> tokens) throws Exception{
        ExprNode exprToPass;

        //todo check if first token is ','
        if(tokens.get(0).getTokenType() != TokenType.COMMA){
            System.err.println("Syntax Error\nMissing comma before a parameter\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        exprToPass = ExprNode.parseExprNode(tokens);


        return new ParamsTNode(exprToPass);

    }

    @Override
    public String convertToJott() {


        return ", "+this.expr.convertToJott();
    }

    @Override
    public boolean validateTree() throws Exception {
        return expr.validateTree();
    }
}
