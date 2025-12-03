package src.parserclasses;
import java.util.ArrayList;

import provided.Token;
import provided.TokenType;

public class IDNode implements OperandNode{
    private Token id;

    public IDNode(Token id){
        this.id = id;

    }

    public Token getID(){
        return this.id;
    }

    @Override
    public String convertToJott() {
        return this.id.getToken();
    }


    @Override
    public boolean validateTree() throws Exception{
        String name = this.id.getToken();
        if(!Character.isLowerCase(name.charAt(0))){

            System.err.printf("Semantic Error: %n %s id needs to start with a lowercase letter %n %s:%d%n", id.getToken(), id.getFilename(), id.getLineNum());
            throw new Exception();
        }

        if(SymbolTable.getVar(name) == null && SymbolTable.getFunction(name) == null){
            System.err.println(String.format("Semantic Error: %s is not defined", name));
            throw new Exception();
        }
        return true;
    }

    @Override
    public Object execute() throws Exception {
        return SymbolTable.getValue(this.id.getToken());
    }

    public static IDNode parseIDNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            if(currToken.getToken().equals("Def") || currToken.getToken().equals("Return") ||
        currToken.getToken().equals("If") || currToken.getToken().equals("Else") ||
        currToken.getToken().equals("Elseif") || currToken.getToken().equals("While") ||
        currToken.getToken().equals("Double") || currToken.getToken().equals("Integer") ||
        currToken.getToken().equals("String") || currToken.getToken().equals("Boolean") ||
        currToken.getToken().equals("Void") || currToken.getToken().equals("True") ||
        currToken.getToken().equals("False")){
            System.err.printf("Semantic Error: %n %s is a keyword %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
            return new IDNode(currToken);
        }
        else{
            System.err.printf("Syntax Error: %n Expected ID got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
    }
     @Override
    public String getExpressionType() throws Exception {
        return SymbolTable.getVar(this.id.getToken());
    }

    @Override
    public Token getToken() {
        return this.getID();
    }
}
