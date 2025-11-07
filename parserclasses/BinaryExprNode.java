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
            if(operator.convertToJott().equals("/") && (Integer.parseInt(num2.convertToJott()) == 0)){
                System.err.println(String.format("Semantic Error: cannot divide %s number by 0. line %d filename %s%n", num1.getNumber().getToken(), num1.getNumber().getLineNum(), num1.getNumber().getFilename()));
                throw new Exception();

            }
            else if(num1.isInteger() && num2.isInteger()){
                return num1.validateTree() && operator.validateTree() && num2.validateTree();
            }
            else if(!num1.isInteger() && !num2.isInteger()){
                return num1.validateTree() && operator.validateTree() && num2.validateTree();
            }
            else{
                System.err.println(String.format("Semantic Error: Incorrect Call %s number not same type as %s number. line %d filename %s%n", num1.getNumber().getToken(), num2.getNumber().getToken(), num1.getNumber().getLineNum(), num1.getNumber().getFilename()));
                throw new Exception();
            }
        }
        else if(operandOne instanceof IDNode && operator instanceof RelopNode){
            IDNode var = (IDNode) operandOne;
            RelopNode equals = (RelopNode) operator;
            if(operandTwo instanceof NumberNode){
                NumberNode num = (NumberNode) operandTwo;
                if(SymbolTable.getVar(var.getID().getToken()).equals("Integer") && num.isInteger()){
                    return var.validateTree() && equals.validateTree() && num.validateTree();
                }
                else if(SymbolTable.getVar(var.getID().getToken()).equals("Double") && !num.isInteger()){
                    return var.validateTree() && equals.validateTree() && num.validateTree();

                }
                else{
                    System.err.println(String.format("Semantic Error: Incorrect Call %s number not same type as %s var. line %d filename %s%n", num, var.getID().getToken(), var.getID().getLineNum(), var.getID().getFilename()));
                    throw new Exception();
                }
            }
            else if(operandTwo instanceof StringLiteralNode){
                StringLiteralNode string = (StringLiteralNode) operandTwo;
                if(SymbolTable.getVar(var.getID().getToken()).equals("String")){
                    return var.validateTree() && equals.validateTree() && string.validateTree();
                }
                else{
                    System.err.println(String.format("Semantic Error: Incorrect Call var %s is not a string. line %d filename %s%n", var.getID().getToken(), var.getID().getLineNum(), var.getID().getFilename()));
                    throw new Exception();
                }
            }
            else if(operandTwo instanceof FunctionCallNode){
                FunctionCallNode function = (FunctionCallNode) operandTwo;
                String funcname = function.getFunctionName().getToken();
                if(SymbolTable.getVar(var.getID().getToken()).equals(SymbolTable.getFunction(funcname))){
                    return var.validateTree() && equals.validateTree() && function.validateTree();
                }
                else{
                    System.err.println(String.format("Semantic Error: Incorrect Call %s function not same type as %s var. line %d filename %s%n", funcname, var.getID().getToken(), var.getID().getLineNum(), var.getID().getFilename()));
                    throw new Exception();
                }

            }
        }else if(operandOne instanceof IDNode && operator instanceof MathopNode){
            IDNode var = (IDNode) operandOne;
            MathopNode math = (MathopNode) operator;
            if(operandTwo instanceof NumberNode){
                NumberNode num = (NumberNode) operandTwo;
                if(SymbolTable.getVar(var.getID().getToken()).equals("Integer") && num.isInteger()){
                    return var.validateTree() && math.validateTree() && num.validateTree();
                }
                else if(SymbolTable.getVar(var.getID().getToken()).equals("Double") && !num.isInteger()){
                    return var.validateTree() && math.validateTree() && num.validateTree();
                }
                else{
                    System.err.println(String.format("Semantic Error: Incorrect Call %s number not same type as %s var. line %d filename %s%n", num.getNumber().getToken(), var.getID().getToken(), num.getNumber().getLineNum(), var.getID().getFilename()));
                    throw new Exception();
                }
            }else if(operandTwo instanceof FunctionCallNode){
                FunctionCallNode function = (FunctionCallNode) operandTwo;
                String funcname = function.getFunctionName().getToken();
                if(SymbolTable.getVar(var.getID().getToken()).equals(SymbolTable.getFunction(funcname))){
                    return var.validateTree() && math.validateTree() && function.validateTree();
                }
                else{
                    System.err.println(String.format("Semantic Error: Incorrect Call %s function not same type as %s var. line %d filename %s%n", funcname, var.getID().getToken(), var.getID().getLineNum(), var.getID().getFilename()));
                    throw new Exception();
                }

                
            }else{
                System.err.println(String.format("Semantic Error: Cannot add String and var %s. line %d filename %s%n", var.getID().getToken(), var.getID().getLineNum(), var.getID().getFilename()));
                throw new Exception();
            }
        }
        else{
            return operandOne.validateTree() && operator.validateTree() && operandTwo.validateTree();
        }
        System.err.println(String.format("Semantic Error: Overall Error in sematics of Expression"));
        throw new Exception();
  
    }

    @Override
    public String getExpressionType() throws Exception {
        return this.operandOne.getExpressionType().equals(this.operandTwo.getExpressionType()) ? this.operandOne.getExpressionType() : "no";
    }
}
