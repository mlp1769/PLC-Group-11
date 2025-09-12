package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.FileReader;
import java.util.ArrayList;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
		try{
			ArrayList<Token> tokens = new ArrayList<>();
			FileReader reader = new FileReader(filename);

			int character;
			char previousCharacter = 0;
			StringBuilder result = new StringBuilder();
			boolean isString = false;
			boolean comment = false;

			int line = 1;
			while ((character = reader.read()) != -1) {
				char c = (char) character;
				if(c != '\n'){
					if(c == ' '){
						continue;
					}else if(c == '!'){
						reader.mark(1);
						int next = reader.read();
						if (next == '='){
							tokens.add(new Token("!=", filename, line, TokenType.REL_OP));

						}else{
							System.err.println("Error: '!' must be followed by '=' on line" + line);
							reader.close();
							return null;

						}
						if(next != -1){
							reader.reset();
						}
					}
					
					else if( c == '#'){
						while((character = reader.read()) != -1){
						if((char)character == '\n'){
							line++;
							break;
						}
					}
						continue;
					}
					else if(c == ','){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.COMMA));
					}else if(c == ']'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.R_BRACKET));
					}else if(c == '['){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.L_BRACKET));
					}else if(c == '}'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.R_BRACE));
					}else if(c == '{'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.L_BRACE));
					}else if(c == '<' || c == '>'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.REL_OP));
					}else if(c == '='){
						if(previousCharacter == '=' || previousCharacter == '<' || previousCharacter == '>' || previousCharacter == '!'){
							tokens.remove(tokens.size()-1);
							tokens.add(new Token(previousCharacter+Character.toString(c), filename, line, TokenType.REL_OP));
						}else{
							tokens.add(new Token(Character.toString(c), filename, line, TokenType.ASSIGN));
						}
					}else if(c == '/' || c == '*' || c == '+' || c == '-'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.MATH_OP));
					}else if(c == ';'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.SEMICOLON));
					}else if(c == ':'){
						if(previousCharacter == ':'){
							tokens.remove(tokens.size()-1);
							tokens.add(new Token(previousCharacter+Character.toString(c), filename, line, TokenType.FC_HEADER));
						}else{
							tokens.add(new Token(Character.toString(c), filename, line, TokenType.COLON));
						}
					// }else if(c == '!'){
					// 	tokens.add(new Token(Character.toString(c), filename, line, TokenType.REL_OP));
					}else if(Character.isDigit(c)){
						if(isString){
							result.append(c);
						}else {
							if (Character.isDigit(previousCharacter)) {
								tokens.remove(tokens.size() - 1);
								tokens.add(new Token(result.toString(), filename, line, TokenType.NUMBER));
							} else {
								result.setLength(0);
							}
							result.append(c);
							tokens.add(new Token(Character.toString(c), filename, line, TokenType.NUMBER));
						}
					}else if(Character.isAlphabetic(c)){
						if(isString){
							result.append(c);

						}
					}else if(c == '"'){
						if(isString){
							result.append(c);
							tokens.remove(tokens.size()-1);
							tokens.add(new Token(result.toString(), filename, line, TokenType.STRING));
							isString = false;
							result.setLength(0);
						}else{
							result.append(c);
							isString = true;
						}
					}else if(c == ' '){
						if(isString){
							result.append(c);
						}
					}
				}else{
					line++;
					comment = false;
				}
				previousCharacter = c;
			}
			if( previousCharacter == '!'){
				System.err.println("Error");
				reader.close();
				return null;

			}


			reader.close();
			System.out.println(tokens);
			return tokens;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}