package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class BinaryExprNode implements ExprNode{
    private OperandNode operandOne;
    private OperandNode operandTwo;
    private JottTree operator;
 

    public BinaryExprNode(OperandNode operandOne, JottTree operator, OperandNode operandTwo){
        this.operandOne = operandOne;
        this.operator = operator;
        this.operandTwo = operandTwo;
    }

    @Override
    public String convertToJott(){
        return operandOne.convertToJott() + operator.convertToJott() + operandTwo.convertToJott();
    }

    @Override
    public boolean validateTree() throws Exception{
        if(operandOne instanceof NumberNode && operandTwo instanceof NumberNode){
            NumberNode num1 = (NumberNode) operandOne;
            NumberNode num2 = (NumberNode) operandTwo;
            if(num1.isInteger() && num2.isInteger()){
                return num1.validateTree() && operator.validateTree() && num2.validateTree();
            }
            else if(!num1.isInteger() && !num2.isInteger()){
                return num1.validateTree() && operator.validateTree() && num2.validateTree();
            }
            else{
                return false;
            }
         
        }
        else{
            return operandOne.validateTree() && operator.validateTree() && operandTwo.validateTree();
        }
  
    }

}
