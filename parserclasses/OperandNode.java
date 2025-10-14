package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;



public interface OperandNode extends JottTree{

    static OperandNode parseOperandNode(ArrayList<Token> tokens) throws Exception{
        Token currToken = tokens.get(0);
        if (currToken.getTokenType() == TokenType.ID_KEYWORD){
            IDNode id = new IDNode(currToken);
            id.parseIDNode(tokens);
            return id;

        }
        else if (currToken.getTokenType() == TokenType.NUMBER){
            //Check if negative 
            //Put flag in number node
            double num = Integer.parseInt(currToken.getToken());
            NumberNode number;
            if(num < 0){
                number = new NumberNode(currToken, true);
            }
            else{
                number = new NumberNode(currToken, false);
            }
            number.parseNumberNode(tokens);
            return number;

        }
        else if (currToken.getTokenType() == TokenType.FC_HEADER){
            FunctionCallNode func = new FunctionCallNode();
            func.parseFunctionCallNode(tokens);
            return func;

        }
        else{
            throw new Exception("Invalid Node");
        }
    }
}