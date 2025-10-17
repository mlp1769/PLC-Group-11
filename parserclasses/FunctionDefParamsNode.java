package parserclasses;

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

    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens) throws Exception{
        Token idToken = tokens.remove(0);
        if(!idToken.getTokenType().equals(TokenType.ID_KEYWORD)){
            throw new Exception("Expected id Token");
        }

        Token colonToken = tokens.remove(0);
        if(!colonToken.getTokenType().equals(TokenType.COLON)){
            throw new Exception("Expected colon Token");
        }

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        ArrayList<FunctionDefParamsNodeT> params = new ArrayList<>();

        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.COMMA){
            params.add(FunctionDefParamsNodeT.parseFunctionDefParamsNodeT(tokens));
        }
        return new FunctionDefParamsNode(idToken, typeNode, params);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(idToken.getToken()).append(":").append(typeNode.convertToJott());
        for(FunctionDefParamsNodeT t : params){
            sb.append(t.convertToJott());
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() {
        return false;
    }

}
