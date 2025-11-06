package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode, JottTree {

    private final Token kwIf;         // "If"
    private final Token lb;           // '['
    private final JottTree cond;      // <expr>
    private final Token rb;           // ']'
    private final Token lbr;   // '{'
    private final BodyNode thenBody;  // then block
    private final Token rbr;   // '}'
    private final Token kwElse;       // optional "Else"
    private final Token lbre;   // '{'
    private final BodyNode elseBody;  // optional else block
    private final Token rbre;   // '}'

    public IfStmtNode(Token kwIf, Token lb, JottTree cond, Token rb,
                      Token lbr, BodyNode thenBody, Token rbr,
                      Token kwElse, Token lbre, BodyNode elseBody, Token rbre) {
        this.kwIf = kwIf;
        this.lb = lb;
        this.cond = cond;
        this.rb = rb;
        this.lbr = lbr;
        this.thenBody = thenBody;
        this.rbr = rbr;
        this.kwElse = kwElse;
        this.lbre = lbre;
        this.elseBody = elseBody;
        this.rbre = rbre;
    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws Exception {
        // If
        Token ifTok = tokens.remove(0);
        if (ifTok.getTokenType() != TokenType.ID_KEYWORD || !"If".equals(ifTok.getToken())) {
            System.err.printf("Syntax Error %n Expected If got %s %n %s:%d%n",
                    ifTok.getToken(), ifTok.getFilename(), ifTok.getLineNum());
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
        Token LBT = tokens.remove(0);
        if (LBT.getTokenType() != TokenType.L_BRACE) {
            System.err.printf("Syntax Error %n Expected Left Brace got %s %n %s:%d%n",
                    LBT.getToken(), LBT.getFilename(), LBT.getLineNum());
            throw new Exception();
        }
        // <body>
        BodyNode thenBody = BodyNode.parseBodyNode(tokens);

        Token RBT = tokens.remove(0);
        if (RBT.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
                    RBT.getToken(), RBT.getFilename(), RBT.getLineNum());
            throw new Exception();
        }

        Token elseTok = null, LBE = null, RBE = null;
        BodyNode elseBody = null;

        if (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD
                && "Else".equals(tokens.get(0).getToken())) {
            elseTok = tokens.remove(0);

            LBE = tokens.remove(0);
            if (LBE.getTokenType() != TokenType.L_BRACE) {
                System.err.printf("Syntax Error %n Expected Left Brace got %s %n %s:%d%n",
                        LBE.getToken(), LBE.getFilename(), LBE.getLineNum());
                throw new Exception();
            }

            elseBody = BodyNode.parseBodyNode(tokens);

            RBE = tokens.remove(0);
            if (RBE.getTokenType() != TokenType.R_BRACE) {
                System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
                        RBE.getToken(), RBE.getFilename(), RBE.getLineNum());
                throw new Exception();
            }
        }

        return new IfStmtNode(ifTok, LB, condition, RB, LBT, thenBody, RBT, elseTok, LBE, elseBody, RBE);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(kwIf.getToken())
          .append(lb.getToken())
          .append(cond.convertToJott())
          .append(rb.getToken())
          .append(lbr.getToken())
          .append(thenBody.convertToJott())
          .append(rbr.getToken());
        if (kwElse != null) {
            sb.append(kwElse.getToken())
              .append(lbre.getToken())
              .append(elseBody.convertToJott())
              .append(rbre.getToken());
        }
        return sb.toString();
    }

    @Override public boolean validateTree() { 
            try {
        if (cond == null) semErr("If condition missing", kwIf);
        cond.validateTree();

        if (thenBody == null) semErr("If missing then-body", lbr);
        thenBody.validateTree();

        if (kwElse != null) {
            if (elseBody == null) semErr("Else missing body", kwElse);
            elseBody.validateTree();
        }

        try {
            // String t = ((ExprNode)cond).getType();
            // if (!"Boolean".equals(t)) semErr("If condition must be Boolean", lb);
        } catch (ClassCastException ignore) {
            // no type info available
        }

        return true;
    } catch (RuntimeException re) {
        throw re;
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

