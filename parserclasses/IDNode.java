package parserclasses;
import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import parserclasses.*;

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
        if((Character.isLowerCase(name.charAt(name.length() - 1))) && (SymbolTable.getVar(name) == null)){
            System.err.println(String.format("Semantic Error: Var %s is not in symbol table", name));
            throw new Exception();
        }
        return true;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.remove(0);
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            return new IDNode(currToken);

        }
        else{
            System.err.printf("Syntax Error %n Expected ID got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
    }
    
}
