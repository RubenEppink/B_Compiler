package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import static nl.han.ica.icss.ast.types.ExpressionType.*;
import static nl.han.ica.icss.checker.Checker.expressionError;

import java.util.ArrayList;

public abstract class Operation extends Expression {

    public Expression lhs;
    public Expression rhs;
    public ExpressionType expressionType = UNDEFINED;
    public boolean isOperable = false;
    public int value = 0;


    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if (lhs != null)
            children.add(lhs);
        if (rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (lhs == null) {
            lhs = (Expression) child;
        } else if (rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public boolean isOperable() {
        return isOperable;
    }


    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }

    @Override
    public void check() {
        if (this.rhs.isOperation()) {
            rhs.check();
        } else if (this.lhs.isOperation()) {
            lhs.check();
        }

        if (expressionError) return;

        if (!this.rhs.isOperable() || !this.lhs.isOperable()) {
            this.setError("You can't use Color or Boolean in an expression");
            expressionError = true;
        } else if (!this.isValidOperation()) {
            this.setError(this.lhs.getExpressionType() +
                    " and " + this.rhs.getExpressionType() +
                    " aren't allowed with " + this.getNodeLabel() +  " operator");
            expressionError = true;
        }
    }

    @Override
    public int getValue() {
        return this.value;
    }

}

