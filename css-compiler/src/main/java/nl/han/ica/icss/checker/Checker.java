package nl.han.ica.icss.checker;


import nl.han.ica.icss.ast.*;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.*;

import static nl.han.ica.icss.ast.types.ExpressionType.*;


public class Checker {

    private boolean expressionError;
    public static LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        checkStyleSheet(ast.root);
    }

    private void checkStyleSheet(Stylesheet stylesheet) {
        HashMap<String, ExpressionType> hashmap = new HashMap<>();
        addScopeToVariableTypes(hashmap);

        stylesheet.getChildren().forEach(child -> {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStyleRule((Stylerule) child);
            }
        });

        removeScopeFromVariableTypes(hashmap);
    }

    private void removeScopeFromVariableTypes(HashMap<String, ExpressionType> hashmap) {
        variableTypes.remove(hashmap);
    }

    private void addScopeToVariableTypes(HashMap<String, ExpressionType> hashmap) {
        variableTypes.add(hashmap);
    }

    private void checkStyleRule(Stylerule stylerule) {
        HashMap<String, ExpressionType> hashmap = new HashMap<>();
        addScopeToVariableTypes(hashmap);

        stylerule.getChildren().forEach(child -> {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            } else if (child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);

            }
        });
        removeScopeFromVariableTypes(hashmap);
    }


    private void checkIfClause(IfClause ifClause) {
        HashMap<String, ExpressionType> hashmap = new HashMap<>();
        addScopeToVariableTypes(hashmap);

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

        removeScopeFromVariableTypes(hashmap);

    }

    private void checkElseClause(ElseClause elseClause) {
        HashMap<String, ExpressionType> hashmap = new HashMap<>();
        addScopeToVariableTypes(hashmap);

        elseClause.body.forEach(child -> {
            if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

        });

        removeScopeFromVariableTypes(hashmap);
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
        variableTypes.getLast().put(name, expressionType);
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
