package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseNode implements JottTree {
    // < else > -> Else { < body >} | ε

    private BodyNode body;

    public ElseNode(BodyNode bodyNode){
        this.body = bodyNode;
    }

    public ElseNode(){

    }

    public static ElseNode parseElseNode (ArrayList<Token> tokens) throws Exception{

        BodyNode bodyToPass;

        //todo check if first token is 'else'
        if(!(tokens.get(0).getToken().equals("Else"))){
            return new ElseNode();
        }
        tokens.remove(0);

        //todo check if first token is '{'
        if(!(tokens.get(0).getTokenType()==TokenType.L_BRACE)){
            System.err.println("Syntax Error\nMissing left bracket in else-statement\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        //todo parse body
        bodyToPass = BodyNode.parseBodyNode(tokens);

        //todo check if first token is '}'
        if(!(tokens.get(0).getTokenType()==TokenType.R_BRACE)){
            System.err.println("Syntax Error\nMissing right bracket in else-statement\n"+tokens.get(0).getFilename()+":"+tokens.get(0).getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        return new ElseNode(bodyToPass);

    }

    @Override
    public String convertToJott() {
        if(body==null){
            return "";
        }else{
            return "Else { "+body.convertToJott()+"}";
        }
    }

    @Override
    public boolean validateTree() {
        return false;
    }











}
