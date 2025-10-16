package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseifNode implements JottTree {
    // Elseif [ < expr >]{ < body >}

    private ExprNode expr;
    private BodyNode body;

    public ElseifNode(ExprNode exprNode, BodyNode bodyNode){
        this.expr = exprNode;
        this.body = bodyNode;
    }


    public static ElseifNode parseElseifNode (ArrayList<Token> tokens) throws Exception{

        ExprNode exprToPass;
        BodyNode bodyToPass;

        //todo check if first token is 'elseif'
        if(!(tokens.get(0).getToken().toLowerCase().equals("elseif"))){
            throw new Exception("'elseif' statement format: Elseif [ < expr >]{ < body >}");
        }
        tokens.remove(0);

        //todo check if first token is '['
        if(!(tokens.get(0).getTokenType()==TokenType.L_BRACE)){
            throw new Exception("'elseif' statement format: Elseif [ < expr >]{ < body >}");
        }
        tokens.remove(0);

        //todo parse expr
        exprToPass = parseExprNode(tokens);

        //todo check if first token is ']'
        if(!(tokens.get(0).getTokenType()==TokenType.R_BRACE)){
            throw new Exception("'elseif' statement format: Elseif [ < expr >]{ < body >}");
        }
        tokens.remove(0);





        //todo check if first token is '{'
        if(!(tokens.get(0).getTokenType()==TokenType.L_BRACKET)){
            throw new Exception("'elseif' statement format: Elseif [ < expr >]{ < body >}");
        }
        tokens.remove(0);

        //todo parse body
        bodyToPass = parseBodyNode(tokens);

        //todo check if first token is '}'
        if(!(tokens.get(0).getTokenType()==TokenType.R_BRACKET)){
            throw new Exception("'elseif' statement format: Elseif [ < expr >]{ < body >}");
        }
        tokens.remove(0);

        return new ElseifNode(exprToPass, bodyToPass);

    }

    @Override
    public String convertToJott() {
        return "Elseif [ "+expr.convertToJott()+"]{ "+body.convertToJott()+"}";
    }

    @Override
    public boolean validateTree() {
        return false;
    }











}
