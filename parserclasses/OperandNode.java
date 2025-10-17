package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;



public interface OperandNode extends ExprNode{

    public static OperandNode parseOperandNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.get(0);
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            return IDNode.parseIDNode(tokens);

        }
        else if (currToken.getTokenType() == TokenType.NUMBER){
            return NumberNode.parseNumberNode(tokens);

        }
        else if (currToken.getTokenType() == TokenType.FC_HEADER){
            return FunctionCallNode.parseFunctionCallNode(tokens);

        }
        else if(currToken.getTokenType() == TokenType.MATH_OP){
            String mathop = currToken.getToken();
            if(mathop.equals("-")){
                if(tokens.get(1).getTokenType() == TokenType.NUMBER){
                    tokens.remove(1);
                    Token newToken = new Token(currToken.getToken() + tokens.get(1), currToken.getFilename(), currToken.getLineNum(), TokenType.NUMBER);
                    tokens.add(0, newToken);
                    return NumberNode.parseNumberNode(tokens);
                }
            }
            
        }
        throw new Exception("Invalid Node");
    }
}