package parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

/** <while_loop> -> While [ <expr> ] { <body_stmt_list> } */
public class WhileLoopNode implements BodyStmtNode, JottTree {

    private final Token kwWhile;                 // "While"
    private final Token lb;                      // '['
    private final JottTree condition;            // <expr>
    private final Token rb;                      // ']'
    private final Token lbrace;                  // '{'
    private final ArrayList<BodyStmtNode> body;  // body statements
    private final Token rbrace;                  // '}'

    public WhileLoopNode(Token kwWhile, Token lb, JottTree condition,
                         Token rb, Token lbrace, ArrayList<BodyStmtNode> body, Token rbrace) {
        this.kwWhile = kwWhile;
        this.lb = lb;
        this.condition = condition;
        this.rb = rb;
        this.lbrace = lbrace;
        this.body = body;
        this.rbrace = rbrace;
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

        JottTree cond = ExprParser.parseExpr(tokens);

        // ']'
        Token RB = tokens.remove(0);
        if (RB.getTokenType() != TokenType.R_BRACKET) {
            System.err.printf("Syntax Error %n Expected Right Bracket got %s %n %s:%d%n",
                    RB.getToken(), RB.getFilename(), RB.getLineNum());
            throw new Exception();
        }

        // '{'
        Token LBRACE = tokens.remove(0);
        if (LBRACE.getTokenType() != TokenType.L_BRACE) {
            System.err.printf("Syntax Error %n Expected Left Brace got %s %n %s:%d%n",
                    LBRACE.getToken(), LBRACE.getFilename(), LBRACE.getLineNum());
            throw new Exception();
        }

        // <body_stmt_list>  (centralized router)
        ArrayList<BodyStmtNode> bodyList = BodyStmtNode.parseBody(tokens);

        // '}'
        Token RBRACE = tokens.remove(0);
        if (RBRACE.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error %n Expected Right Brace got %s %n %s:%d%n",
                    RBRACE.getToken(), RBRACE.getFilename(), RBRACE.getLineNum());
            throw new Exception();
        }

        return new WhileLoopNode(whileTok, LB, cond, RB, LBRACE, bodyList, RBRACE);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(kwWhile.getToken())
          .append(lb.getToken())
          .append(condition.convertToJott())
          .append(rb.getToken())
          .append(lbrace.getToken());
        for (BodyStmtNode s : body) sb.append(s.convertToJott());
        sb.append(rbrace.getToken());
        return sb.toString();
    }

    @Override public boolean validateTree() { return true; }
}
