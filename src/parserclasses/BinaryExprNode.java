package src.parserclasses;

public class BinaryExprNode implements ExprNode{
    private OperandNode operandOne;
    private OperandNode operandTwo;
    private OperationNode operator;
 

    public BinaryExprNode(OperandNode operandOne, OperationNode operator, OperandNode operandTwo){
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
        if(this.operandOne instanceof NumberNode){
            NumberNode number = (NumberNode) this.operandOne;
            if(this.operandTwo instanceof NumberNode){
                NumberNode operand2 = (NumberNode) this.operandTwo;
                if(!number.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }else if(operator.convertToJott().equals("/") && operand2.getToken().getToken().equals("0")){
                    System.err.println("Semantic Error:");
                    System.err.println("Division by zero error.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else if(this.operandTwo instanceof IDNode){
                IDNode operand2 = (IDNode) this.operandTwo;
                if(!number.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else if(this.operandTwo instanceof FunctionCallNode){
                FunctionCallNode operand2 = (FunctionCallNode) this.operandTwo;
                if(!number.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else{
                throw new Exception("Not a binary expression");
            }

        } else if(operandOne instanceof  IDNode){
            IDNode id = (IDNode) this.operandOne;
            if(this.operandTwo instanceof NumberNode){
                NumberNode operand2 = (NumberNode) this.operandTwo;
                if(!id.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else if(this.operandTwo instanceof IDNode){
                IDNode operand2 = (IDNode) this.operandTwo;
                if(!id.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }

            }else if(this.operandTwo instanceof FunctionCallNode){
                FunctionCallNode operand2 = (FunctionCallNode) this.operandTwo;
                if(!id.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else{
                throw new Exception("Not a binary expression");
            }
        }else if(operandOne instanceof FunctionCallNode){
            FunctionCallNode function = (FunctionCallNode) this.operandOne;
            if(this.operandTwo instanceof NumberNode){
                NumberNode operand2 = (NumberNode) this.operandTwo;
                if(!function.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }

            }else if(this.operandTwo instanceof IDNode){
                IDNode operand2 = (IDNode) this.operandTwo;
                if(!function.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }

            }else if(this.operandTwo instanceof FunctionCallNode){
                FunctionCallNode operand2 = (FunctionCallNode) this.operandTwo;
                if(!function.getExpressionType().equals(operand2.getExpressionType())){
                    System.err.println("Semantic Error:");
                    System.err.println("Function type does not match the second operand's type in expression.");
                    System.err.println(operandOne.getToken().getFilename()+":"+operandOne.getToken().getLineNum());
                    throw new Exception();
                }
            }else{
                throw new Exception("Not a binary expression");
            }
        }else{
            throw new Exception("Not a binary expression");
        }
        return this.operandOne.validateTree() && this.operandTwo.validateTree() && this.operator.validateTree();
    }

    @Override
    public Object execute() throws Exception {
        return null;
    }

    @Override
    public String getExpressionType() throws Exception {
        if(operator.isMathOp()){
            return this.operandOne.getExpressionType().equals(this.operandTwo.getExpressionType())? this.operandOne.getExpressionType() : "no";
        }
        return this.operandOne.getExpressionType().equals(this.operandTwo.getExpressionType()) ? "Boolean" : "no";
    }
}
