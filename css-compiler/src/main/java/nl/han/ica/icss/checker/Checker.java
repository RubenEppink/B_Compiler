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
    public static List<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        checkStyleSheet(ast.root);
    }

    private void checkStyleSheet(Stylesheet stylesheet) {
        stylesheet.getChildren().forEach(child -> {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStyleRule((Stylerule) child);
            }
        });
    }

    private void checkStyleRule(Stylerule stylerule) {
        stylerule.getChildren().forEach(child -> {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        });
    }

    private void checkIfClause(IfClause ifClause) {
        if (ifClause.conditionalExpression.getExpressionType() != BOOL) {
            ifClause.setError("Conditional expression has to be a boolean");
        }

        ifClause.getChildren().forEach(child -> {
                    if (child instanceof IfClause) {
                        checkIfClause((IfClause) child);
                    } else if (child instanceof Declaration) {
                        checkDeclaration((Declaration) child);
                    } else if (child instanceof ElseClause) {
                        checkElseClause((ElseClause) child);
                    }
                }
        );


    }

    private void checkElseClause(ElseClause elseClause) {
        elseClause.body.forEach(child -> {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

        });
    }

    private void checkDeclaration(Declaration declaration) {
        ExpressionType expressionType;

        if (declaration.expression.isOperation()) {
            checkOperation((Operation) declaration.expression);

        }
        expressionType = declaration.expression.getExpressionType();

        switch (declaration.property.name) {
            case "color":
            case "background-color":
                if (expressionType != COLOR) {
                    declaration.setError(expressionType + " isn't compatible with the a color property");
                }
                break;
            case "width":
            case "height":
                if (expressionType != PIXEL && expressionType != PERCENTAGE) {
                    declaration.setError(expressionType + " isn't compatible with a size property");
                }
                break;
        }
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;

        if (expression instanceof Operation) {
            expressionError = false;
            checkOperation((Operation) expression);
        }

        addVariableReferenceToList(variableAssignment.name.name, expression.getExpressionType());
    }

    private void addVariableReferenceToList(String name, ExpressionType expressionType) {
        HashMap<String, ExpressionType> variableWithType = new HashMap<>();
        variableWithType.put(name, expressionType);
        variableTypes.add(variableWithType);
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
            operation.setError(operation.lhs.getExpressionType() + " and " + operation.rhs.getExpressionType() + " aren't allowed with this operator");
            expressionError = true;
        }
    }
}
