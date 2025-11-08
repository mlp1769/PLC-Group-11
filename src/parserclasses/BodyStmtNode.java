package src.parserclasses;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStmtNode extends JottTree{
    /**
     * Checks if the given node is an IfStatementNode, FunctionCallNode, WhileLoopNode, or AssignmentNode.
     * @param node The node to check.
     * @return true if the node matches one of the specified types, false otherwise.
     */

    static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.isEmpty()) {
            System.err.printf("Syntax Error %n Unexpected end of file in block %n <unknown>:1%n");
            throw new Exception();
        }

        Token t0 = tokens.get(0);

        if (t0.getTokenType() == TokenType.ID_KEYWORD) {
            String kw = t0.getToken();
            if ("If".equals(kw)) {
                return IfStmtNode.parseIfStmtNode(tokens);
            }
            if ("While".equals(kw)) {
                return WhileLoopNode.parseWhileLoopNode(tokens);
            }

            if (tokens.size() > 1 && tokens.get(1).getTokenType() == TokenType.ASSIGN) {
                return AsmtNode.parseAsmtNode(tokens);
            }
        }

        if (t0.getTokenType() == TokenType.FC_HEADER) {
            return FunctionCallNode.parseFunctionCallNode(tokens);
        }

        System.err.printf("Syntax Error %n Unknown statement start: '%s' %n %s:%d%n",
                t0.getToken(), t0.getFilename(), t0.getLineNum());
        throw new Exception();
    }
}