package src.parserclasses;

import java.util.HashMap;

import provided.Token;
import java.util.ArrayList;


public class SymbolTable {
    private static HashMap<String, FunctionValue> functionTable = new HashMap<>();
    private static HashMap<String, HashMap<String, VarValue>> varTable = new HashMap<>();
    // first string name of function, second arraylist of types
    private static HashMap<String, ArrayList<String[]>> paramTable = new HashMap<>();
    private static ArrayList<String[]> copyTable;
    private static String scope = "";
    private static String paramScope = "main";

    public SymbolTable(){}

    public static void addFunction(Token name) throws Exception{
        if(functionTable.containsKey(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate function %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }

        functionTable.put(name.getToken(), new FunctionValue());
        varTable.put(name.getToken(), new HashMap<String, VarValue>());
        paramTable.put(name.getToken(), new ArrayList<String[]>());

        if(functionTable.containsKey("Def") || functionTable.containsKey("Return") ||
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
    }

    public static void addVar(Token name, String type) throws Exception{
        if(varTable.get(scope).containsKey(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Duplicate variable %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        varTable.get(scope).put(name.getToken(), new VarValue(type));
    }

    public static void addFunctionBody(FBodyNode body){functionTable.get(scope).setBody(body);}

    public static FBodyNode getFunctionBody(){return functionTable.get(scope).getBody();}

    public static void updateReturnType(String type){
        functionTable.get(scope).setType(type);
    }

    public static String getFunction(String name){
        return functionTable.get(name).getType();
    }
    
    public static String getVar(String name){
        try{
            return varTable.get(scope).get(name).getType();
        } catch (Exception e){
            return null;
        }
    }

    public static void changeParamScope(String name){paramScope = name;}

    public static String getParamScope(){return paramScope;}

    public static void setValue(String name, Object value){varTable.get(scope).get(name).setValue(value);}

    public static void setParamValue(String name, Object value){varTable.get(paramScope).get(name).setValue(value);}

    public static Object getValue(String name){return varTable.get(scope).get(name).getValue();}

    public static void addParam(Token name, String type) throws  Exception{
        SymbolTable.addVar(name,type);
        String[] binding = {name.getToken(), type};
        paramTable.get(scope).add(binding);
    }

    public static void getParamstart(String call){
        if(paramTable.get(call) == null){
            copyTable = new ArrayList<>();
        }else{
            copyTable = new ArrayList<>(paramTable.get(call));
        }
    }

    public static String getParam(){
        try {
            return copyTable.remove(0)[1];
        } catch (Exception e) {
            return null;
        }
    }

    public static String getParamName(){
        try {
            return copyTable.remove(0)[0];
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<String[]> getCopyTable() {
        return copyTable;
    }

    public static void setCopyTable(ArrayList<String[]> copyTable2){
        copyTable = copyTable2;
    }

    public static void changeScope(Token name) throws Exception{
        if(!functionTable.containsKey(name.getToken())){
            System.err.println(String.format("Semantic Error: %n Uninitialized function %s %n %s:%d%n",
                    name.getToken(), name.getFilename(), name.getLineNum()));
            throw new Exception();
        }
        scope = name.getToken();
    }

    public static String getScope(){
        return scope;
    }

    public static void clear(){
        functionTable = new HashMap<>();
        varTable = new HashMap<>();
        paramTable = new HashMap<>();
        Token print = new Token("print", null, 0, null);
        Token concat = new Token("concat", null, 0, null);
        Token concat2 = new Token("concat2", null, 0, null);
        Token lenth = new Token("length", null, 0, null);
        try {
            SymbolTable.addFunction(print);
            SymbolTable.changeScope(print);
            SymbolTable.updateReturnType("Void");
            SymbolTable.addParam(print, "All");
            SymbolTable.addFunction(concat);
            SymbolTable.changeScope(concat);
            SymbolTable.addParam(concat,"String");
            SymbolTable.addParam(concat2,"String");
            SymbolTable.updateReturnType("String");
            SymbolTable.addFunction(lenth);
            SymbolTable.changeScope(lenth);
            SymbolTable.updateReturnType("Integer");
            SymbolTable.addParam(lenth, "String");
        } catch (Exception e) {
            //Should never error 
        }
        scope = "";
        
    }
}
