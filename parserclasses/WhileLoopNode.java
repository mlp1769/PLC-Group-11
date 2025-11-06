package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class WhileLoopNode implements BodyStmtNode, JottTree {

    private final Token kwWhile;    // "While"
    private final Token lb;         // '['
    private final JottTree cond;    // <expr>
    private final Token rb;         // ']'
    private final Token lbr;     // '{'
    private final BodyNode body;    // body node (statements + optional Return)
    private final Token rbr;     // '}'

    public WhileLoopNode(Token kwWhile, Token lb, JottTree cond, Token rb,
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
            System.err.printf("Syntax Error %n Expected While got %s %n %s:%d%n",
                    whileTok.getToken(), whileTok.getFilename(), whileTok.getLineNum());
            throw new Exception();
        }

        // '['
        Token LB = tokens.remove(0);
        if (LB.getTokenType() != TokenType.L_BRACKET) {
            System.err.printf("Syntax Error %n Expected Left Bracket got %s %n %s:%d%n",
                    LB.getToken(), LB.getFilename(), LB.getLineNum());
            throw new Exception();
        }

        // <expr>
        JottTree condition = ExprNode.parseExprNode(tokens);

        // ']'
        Token RB = tokens.remove(0);
        if (RB.getTokenType() != TokenType.R_BRACKET) {
            System.err.printf("Syntax Error %n Expected Right Bracket got %s %n %s:%d%n",
                    RB.getToken(), RB.getFilename(), RB.getLineNum());
            throw new Exception();
        }

        // '{'
        Token lbr = tokens.remove(0);
        if (lbr.getTokenType() != TokenType.L_BRACE) {
            System.err.printf("Syntax Error %n Expected Left Brace got %s %n %s:%d%n",
                    lbr.getToken(), lbr.getFilename(), lbr.getLineNum());
            throw new Exception();
        }

        // <body>
        BodyNode body = BodyNode.parseBodyNode(tokens);

        // '}'
        Token rbr = tokens.remove(0);
        if (rbr.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
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
        //  1) ((ExprNode)cond).getType()
        //  2) ((TypedNode)cond).getType()
        //  3) a static TypeResolver.typeOf(cond)
        try {
            // String t = ((ExprNode)cond).getType();
            // if (!"Boolean".equals(t)) semErr("While condition must be Boolean", lb);
        } catch (ClassCastException ignore) {

        }

        return true;
    } catch (RuntimeException re) {
        throw re; // rethrow semantic error
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    private static void semErr(String msg, Token loc) {
        System.err.printf("Semantic Error: %n %s %n %s:%d%n",
                msg,
                (loc == null ? "<unknown>" : loc.getFilename()),
                (loc == null ? 1 : loc.getLineNum()));
        throw new RuntimeException(msg);
        }
}
