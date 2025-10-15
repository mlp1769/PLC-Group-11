package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class WhileLoop implements BodyStmt {

    private final JottTree condition;
    private final ArrayList<BodyStmt> body;
    private final int lineNum;

    public WhileLoop(JottTree condition, ArrayList<BodyStmt> body, int lineNum) {
        this.condition = condition;
        this.body = body;
        this.lineNum = lineNum;
    }

    public static WhileLoop parse(Parser p) {
        Token whileTok = p.expect(TokenType.ID_KEYWORD, "While");
        int line = whileTok.getLineNum();

        p.expect(TokenType.L_BRACKET, "[");
        JottTree condition = ExprParser.parse(p);
        p.expect(TokenType.R_BRACKET, "]");

        p.expect(TokenType.L_BRACE, "{");
        ArrayList<BodyStmt> bodyStmts = BodyStmtParser.parseBody(p);
        p.expect(TokenType.R_BRACE, "}");

        return new WhileLoop(condition, bodyStmts, line);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append("While[").append(condition.convertToJott()).append("]{");
        for (BodyStmt stmt : body)
            sb.append(stmt.convertToJott());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean validateTree() {
        // Phase 3: type checks (e.g., condition type) go here
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
}
