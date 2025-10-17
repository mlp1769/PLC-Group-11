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

    }



    public static ParamsNode parseParamsNode (ArrayList<Token> tokens) throws Exception{

        ExprNode exprToPass;
        ArrayList<ParamsTNode> paramsToPass = new ArrayList<>();



        //todo check if the first token is ']'
        if(tokens.get(0).getTokenType()==TokenType.R_BRACE){
            return new ParamsNode();
        }


        //todo else parse expr then parse params_t
        exprToPass = parseExprNode(tokens);

        //todo check if the first char in the token is ',' to see if its params_t
        boolean keepCheckingForParamsT = true;
        while(keepCheckingForParamsT){
            if(tokens.get(0).getToken().charAt(0)==','){
                ParamsTNode tokenIndexParamsT = parseParamsTNode(tokens);
                paramsToPass.add(tokenIndexParamsT);
                tokens.remove(0);
            }else{
                keepCheckingForParamsT=false;
            }
        }
        return new ParamsNode(exprToPass, paramsToPass);


    }

    @Override
    public String convertToJott() {
        String text = "";
        if(paramsT.size()>0){
            text += expr.convertToJott();
            for(int i=0; i<paramsT.size(); i++){
                text += " " + paramsT.get(i).convertToJott();
            }
        }

        return text;
    }

    @Override
    public boolean validateTree() {
        return false;
    }


}
