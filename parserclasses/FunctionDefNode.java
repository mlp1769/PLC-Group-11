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
    private FBodyNode body;

    public FunctionDefNode(Token defToken, Token idToken, FunctionDefParamsNode params, FunctionReturnNode returnType, FBodyNode body) {
        this.defToken = defToken;
        this.idToken = idToken;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception{
        Token def = tokens.remove(0);

        if(!(def.getTokenType() == TokenType.ID_KEYWORD && def.getToken().equals("Def"))){
            System.err.println("Syntax Error:");
            System.err.println("Expected keyword 'Def' but got '"+def.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(def.getFilename()+":"+def.getLineNum());
            throw new Exception();
        }

        Token id = tokens.remove(0);

        if (id.getTokenType() != TokenType.ID_KEYWORD) {
            System.err.println("Syntax Error:");
            System.err.println("Expected id but got '"+id.getTokenType().toString().toLowerCase()+"' for function name");
            System.err.println(id.getFilename()+":"+id.getLineNum());
            throw new Exception();
        }

        Token lBracket = tokens.remove(0);
        if (lBracket.getTokenType() != TokenType.L_BRACKET) {
            System.err.println("Syntax Error:");
            System.err.println("Expected left bracket but got '"+lBracket.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(lBracket.getFilename()+":"+lBracket.getLineNum());
            throw new Exception();
        }

        FunctionDefParamsNode params = null;
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
            params = new FunctionDefParamsNode();
        }else{
            params = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
        }

        Token rBracket = tokens.remove(0);
        if (rBracket.getTokenType() != TokenType.R_BRACKET) {
            System.err.println("Syntax Error:");
            System.err.println("Expected right bracket but got '"+rBracket.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(rBracket.getFilename()+":"+rBracket.getLineNum());
            throw new Exception();
        }

        Token colon = tokens.remove(0);
        if (colon.getTokenType() != TokenType.COLON) {
            System.err.println("Syntax Error:");
            System.err.println("Expected colon but got '"+colon.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(colon.getFilename()+":"+colon.getLineNum());
            throw new Exception();
        }

        FunctionReturnNode returnType = FunctionReturnNode.parseFunctionReturnNode(tokens);

        Token lBrace = tokens.remove(0);
        if (lBrace.getTokenType() != TokenType.L_BRACE) {
            System.err.println("Syntax Error:");
            System.err.println("Expected left brace but got '"+lBrace.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(lBrace.getFilename()+":"+lBrace.getLineNum());
            throw new Exception();
        }

        FBodyNode body = FBodyNode.parseFBodyNode(tokens);

        Token rBrace = tokens.remove(0);
        if (rBrace.getTokenType() != TokenType.R_BRACE) {
            System.err.println("Syntax Error:");
            System.err.println("Expected right brace but got '"+rBrace.getTokenType().toString().toLowerCase()+"' for function definition");
            System.err.println(rBrace.getFilename()+":"+rBrace.getLineNum());
            throw new Exception();
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

    public FBodyNode getBody() {
        return body;
    }

    public void setBody(FBodyNode body) {
        this.body = body;
    }

    public FunctionReturnNode getReturnType() {
        return returnType;
    }

    public void setReturnType(FunctionReturnNode returnType) {
        this.returnType = returnType;
    }
}
