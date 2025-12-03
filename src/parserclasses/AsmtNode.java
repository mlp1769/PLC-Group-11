package src.parserclasses;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class AsmtNode implements BodyStmtNode{

    private final IDNode id;
    private final Token assign;  // '='
    private final ExprNode exp;  // <expr>
    private final Token semi;    // ';'

    public AsmtNode(IDNode id, Token assign, ExprNode exp, Token semi) {
        this.id = id;
        this.assign = assign;
        this.exp = exp;
        this.semi = semi;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws Exception {
        // ID
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            Token t = tokens.isEmpty() ? null : tokens.get(0);
            String got = (t == null) ? "<eof>" : t.getToken();
            String fn  = (t == null) ? "<unknown>" : t.getFilename();
            int ln     = (t == null) ? 1 : t.getLineNum();
            System.err.printf("Syntax Error %n identifier expected at start of assignment, got %s %n %s:%d%n",
                    got, fn, ln);
            throw new Exception();
        }
        IDNode lhs = IDNode.parseIDNode(tokens);

        // '='
        Token eq = tokens.remove(0);
        if (eq.getTokenType() != TokenType.ASSIGN) {
            System.err.printf("Syntax Error %n Expected '=' in assignment got %s %n %s:%d%n",
                    eq.getToken(), eq.getFilename(), eq.getLineNum());
            throw new Exception();
        }

        if (tokens.isEmpty() || tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
            System.err.printf("Syntax Error %n Assignment missing right side expression %n %s:%d%n",
                    eq.getFilename(), eq.getLineNum());
            throw new Exception();
        }

        ExprNode exp = ExprNode.parseExprNode(tokens);

        Token semi = tokens.remove(0);
        if (semi.getTokenType() != TokenType.SEMICOLON) {
            System.err.printf("Syntax Error %n Expected semicolon got %s %n %s:%d%n",
                    semi.getToken(), semi.getFilename(), semi.getLineNum());
            throw new Exception();
        }

        return new AsmtNode(lhs, eq, exp, semi);
    }

    @Override
    public String convertToJott() {
        return id.convertToJott() + assign.getToken() + exp.convertToJott() + semi.getToken();
    }

    @Override 
    public boolean validateTree() {
    try {
            if (id == null || !(id instanceof IDNode)) {
                semErr("Left-hand side of assignment must be an identifier", assign);
            }
            if (exp == null) {
                semErr("Assignment missing right-hand side expression", assign);
            }
            id.validateTree();
            exp.validateTree();
            if (SymbolTable.getVar(id.getID().getToken()).equals(exp.getExpressionType())) {
                return true;
            }
            else {
                semErr("Assignement type not matching", assign);
            }
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object execute() throws Exception {
        return null;
    }

    private static void semErr(String msg, provided.Token loc)
    {
        System.err.printf("Semantic Error:%n%s%n%s:%d%n",
                msg,
                (loc == null ? "<unknown>" : loc.getFilename()),
                (loc == null ? 1 : loc.getLineNum()));
        throw new RuntimeException(msg);
    }
}