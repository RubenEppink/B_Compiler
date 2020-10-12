package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        // variableTypes = new HANLinkedList<>();
            checkStyleSheet(ast.root);
    }
    private void checkStyleSheet(Stylesheet stylesheet) {
        for (ASTNode child : stylesheet.getChildren())
           if (child instanceof VariableAssignment) {
               checkVariableAssignment((VariableAssignment) child );

           } else {
               checkStyleRule((Stylerule) child);
           }
    }

    private void checkStyleRule(Stylerule stylerule) {
        //TODO
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;
        if (expression instanceof Literal) {
            checkLiteral((Literal) expression );
        } else {
            checkOperation((Operation) expression);
        }
    }

    private void checkOperation(Operation expression) {
        //TODO
    }

    private void checkLiteral(Literal literal) {
        //TODO
    }
}
