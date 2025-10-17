package parserclasses;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface ExprNode extends JottTree{
    
    static ExprNode parseExprNode(ArrayList<Token> tokens) throws Exception{
        
        Token currToken = tokens.get(0);
        if (currToken.getToken().equals("True") || currToken.getToken().equals("False")){
            return BoolNode.parseBoolNode(tokens);
            
        }
        else if (currToken.getTokenType() == TokenType.STRING){
            return StringLiteralNode.parseStringLiteralNode(tokens);

        }
        else if (currToken.getTokenType() == TokenType.ID_KEYWORD || currToken.getTokenType() == TokenType.NUMBER || currToken.getTokenType() == TokenType.FC_HEADER){
            OperandNode.parseOperandNode(tokens);
            Token nextToken = tokens.get(0);
            if (nextToken.getTokenType().equals(TokenType.MATH_OP)){
                MathopNode.parseMathOpNode(tokens);
                Token thirdToken = tokens.get(0);
                if (thirdToken.getTokenType() == TokenType.ID_KEYWORD || thirdToken.getTokenType() == TokenType.NUMBER || thirdToken.getTokenType() == TokenType.FC_HEADER){
                    OperandNode.parseOperandNode(tokens);
                }
                else{
                    throw new Exception("Invalid Node");
                }
            }
            else if (nextToken.getToken() == TokenType.REL_OP){
                RelopNode.parseRelopNode(tokens);
                Token thirdToken = tokens.get(0);
                if (thirdToken.getTokenType() == TokenType.ID_KEYWORD || thirdToken.getTokenType() == TokenType.NUMBER || thirdToken.getTokenType() == TokenType.FC_HEADER){
                    OperandNode.parseOperandNode(tokens);
                }
                else{
                    throw new Exception("Invalid Node");
                }

            }
            else{
                throw new Exception("Invalid Node");
            }   

        }
        else{
            throw new Exception("Invalid Node");
        }
    }

    
}
