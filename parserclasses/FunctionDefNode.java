package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {

    private Token defToken;
    private Token idToken;
    private FunctionDefParamsNode params;
    private FunctionReturnNode returnType;
    private F_BodyNode body;

    public FunctionDefNode(Token defToken, Token idToken, FunctionDefParamsNode params, FunctionReturnNode returnType, F_BodyNode body) {
        this.defToken = defToken;
        this.idToken = idToken;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception{
        Token def = tokens.remove(0);

        if(!(def.getTokenType() == TokenType.ID_KEYWORD && def.getToken().equals("Def"))){
            throw new Exception("Expected 'Def' keyword, found: " + def.getToken());
        }

        Token id = tokens.remove(0);

        if (id.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Exception("Expected function name, found: " + id.getToken());
        }

        Token lBracket = tokens.remove(0);
        if (lBracket.getTokenType() != TokenType.L_BRACKET) {
            throw new Exception("Expected '[', found: " + lBracket.getToken());
        }

        FunctionDefParamsNode params = null;
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
            params = new FunctionDefParamsNode();
        }else{
            params = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
        }

        Token rBracket = tokens.remove(0);
        if (rBracket.getTokenType() != TokenType.R_BRACKET) {
            throw new Exception("Expected ']', found: " + rBracket.getToken());
        }

        Token colon = tokens.remove(0);
        if (colon.getTokenType() != TokenType.COLON) {
            throw new Exception("Expected ':', found: " + colon.getToken());
        }

        FunctionReturnNode returnType = FunctionReturnNode.parseFunctionReturnNode(tokens);

        Token lBrace = tokens.remove(0);
        if (lBrace.getTokenType() != TokenType.L_BRACE) {
            throw new Exception("Expected '{', found: " + lBrace.getToken());
        }

        F_BodyNode body = F_BodyNode.parseF_bodyNode(tokens);

        Token rBrace = tokens.remove(0);
        if (rBrace.getTokenType() != TokenType.R_BRACE) {
            throw new Exception("Expected '}', found: " + rBrace.getToken());
        }

        return new FunctionDefNode(def, id, params, returnType, body);
    }

    @Override
    public String convertToJott() {
        return this.defToken.getToken() + " " + this.idToken.getToken() + "[" + this.params.convertToJott() + "]:" + this.returnType.convertToJott() + " {" + this.body.convertToJott() + "}";
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    public Token getDefToken() {
        return defToken;
    }

    public void setDefToken(Token defToken) {
        this.defToken = defToken;
    }

    public Token getIdToken() {
        return idToken;
    }

    public void setIdToken(Token idToken) {
        this.idToken = idToken;
    }

    public FunctionDefParamsNode getParams() {
        return params;
    }

    public void setParams(FunctionDefParamsNode params) {
        this.params = params;
    }

    public F_BodyNode getBody() {
        return body;
    }

    public void setBody(F_BodyNode body) {
        this.body = body;
    }

    public FunctionReturnNode getReturnType() {
        return returnType;
    }

    public void setReturnType(FunctionReturnNode returnType) {
        this.returnType = returnType;
    }
}
