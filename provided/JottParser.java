package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import parserclasses.ProgramNode;

import java.util.ArrayList;

public class JottParser {

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens) throws Exception{
        JottTree possible = null;

        possible = ProgramNode.parseProgramNodeProgram(tokens);

        /*
        try{
             possible = ProgramNode.parseProgramNodeProgram(tokens);
        } catch (Exception e) {
            return null;
        }

         */

		return possible;
    }
}
