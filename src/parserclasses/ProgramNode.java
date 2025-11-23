package src.parserclasses;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ProgramNode implements JottTree {
    private ArrayList<FunctionDefNode> functions;

    public ProgramNode(ArrayList<FunctionDefNode> functions) {
        this.functions = functions;
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(FunctionDefNode f : functions) {
            sb.append(f.convertToJott()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws Exception{
        boolean mainExists = false;

        for(FunctionDefNode function : this.functions){
            function.validateTree();

            Token functionNameNode = function.getIdToken().getID();

            if(functionNameNode.getToken().equals("main")){
                mainExists = true;

                if(!function.getReturnType().convertToJott().equals("Void")){
                    System.err.println("Semantic Error:");
                    System.err.println("Main must have a return type of Void");
                    System.err.println(functionNameNode.getFilename() + ":" + functionNameNode.getLineNum());
                    throw new Exception();
                }

                if(!function.getParams().isEmpty()){
                    System.err.println("Semantic Error:");
                    System.err.println("Main should not have parameters");
                    System.err.println(functionNameNode.getFilename() + ":" + functionNameNode.getLineNum());
                    throw new Exception();
                }
            }
        }

        if(!mainExists){
            System.err.println("Semantic Error:");
            System.err.println("Missing main function");
            throw new Exception();
        }

        return true;
    }

    @Override
    public Object exicute() throws Exception {
        return null;
    }

    public static ProgramNode parseProgramNodeProgram(ArrayList<Token> tokens) throws Exception {
        SymbolTable.clear();
        ArrayList<FunctionDefNode> functionDefs = new ArrayList<>();
        while (!tokens.isEmpty()) {
            functionDefs.add(FunctionDefNode.parseFunctionDefNode(tokens));
        }

        return new ProgramNode(functionDefs);
    }
}
