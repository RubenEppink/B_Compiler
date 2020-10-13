package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static nl.han.ica.icss.ast.types.ExpressionType.*;


public class Checker {

    private boolean expressionError;
    private List<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        checkStyleSheet(ast.root);
    }

    private void checkStyleSheet(Stylesheet stylesheet) {
        stylesheet.getChildren().forEach(child -> {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                //checkStyleRule((Stylerule) child);
            }
        });
    }

    private void checkStyleRule(Stylerule stylerule) {
        stylerule.getChildren().forEach(child -> {
            if (child instanceof Declaration) {
                // checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                // checkIfClause((IfClause) child);
            }
        });
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;

        if (expression instanceof Operation) {
            expressionError = false;
            checkOperation((Operation) expression);
        } else if (expression instanceof Literal) {
            // checkLiteral(variableAssignment);
        }
    }

    private void checkOperation(Operation operation) {
        if (operation.rhs.isOperation()) {
            checkOperation((Operation) operation.rhs);
        } else if (operation.lhs.isOperation()) {
            checkOperation((Operation) operation.lhs);
        }

        if (expressionError) return;

        if (!operation.rhs.isOperable() || !operation.lhs.isOperable()) {
            operation.setError("You can't use Color or Boolean in an expression");
            expressionError = true;
        } else if (!operation.isValidOperation()) {
            operation.setError(operation.lhs.getExpressionType().name() + " and " + operation.rhs.getExpressionType() + " aren't allowed with this operator");
            expressionError = true;
        }
    }
}
