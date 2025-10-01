package parserclasses;
import java.util.ArrayList;
import package provided JottTree;
import package provided Token;
import provided.TokenType;

public interface BodyStatement implements JottTree{
    /**
     * Checks if the given node is an IfStatementNode, FunctionCallNode, WhileLoopNode, or AssignmentNode.
     * @param node The node to check.
     * @return true if the node matches one of the specified types, false otherwise.
     */
    boolean isBodyStatement(Node node);
}