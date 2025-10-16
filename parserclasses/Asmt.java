package parserclasses;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
/**
 * <asmt> -> ID '=' <expr> ';'
 */
public class Asmt implements BodyStmt {

    private final String id;
    private final JottTree rhs;
    private final int lineNum;

    private Asmt(String id, JottTree rhs, int lineNum) {
        this.id = id;
        this.rhs = rhs;
        this.lineNum = lineNum;
    }

    public static Asmt parse(ArrayList<Token> p) {
        Token idTok = p.remove(TokenType.ID_KEYWORD, "identifier");
        int line = idTok.getLineNum();

        p.expect(TokenType.ASSIGN, "'='");

        // Handout-specific messaging when RHS is missing (e.g., "x = ;")
        if (p.cur() == null || p.cur().getTokenType() == TokenType.SEMICOLON) {
            System.err.println("Syntax Error:");
            System.err.println("Assignment missing right side expression");
            System.err.println(p.filename + ":" + p.line());
            throw new Parser.Halt();
        }

        JottTree rhs = ExprParser.parse(p);
        p.expect(TokenType.SEMICOLON, "semicolon");

        return new Asmt(idTok.getToken(), rhs, line);
    }

    @Override
    public String convertToJott() {
        return id + "=" + rhs.convertToJott() + ";";
    }

    @Override
    public boolean validateTree() {
        // Phase 3: type checks (e.g., lhs symbol lookup, type compatibility) go here
        return true;
    }

}

