package src.parserclasses;

import provided.JottTree;
import provided.Token;

public interface OperationNode extends JottTree {

    public Boolean isMathOp();

    public Token getOperatorToken();

}
