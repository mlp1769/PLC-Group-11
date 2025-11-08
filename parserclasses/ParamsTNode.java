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
            System.err.println("Syntax Error:\nMissing comma before a parameter\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        exprToPass = ExprNode.parseExprNode(tokens);

        //take head from paramsTable
        String paramsHead = SymbolTable.getParam();

        //check if paramsHead == null; if it is, throw error
        if(paramsHead==null){
            System.err.println("Semantic Error:\nThe number of provided params doesn't match the number of expected params\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }


        //check if paramsHead == the type of expr
        if(!(exprToPass.getExpressionType().equals(paramsHead) || paramsHead.equals("All"))) {
            System.err.println("Semantic Error:\nParam type does not match function param type.\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }


        return new ParamsTNode(exprToPass);

    }

    @Override
    public String convertToJott() {


        return ", "+this.expr.convertToJott();
    }

    @Override
    public boolean validateTree() throws Exception {
        if(!(expr==null)){
            return expr.validateTree();
        }
        return true;
    }
}
