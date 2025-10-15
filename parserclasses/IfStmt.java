package parserclasses;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class IfStmt implements BodyStmt {

    private final JottTree condition;
    private final ArrayList<BodyStmt> thenBody;
    private final ArrayList<BodyStmt> elseBody;  // can be null if no else
    private final int lineNum;

    public IfStmt(JottTree condition, ArrayList<BodyStmt> thenBody,
                  ArrayList<BodyStmt> elseBody, int lineNum) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
        this.lineNum = lineNum;
    }

    public static IfStmt parse(Parser p) {
        Token ifTok = p.expect(TokenType.ID_KEYWORD, "If");
        int line = ifTok.getLineNum();

        p.expect(TokenType.L_BRACKET, "[");
        JottTree condition = ExprParser.parse(p);
        p.expect(TokenType.R_BRACKET, "]");

        p.expect(TokenType.L_BRACE, "{");
        ArrayList<BodyStmt> thenBody = BodyStmtParser.parseBody(p);
        p.expect(TokenType.R_BRACE, "}");

        ArrayList<BodyStmt> elseBody = null;
        if (p.match(TokenType.ID_KEYWORD, "Else")) {
            p.expect(TokenType.L_BRACE, "{");
            elseBody = BodyStmtParser.parseBody(p);
            p.expect(TokenType.R_BRACE, "}");
        }

        return new IfStmt(condition, thenBody, elseBody, line);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append("If[").append(condition.convertToJott()).append("]{");
        for (BodyStmt stmt : thenBody)
            sb.append(stmt.convertToJott());
        sb.append("}");
        if (elseBody != null) {
            sb.append("Else{");
            for (BodyStmt stmt : elseBody)
                sb.append(stmt.convertToJott());
            sb.append("}");
        }
        return sb.toString();
    }
    @Override
    public boolean isBodyStatement(Node node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBodyStatement'");
    }

}
