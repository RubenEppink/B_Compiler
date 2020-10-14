package nl.han.ica.icss.checker;


import nl.han.ica.icss.ast.*;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;


public class Checker {

    public static boolean expressionError;
    public static LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        ast.root.check();
    }

    public static void removeScopeFromVariableTypes(HashMap<String, ExpressionType> hashmap) {
        variableTypes.remove(hashmap);
    }

    public static void addScopeToVariableTypes(HashMap<String, ExpressionType> hashmap) {
        variableTypes.add(hashmap);
    }
}
