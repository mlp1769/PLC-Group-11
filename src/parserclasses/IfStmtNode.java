package src.parserclasses;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode, JottTree {

    // missing fields (referenced below)
    private final Token kwIf;
    private final Token lb;
    private final ExprNode cond;
    private final Token rb;
    private final Token lbr;
    private final BodyNode thenBody;
    private final Token rbr;
    private final ElseNode elseNode;
    private final ArrayList<ElseifNode> elseifNodes;

    public IfStmtNode(Token kwIf, Token lb, ExprNode cond, Token rb,
                    Token lbr, BodyNode thenBody, Token rbr, ArrayList<ElseifNode> elseifNodes,
                    ElseNode elseNode) {
        this.kwIf = kwIf;
        this.lb = lb;
        this.cond = cond;
        this.rb = rb;
        this.lbr = lbr;
        this.thenBody = thenBody;
        this.rbr = rbr;
        this.elseNode = elseNode;
        this.elseifNodes = (elseifNodes == null) ? new ArrayList<>() : elseifNodes;
    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws Exception {
        Token ifTok = tokens.remove(0);
        if (ifTok.getTokenType() != TokenType.ID_KEYWORD || !"If".equals(ifTok.getToken())) {
            System.err.printf("Syntax Error: %n Expected If got %s %n %s:%d%n",
                    ifTok.getToken(), ifTok.getFilename(), ifTok.getLineNum());
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

        // '}'
        Token RBT = tokens.remove(0);
        if (RBT.getTokenType() != TokenType.R_BRACE) {
            System.err.printf("Syntax Error: %n Expected Right Brace got %s %n %s:%d%n",
                    RBT.getToken(), RBT.getFilename(), RBT.getLineNum());
            throw new Exception();
        }
        // ElseIf
        ArrayList<ElseifNode> elseifNodes = new ArrayList<>();
        while (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD
                && "Elseif".equals(tokens.get(0).getToken())) {
            ElseifNode en = ElseifNode.parseElseifNode(tokens);
            elseifNodes.add(en);
        }

        // optional Else
        ElseNode elseNode = null;
        if (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD
                && "Else".equals(tokens.get(0).getToken())) {
            elseNode = ElseNode.parseElseNode(tokens);
        }
        return new IfStmtNode(ifTok, LB, condition, RB, LBT, thenBody, RBT, elseifNodes, elseNode);
    }

    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        sb.append(kwIf.getToken())
        .append(lb.getToken())
        .append(cond.convertToJott())
        .append(rb.getToken())
        .append(lbr.getToken())
        .append(thenBody.convertToJott())
        .append(rbr.getToken());

        if (elseifNodes != null) {
            for (ElseifNode e : elseifNodes) {
                if (e != null) sb.append(e.convertToJott());
            }
        }

        if (elseNode != null) {
            sb.append(elseNode.convertToJott());
        }

        return sb.toString();
    }
    @Override
    public boolean validateTree() { 
    try {
        if (cond == null) {
            semErr("If condition missing", kwIf);
        }
        cond.validateTree();                           
        if (cond instanceof BoolNode) {
            return true;
        } 
        else if (cond instanceof IDNode) {
            String name = ((IDNode) cond).convertToJott();     
            String sym  = SymbolTable.getVar(name);
            if (!"Boolean".equals(sym)) {
                semErr("If condition must be Boolean, got " + (sym == null ? "Unknown" : sym), lb);
            }
        } 
        else if (cond instanceof BinaryExprNode) {
            String s = cond.convertToJott();
            if (!(s.contains("==") || s.contains("!=") || s.contains("<=") ||
                  s.contains(">=") || s.contains("<")  || s.contains(">"))) {
                semErr("If condition must be Boolean (relational expression expected)", lb);
            }
        } 
        else {
            semErr("If condition must be Boolean, unsupported expression type", lb);
        }
        
        if (thenBody == null){ 
            semErr("If missing then-body", lbr);
        }
        thenBody.validateTree();  

        if (elseifNodes != null) {
            for (ElseifNode eli : elseifNodes) {
                if (eli != null) eli.validateTree();
            }
        }
        if (elseNode != null) {
            elseNode.validateTree();
        }
        return true;
    } 
    catch (RuntimeException re) {
        throw re;
    } 
    catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    @Override
    public Object execute() throws Exception {
    Object cv = cond.execute();
    if (!(cv instanceof Boolean)) {
        semErr("If condition must evaluate to Boolean at runtime", lb);
    }
    if ((Boolean) cv) {
        return this.thenBody.execute();
    }else if (!this.elseifNodes.isEmpty()) {
        for (ElseifNode ef : this.elseifNodes) {
            Object res = ef.execute();
            if(res instanceof ElseifNode){
                continue;
            }
            return res;
        }
        return this.elseNode.execute();
    }else if (this.elseNode != null) {
        return this.elseNode.execute();
    }
    return null;
}


    private static void semErr(String msg, provided.Token loc) {
    System.err.printf("Runtime Error:%n%s%n%s:%d%n",
            msg,
            (loc == null ? "<unknown>" : loc.getFilename()),
            (loc == null ? 1 : loc.getLineNum()));
    throw new RuntimeException(msg);
}

    public ArrayList<BodyNode> getBodyNodes() {
    ArrayList<BodyNode> BodyNodes = new ArrayList<>();

    // IF body
    if (thenBody != null) {
        BodyNodes.add(thenBody);
    }

    // ELSEIF bodies
    if (elseifNodes != null) {
        for (ElseifNode en : elseifNodes) {
            if (en == null) continue;
            try {
                java.lang.reflect.Field f = en.getClass().getDeclaredField("body");
                f.setAccessible(true);
                Object bodyObj = f.get(en);
                if (bodyObj instanceof BodyNode) {
                    BodyNodes.add((BodyNode) bodyObj);
                }
            } catch (Exception ignore) {
            }
        }
    }

    // ELSE body 
    if (elseNode != null) {
        try {
            java.lang.reflect.Field f = elseNode.getClass().getDeclaredField("body");
            f.setAccessible(true);
            Object bodyObj = f.get(elseNode);
            if (bodyObj instanceof BodyNode) {
                BodyNodes.add((BodyNode) bodyObj);
            }
        } catch (Exception ignore) {
        }
    }

    return BodyNodes;
    }
}
