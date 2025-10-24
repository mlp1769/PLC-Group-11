package parserclasses;

import java.util.HashMap;

public class SymbolTable {
    private static HashMap functionTable;
    private static HashMap varTable;
    private static String scope;

    public SymbolTable(){
        this.functionTable = new HashMap<>();
        this.varTable = new HashMap<>();
    }

    public void addFunction(String name, String type){

    }

    public void addVar(String name, String type){

    }

    public String getFunction(String name){
        return "";
    }
    
    public String getVar(String name){
        return "";
    }

    public void changeScope(String name){
        
    }
}
