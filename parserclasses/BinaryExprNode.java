package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.lang.runtime.ExactConversionsSupport;
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
    public boolean validateTree(){
        return false;
    }

    

    
}
