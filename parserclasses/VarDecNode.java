package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    // < type > < id >;
    private TypeNode type;
    private IdNode id;

    public VarDecNode(TypeNode typeNode, IdNode idNode){
        this.type = typeNode;
        this.id = idNode;
    }

    public static VarDecNode parseVarDecNode (ArrayList<Token> tokens) throws Exception{

        TypeNode typeToPass;
        IdNode idToPass;

        //todo parse type
        typeToPass = parseTypeNode(tokens);

        //todo parse id
        idToPass = parseIDNode(tokens);

        //todo check if first token is ';'
        if(!(tokens.get(0).getTokenType()== TokenType.SEMICOLON)){
            throw new Exception("Syntax Error\nMissing semicolon\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
        }
        tokens.remove(0);

        return new VarDecNode(typeToPass, idToPass);

    }

    @Override
    public String convertToJott() {

            return type.convertToJott()+" "+id.convertToJott()+";";

    }

    @Override
    public boolean validateTree() {
        return false;
    }





}
