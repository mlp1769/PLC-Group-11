package parserclasses;

import java.util.HashMap;

import provided.Token;
import provided.TokenType;

public class SymbolTable {
    private static HashMap<String, String> functionTable = new HashMap<>();
    private static HashMap<String, HashMap<String, String>> varTable = new HashMap<>();
    private static String scope = "";

    public SymbolTable(){}

    public static void addFunction(Token name, String type) throws Exception{
        if(functionTable.keySet().contains(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate function %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        functionTable.put(name.getToken(), type);
        varTable.put(name.getToken(), new HashMap<String, String>());
    }

    public static void addVar(Token name, String type) throws Exception{
        if(varTable.get(scope).keySet().contains(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate variable %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        varTable.get(scope).put(name.getToken(), type);
    }

    public static String getFunction(String name){
        return functionTable.get(name);
    }
    
    public static String getVar(String name){
        return varTable.get(scope).get(name);
    }

    public static void changeScope(String name){
        scope = name;
    }
}
