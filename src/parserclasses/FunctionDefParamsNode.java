package src.parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefParamsNode implements JottTree {
    private Token idToken;
    private TypeNode typeNode;
    private ArrayList<FunctionDefParamsNodeT> params;

    public FunctionDefParamsNode(Token idToken, TypeNode typeNode, ArrayList<FunctionDefParamsNodeT> params) {
        this.idToken = idToken;
        this.typeNode = typeNode;
        this.params = params;
    }

    public FunctionDefParamsNode(){
        this.idToken = null;
        this.typeNode = null;
        this.params = new ArrayList<>();
    }

    public boolean isEmpty(){
        return this.idToken == null && this.typeNode == null && this.params.isEmpty();
    }

    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens) throws Exception{
        Token idToken = tokens.remove(0);
        if(!idToken.getTokenType().equals(TokenType.ID_KEYWORD)){
            System.err.println("Syntax Error:");
            System.err.println("Expected id but got '"+idToken.getTokenType().toString().toLowerCase()+"' for function definition parameters");
            System.err.println(idToken.getFilename()+":"+idToken.getLineNum());
            throw new Exception();
        }

        Token colonToken = tokens.remove(0);
        if(!colonToken.getTokenType().equals(TokenType.COLON)){
            System.err.println("Syntax Error:");
            System.err.println("Expected colon but got '"+colonToken.getTokenType().toString().toLowerCase()+"' for function definition parameters");
            System.err.println(colonToken.getFilename()+":"+colonToken.getLineNum());
            throw new Exception();
        }

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        SymbolTable.addParam(idToken, typeNode.getType().getToken());

        ArrayList<FunctionDefParamsNodeT> params = new ArrayList<>();

        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.COMMA){
            params.add(FunctionDefParamsNodeT.parseFunctionDefParamsNodeT(tokens));
        }
        return new FunctionDefParamsNode(idToken, typeNode, params);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        if(this.idToken != null){
            sb.append(idToken.getToken()).append(":").append(typeNode.convertToJott());
        }
        for(FunctionDefParamsNodeT t : params){
            sb.append(t.convertToJott());
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws Exception{
        if(this.isEmpty()){
            return true;
        }
        if(typeNode.validateTree()){
            for(FunctionDefParamsNodeT moreParams: this.params){
                moreParams.validateTree();
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public Object execute() throws Exception {
        return null;
    }

}
