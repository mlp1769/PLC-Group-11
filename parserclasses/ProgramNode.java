package parserclasses;

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
    public boolean validateTree() {
        return false;
    }

    public static ProgramNode parseProgramNodeProgram(ArrayList<Token> tokens) throws Exception {
        ArrayList<FunctionDefNode> functionDefs = new ArrayList<>();

        while (!tokens.isEmpty()) {
            functionDefs.add(FunctionDefNode.parseFunctionDefNode(tokens));
        }

        return new ProgramNode(functionDefs);
    }
}
