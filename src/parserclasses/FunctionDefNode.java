package src.parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {

    private Token defToken;
    private IDNode idToken;
    private FunctionDefParamsNode params;
    private FunctionReturnNode returnType;
    private FBodyNode body;

    public FunctionDefNode(Token defToken, IDNode idToken, FunctionDefParamsNode params, FunctionReturnNode returnType, FBodyNode body) {
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

        IDNode id = IDNode.parseIDNode(tokens);

        SymbolTable.addFunction(id.getID());

        SymbolTable.changeScope(id.getID());

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

        String type = returnType.convertToJott();

        SymbolTable.updateReturnType(type);

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
        return this.defToken.getToken() + " " + this.idToken.convertToJott() + "[" + this.params.convertToJott() + "]:" + this.returnType.convertToJott() + "{" + this.body.convertToJott() + "}";
    }

    @Override
    public boolean validateTree() throws Exception{
        SymbolTable.changeScope(this.idToken.getID());
        try {
            return idToken.validateTree() && params.validateTree() && returnType.validateTree() && body.validateTree();
        } catch (ExceptionInInitializerError e) {
            System.err.println(String.format("Semantic Error: %n %s has no valid return %n %s:%d%n",
                    this.idToken.getID().getToken(), this.idToken.getID().getFilename(), this.idToken.getID().getLineNum()));
            throw new Exception();
        }
    }

    @Override
    public Object execute() throws Exception {
        return null;
    }

    public Token getDefToken() {
        return defToken;
    }

    public IDNode getIdToken() {
        return idToken;
    }

    public void setIdToken(IDNode idToken) {
        this.idToken = idToken;
    }

    public void setDefToken(Token defToken) {
        this.defToken = defToken;
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
