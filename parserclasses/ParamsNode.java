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
        this.paramsT = null;
    }



    public static ParamsNode parseParamsNode (ArrayList<Token> tokens) throws Exception{

        ExprNode exprToPass = null;
        ArrayList<ParamsTNode> paramsToPass = new ArrayList<>();

        //duplicate paramsList; take head from paramsTable
        String paramsHead = SymbolTable.getParam();

        //todo check if the first token is ']'
        if(tokens.get(0).getTokenType()==TokenType.R_BRACKET && paramsHead==null){
            return new ParamsNode();
        }

        //check if paramsHead == null; if it is, throw error
        if(tokens.get(0).getTokenType()==TokenType.R_BRACKET&&paramsHead!=null){
            System.err.println("Semantic Error\nThe number of provided params doesn't match the number of expected params\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }

        //check if paramsHead == null; if it is, throw error
        if(paramsHead==null){
            System.err.println("Semantic Error\nThe number of provided params doesn't match the number of expected params\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }

        //todo else parse expr then parse params_t
        exprToPass = ExprNode.parseExprNode(tokens);

        //check if paramsHead == the type of expr
        if(!(exprToPass.getExpressionType().equals(paramsHead) || paramsHead.equals("All"))) {
            System.err.println("Semantic Error:\nParam type does not match function param type.\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }

        //todo check if the first char in the token is ',' to see if its params_t
        boolean keepCheckingForParamsT = true;
        while(keepCheckingForParamsT){
            if(tokens.get(0).getToken().charAt(0)==','){
                ParamsTNode tokenIndexParamsT = ParamsTNode.parseParamsTNode(tokens);
                paramsToPass.add(tokenIndexParamsT);
                tokens.remove(0);
            }else{
                keepCheckingForParamsT=false;
            }
        }
        if(SymbolTable.getCopyTable().size()!=0){
            System.err.println("Semantic Error\nThe number of provided params doesn't match the number of expected params\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }




        return new ParamsNode(exprToPass, paramsToPass);


    }

    @Override
    public String convertToJott() {
        String text = "";
        if(this.expr != null){
            text += expr.convertToJott();
        }
        if(paramsT.size()>0){
            for(int i=0; i<paramsT.size(); i++){
                text += " " + paramsT.get(i).convertToJott();
            }
        }

        return text;
    }

    @Override
    public boolean validateTree() throws Exception{
        if(!(expr==null)){
            if(expr.validateTree()){
                for(int i=0; i<paramsT.size(); i++){
                    paramsT.get(i).validateTree();
                }
            }
        }
        return true;
    }


}
