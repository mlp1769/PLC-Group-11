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
        if(tokens.get(0).getTokenType()== TokenType.COMMA){
            throw new Exception("parseParamsTNode was called on something that doesn't start with a ','");
        }
        tokens.remove(0);

        exprToPass = parseExprNode(tokens);


        return new ParamsTNode(exprToPass);

    }

    @Override
    public String convertToJott() {


        return ", "+this.expr.convertToJott();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
