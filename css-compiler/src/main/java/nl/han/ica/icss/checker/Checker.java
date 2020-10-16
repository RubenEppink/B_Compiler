package nl.han.ica.icss.checker;


import nl.han.ica.icss.ast.*;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;


public class Checker {

    public static boolean expressionError;
    public static LinkedList<HashMap<String, Literal>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        ast.root.check();
    }

    public static void removeScopeFromVariableTypes(HashMap<String, Literal> hashmap) {
        variableTypes.remove(hashmap);
    }

    public static void addScopeToVariableTypes(HashMap<String, Literal> hashmap) {
        variableTypes.add(hashmap);
    }
}
