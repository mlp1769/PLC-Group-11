package src.parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class WhileLoopNode implements BodyStmtNode, JottTree {

    private final Token kwWhile;    // "While"
    private final Token lb;         // '['
    private final ExprNode cond;    // <expr>
    private final Token rb;         // ']'
    private final Token lbr;     // '{'
    private final BodyNode body;    // body node (statements + optional Return)
    private final Token rbr;     // '}'

    public WhileLoopNode(Token kwWhile, Token lb, ExprNode cond, Token rb,
                         Token lbr, BodyNode body, Token rbr) {
        this.kwWhile = kwWhile;
        this.lb = lb;
        this.cond = cond;
        this.rb = rb;
        this.lbr = lbr;
        this.body = body;
        this.rbr = rbr;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception {
        // While
        Token whileTok = tokens.remove(0);
        if (whileTok.getTokenType() != TokenType.ID_KEYWORD || !"While".equals(whileTok.getToken())) {
            System.err.printf("Syntax Error: %n Expected While got %s %n %s:%d%n",
                    whileTok.getToken(), whileTok.getFilename(), whileTok.getLineNum());
            throw new Exception();
        }

        // '['
        Token LB = tokens.remove(0);
        if (LB.getTokenType() != TokenType.L_BRACKET) {
            System.err.printf("Syntax Error: %n Expected Left Bracket got %s %n %s:%d%n",
                    LB.getToken(), LB.getFilename(), LB.getLineNum());
            throw new Exception();
        }

        // <expr>
        ExprNode condition = ExprNode.parseExprNode(tokens);

        // ']'
        Token RB = tokens.remove(0);
        if (RB.getTokenType() != TokenType.R_BRACKET) {
            System.err.printf("Syntax Error: %n Expected Right Bracket got %s %n %s:%d%n",
                    RB.getToken(), RB.getFilename(), RB.getLineNum());
            throw new Exception();
        }

        // '{'
        Token lbr = tokens.remove(0);
        if (lbr.getTokenType() != TokenType.L_BRACE) {
            System.err.printf("Syntax Error: %n Expected Left Brace got %s %n %s:%d%n",
                    lbr.getToken(), lbr.getFilename(), lbr.getLineNum());
            throw new Exception();
        }

        // <body>
        BodyNode body = BodyNode.parseBodyNode(tokens);

        // '}'
        Token rbr = tokens.remove(0);
        if (rbr.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error: %n Expected Right Brace got %s %n %s:%d%n",
                    rbr.getToken(), rbr.getFilename(), rbr.getLineNum());
            throw new Exception();
        }

        return new WhileLoopNode(whileTok, LB, condition, RB, lbr, body, rbr);
    }

    @Override
    public String convertToJott() {
        return kwWhile.getToken()
             + lb.getToken()
             + cond.convertToJott()
             + rb.getToken()
             + lbr.getToken()
             + body.convertToJott()
             + rbr.getToken();
    }

    @Override public boolean validateTree() { 
    try {
        if (cond == null) semErr("While condition missing", kwWhile);

        cond.validateTree();                 
        if (body == null) semErr("While body missing", lbr);
        body.validateTree();                 

        if (cond instanceof BoolNode) {
            return true;
        } 
        else if (cond instanceof IDNode) {
            String name = ((IDNode) cond).convertToJott();
            String sym    = SymbolTable.getVar(name);
            if (!"Boolean".equals(sym)) {
                semErr("While condition must be Boolean, got " + (sym == null ? "Unknown" : sym), lb);
            }
        } 
        else if (cond instanceof BinaryExprNode) {
            String s = cond.convertToJott();
            if (!(s.contains("==") || s.contains("!=") || s.contains("<=") ||
                  s.contains(">=") || s.contains("<")  || s.contains(">"))) {
                semErr("While condition must be Boolean (relational expression expected)", lb);
            }
        } 
        else if (cond instanceof FunctionCallNode) {
            String fname = ((FunctionCallNode) cond).getFunctionName().getToken();
            String ret   = SymbolTable.getFunction(fname);
            if (!"Boolean".equals(ret)) {
                semErr("While condition must be Boolean, but function " + fname +
                       " returns " + (ret == null ? "Unknown" : ret), lb);
            }

        } 
        else {
            semErr("While condition must be Boolean, unsupported expression type", lb);
        }

        return true;
    } catch (RuntimeException re) {
        throw re;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

@Override
    public Object execute() throws Exception {
    while (true) {
        Object cv = cond.execute();
        if (!(cv instanceof Boolean)) {
            semErr("While condition must evaluate to Boolean at runtime", lb);
        }
        if (!((Boolean) cv)) break;
        Object r = body.execute();
        if (r != null) return r;
    }
    return null;
    }


    private static void semErr(String msg, provided.Token loc) {
    System.err.printf("Semantic Error:%n%s%n%s:%d%n",
            msg,
            (loc == null ? "<unknown>" : loc.getFilename()),
            (loc == null ? 1 : loc.getLineNum()));
    throw new RuntimeException(msg);
    }
}
