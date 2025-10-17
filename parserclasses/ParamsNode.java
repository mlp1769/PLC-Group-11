package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamsNode implements JottTree {




    private ExprNode expr;
    private ArrayList<ParamsTNode> paramsT;

    public ParamsNode(ExprNode exprNode, ArrayList<ParamsTNode> paramsT){
        this.expr = exprNode;
        this.paramsT = paramsT;
    }

    public ParamsNode(){
        this.expr = null;
        this.paramsT = new ArrayList<>();
    }



    public static ParamsNode parseParamsNode (ArrayList<Token> tokens) throws Exception{

        ExprNode exprToPass;
        ArrayList<ParamsTNode> paramsToPass = new ArrayList<>();

        //todo check if the first token is ']'
        if(tokens.get(0).getTokenType()==TokenType.R_BRACKET){
            return new ParamsNode();
        }


        //todo else parse expr then parse params_t
        exprToPass = ExprNode.parseExprNode(tokens);

        //todo check if the first token is ',' to see if its params_t
        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.COMMA){
            ParamsTNode tokenIndexParamsT = ParamsTNode.parseParamsTNode(tokens);
            paramsToPass.add(tokenIndexParamsT);
        }
        
        return new ParamsNode(exprToPass, paramsToPass);


    }

    @Override
    public String convertToJott() {
        String text = "";
        if(expr != null){
            text += expr.convertToJott();
            for(int i=0; i<paramsT.size(); i++){
                text += paramsT.get(i).convertToJott();
            }
        }

        return text;
    }

    @Override
    public boolean validateTree() {
        return false;
    }


}
