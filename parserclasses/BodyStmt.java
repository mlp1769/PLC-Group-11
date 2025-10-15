package parserclasses;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStmt extends JottTree{
    /**
     * Checks if the given node is an IfStatementNode, FunctionCallNode, WhileLoopNode, or AssignmentNode.
     * @param node The node to check.
     * @return true if the node matches one of the specified types, false otherwise.
     */
}