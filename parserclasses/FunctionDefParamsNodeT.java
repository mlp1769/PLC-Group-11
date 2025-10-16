package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefParamsNodeT implements JottTree {

    private Token idToken;
    private TypeNode typeNode;

    public FunctionDefParamsNodeT(Token idToken, TypeNode typeNode) {
        this.idToken = idToken;
        this.typeNode = typeNode;
    }

    public static FunctionDefParamsNodeT parseFunctionDefParamsNodeT(ArrayList<Token> tokens) throws Exception{
        Token commaToken = tokens.remove(0);
        if(!commaToken.getTokenType().equals(TokenType.COMMA)){
            throw new Exception("Expected comma token");
        }

        Token idToken = tokens.remove(0);
        if(!idToken.getTokenType().equals(TokenType.ID_KEYWORD)){
            throw new Exception("Expected id token");
        }

        Token colonToken = tokens.remove(0);
        if(!colonToken.getTokenType().equals(TokenType.COLON)){
            throw new Exception("Expected colon");
        }

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        return new FunctionDefParamsNodeT(idToken, typeNode);
    }

    @Override
    public String convertToJott() {
        return ", " + this.idToken.getToken()+":"+this.typeNode.convertToJott();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
