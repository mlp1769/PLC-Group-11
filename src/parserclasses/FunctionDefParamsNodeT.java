package src.parserclasses;

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
            System.err.println("Syntax Error:");
            System.err.println("Expected comma but got '"+commaToken.getTokenType().toString().toLowerCase()+"' for additional function definition parameters");
            System.err.println(commaToken.getFilename()+":"+commaToken.getLineNum());
            throw new Exception();
        }

        Token idToken = tokens.remove(0);
        if(!idToken.getTokenType().equals(TokenType.ID_KEYWORD)){
            System.err.println("Syntax Error:");
            System.err.println("Expected id but got '"+idToken.getTokenType().toString().toLowerCase()+"' for additional function definition parameters");
            System.err.println(idToken.getFilename()+":"+idToken.getLineNum());
            throw new Exception();
        }

        Token colonToken = tokens.remove(0);
        if(!colonToken.getTokenType().equals(TokenType.COLON)){
            System.err.println("Syntax Error:");
            System.err.println("Expected colon but got '"+colonToken.getTokenType().toString().toLowerCase()+"' for additional function definition parameters");
            System.err.println(colonToken.getFilename()+":"+colonToken.getLineNum());
            throw new Exception();
        }

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        SymbolTable.addVar(idToken, typeNode.getType().getToken());
        SymbolTable.addParam(typeNode.getType().getToken());

        return new FunctionDefParamsNodeT(idToken, typeNode);
    }

    @Override
    public String convertToJott() {
        return ", " + this.idToken.getToken()+":"+this.typeNode.convertToJott();
    }

    @Override
    public boolean validateTree() {
        return typeNode.validateTree();
    }
}
