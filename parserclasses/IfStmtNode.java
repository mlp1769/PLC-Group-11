package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode, JottTree {

    private final Token kwIf;                       // "If"
    private final Token lb;                         // '['
    private final JottTree condition;               // <expr>
    private final Token rb;                         // ']'
    private final Token lbr;                 // '{'
    private final ArrayList<BodyStmtNode> thenBody;
    private final Token rbr;                 // '}'
    private final Token kwElse;                     // optional "Else"
    private final Token lbre;                 // '{'
    private final ArrayList<BodyStmtNode> elseBody; // optional else body
    private final Token rbre;                 // '}'

    public IfStmtNode(Token kwIf, Token lb, JottTree condition, Token rb,
                      Token lbr, ArrayList<BodyStmtNode> thenBody, Token rbr,
                      Token kwElse, Token lbre, ArrayList<BodyStmtNode> elseBody, Token rbre) {
        this.kwIf = kwIf;
        this.lb = lb;
        this.condition = condition;
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

        JottTree cond = ExprParser.parseExpr(tokens);

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
        ArrayList<BodyStmtNode> thenBody = BodyStmtNode.parseBody(tokens);
        Token RBT = tokens.remove(0);
        if (RBT.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
                    RBT.getToken(), RBT.getFilename(), RBT.getLineNum());
            throw new Exception();
        }

        Token elseTok = null, LBE = null, RBE = null;
        ArrayList<BodyStmtNode> elseBody = null;

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

            elseBody = BodyStmtNode.parseBody(tokens);

            RBE = tokens.remove(0);
            if (RBE.getTokenType() != TokenType.R_BRACE) {
                System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
                        RBE.getToken(), RBE.getFilename(), RBE.getLineNum());
                throw new Exception();
            }
        }

        return new IfStmtNode(ifTok, LB, cond, RB, LBT, thenBody, RBT, elseTok, LBE, elseBody, RBE);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(kwIf.getToken())
          .append(lb.getToken())
          .append(condition.convertToJott())
          .append(rb.getToken())
          .append(lbr.getToken());
        for (BodyStmtNode s : thenBody) sb.append(s.convertToJott());
        sb.append(rbr.getToken());
        if (kwElse != null) {
            sb.append(kwElse.getToken())
              .append(lbre.getToken());
            for (BodyStmtNode s : elseBody) sb.append(s.convertToJott());
            sb.append(rbre.getToken());
        }
        return sb.toString();
    }

    @Override public boolean validateTree() { return true; }
}
