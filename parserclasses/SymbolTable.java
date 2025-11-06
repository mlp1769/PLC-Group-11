package parserclasses;

import java.util.HashMap;

import provided.Token;
import java.util.ArrayList;

public class SymbolTable {
    private static HashMap<String, String> functionTable = new HashMap<>();
    private static HashMap<String, HashMap<String, String>> varTable = new HashMap<>();
    private static HashMap<String, ArrayList<String>> paramTable = new HashMap<>();
    private static String scope = "";

    public SymbolTable(){}

    public static void addFunction(Token name, String type) throws Exception{
        if(functionTable.containsKey(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate function %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }else if(functionTable.containsKey("Def") || functionTable.containsKey("Return") ||
        functionTable.containsKey("If") || functionTable.containsKey("Else") ||
        functionTable.containsKey("Elseif") || functionTable.containsKey("While") ||
        functionTable.containsKey("Double") || functionTable.containsKey("Integer") ||
        functionTable.containsKey("String") || functionTable.containsKey("Boolean") ||
        functionTable.containsKey("Void") || functionTable.containsKey("True") ||
        functionTable.containsKey("False")){
            System.err.println(String.format("Semantic Error: %n %s is already a defined keyword %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        functionTable.put(name.getToken(), type);
        varTable.put(name.getToken(), new HashMap<String, String>());
        paramTable.put(name.getToken(), new ArrayList<String>());
    }

    public static void addVar(Token name, String type) throws Exception{
        if(varTable.get(scope).containsKey(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate variable %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        varTable.get(scope).put(name.getToken(), type);
    }

    public static void updateReturnType(String type){
        functionTable.put(scope, type);
    }

    public static String getFunction(String name){
        return functionTable.get(name);
    }
    
    public static String getVar(String name){
        return varTable.get(scope).get(name);
    }

    public static void addParam(String type){
        paramTable.get(scope).add(type);
    }

    public static ArrayList<String> getParam(){
        return paramTable.get(scope);
    }

    public static void changeScope(Token name) throws Exception{
        if(varTable.get(scope) == null){
            System.err.println(String.format("Semantic Error: %n Uninitialized function %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        scope = name.getToken();
    }

    public static String getScope(){
        return scope;
    }
}
