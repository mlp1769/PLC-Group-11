package src.parserclasses;

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
            OperandNode leftOp = OperandNode.parseOperandNode(tokens);

            if(tokens.isEmpty()){
                return leftOp;
            }
            Token nextToken = tokens.get(0);

            if(nextToken.getTokenType() == TokenType.REL_OP){
                RelopNode relop = RelopNode.parseRelopNode(tokens);
                OperandNode rightOp = OperandNode.parseOperandNode(tokens);
                return new BinaryExprNode(leftOp, relop, rightOp);
            }
            else if (nextToken.getTokenType() == TokenType.MATH_OP){
                MathopNode mathop = MathopNode.parseMathOpNode(tokens);
                OperandNode rightOp = OperandNode.parseOperandNode(tokens);
                return new BinaryExprNode(leftOp, mathop, rightOp);
            }
            return leftOp;

        }
        else{
            System.err.printf("Syntax Error %n Expected Expertion got %s %n %s:%d%n",
                    currToken.getToken(), currToken.getFilename(), currToken.getLineNum());
            throw new Exception();
        }
    }

    String getExpressionType() throws Exception;
}
