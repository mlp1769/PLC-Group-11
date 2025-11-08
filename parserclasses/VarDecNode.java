package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    // < type > < id >;
    private TypeNode type;
    private IDNode id;

    public VarDecNode(TypeNode typeNode, IDNode idNode){
        this.type = typeNode;
        this.id = idNode;
    }

    public static VarDecNode parseVarDecNode (ArrayList<Token> tokens) throws Exception{

        TypeNode typeToPass;
        IDNode idToPass;

        //todo parse type
        typeToPass = TypeNode.parseTypeNode(tokens);

        //todo parse id
        idToPass = IDNode.parseIDNode(tokens);

        //todo check if first token is ';'
        if(!(tokens.get(0).getTokenType()== TokenType.SEMICOLON)){
            System.err.println("Syntax Error:\nMissing semicolon\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        //todo check if first letter of id is capital. If not, error
        if(Character.isUpperCase(idToPass.getID().getToken().charAt(0))){
            System.err.println("Semantic Error:\nAn 'id' must start with a capital letter\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }

        //add to symbol table
        SymbolTable.addVar(idToPass.getID(), typeToPass.getType().getToken());



        return new VarDecNode(typeToPass, idToPass);

    }

    @Override
    public String convertToJott() {

         return type.convertToJott()+" "+id.convertToJott()+";";

    }

    @Override
    public boolean validateTree() throws Exception {

        return (type.validateTree() && id.validateTree());
    }





}
