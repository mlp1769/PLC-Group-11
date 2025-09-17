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
			boolean decimal = false;
			boolean comment = false;
			boolean incompleteToken = false;

			int line = 1;
			while ((character = reader.read()) != -1) {
				char c = (char) character;
				if(c != '\n'){
			        //Single charater tokens
					if(c == ','){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.COMMA));
					}else if(c == ']'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.R_BRACKET));
					}else if(c == '['){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.L_BRACKET));
					}else if(c == '}'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.R_BRACE));
					}else if(c == '{'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.L_BRACE));
					//assignments 
					}else if(c == '='){
						if(previousCharacter == '=' || previousCharacter == '<' || previousCharacter == '>' || previousCharacter == '!'){
							tokens.remove(tokens.size()-1);
							tokens.add(new Token(previousCharacter+Character.toString(c), filename, line, TokenType.REL_OP));
						}else{
							tokens.add(new Token(Character.toString(c), filename, line, TokenType.ASSIGN));
						}
					}else if(c == '<' || c == '>'){
						tokens.add(new Token(Character.toString(c), filename, line, TokenType.REL_OP));
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
					}else if(c == '!'){
						int next = reader.read();
						if (next == '='){
							tokens.add(new Token("!=", filename, line, TokenType.REL_OP));

						}else{
							System.err.println("Error: '!' must be followed by '=' on line" + line);
							reader.close();
							return null;

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
					//Numbers 
					}else if(Character.isDigit(c) || c == '.'){
						//If a number is in a string
						if(isString){
							result.append(c);
						}else{
							if(decimal && c == '.'){
								System.err.println(String.format("Syntax Error Invalid \n token \"%c\" \"%c\" cannot follow \"%c\" in same token %s",c,c,c,filename));
								return null;			
							}
							if (Character.isDigit(previousCharacter) || previousCharacter == '.') {
								tokens.remove(tokens.size() - 1);
								result.append(c);
								tokens.add(new Token(result.toString(), filename, line, TokenType.NUMBER));
								incompleteToken = false;
							} else {
								result.setLength(0);
								result.append(c);
								tokens.add(new Token(Character.toString(c), filename, line, TokenType.NUMBER));
							}
							if(c == '.'){
								decimal = true;
							}
							if(c == '.' && !Character.isDigit(previousCharacter)){
								incompleteToken = true;
							}
						}
					//Strings
					}else if(Character.isAlphabetic(c)){
						if(isString){
							result.append(c);
						}
					}else if(c == '"'){
						if(isString){
							result.append(c);
							tokens.add(new Token(result.toString(), filename, line, TokenType.STRING));
							isString = false;
							result.setLength(0);
						}else{
							result.append(c);
							isString = true;
						}
					}else if(c == ' '){
						decimal = false;
						if(isString){
							result.append(c);
						}else if(incompleteToken){
							System.err.println(String.format("Syntax Error Invalid \n token \"%s\" \"%s\" is incomplete %s",result,result,filename));
							return null;
						}
					}
				}else{
					if(isString){
						System.err.println("Syntax Error");
						System.err.println("Missing double quotes");
						System.err.println("file."+filename+":"+line);
						return null;
					}
					line++;
					comment = false;
					result.setLength(0);
				}
				previousCharacter = c;
			}
			reader.close();
			if(incompleteToken){
				System.err.println(String.format("Syntax Error Invalid \n token \"%s\" \"%s\" is incomplete %s",result,result,filename));
				return null;
			}
			if(isString){
				System.err.println("Syntax Error");
				System.err.println("Missing double quotes");
				System.err.println("file."+filename+":"+line);
				return null;
			}
			return tokens;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}