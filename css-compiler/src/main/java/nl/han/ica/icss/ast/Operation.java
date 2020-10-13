package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import static nl.han.ica.icss.ast.types.ExpressionType.*;

import java.util.ArrayList;

public abstract class Operation extends Expression {

    public Expression lhs;
    public Expression rhs;
    public ExpressionType expressionType = UNDEFINED;
    public boolean isOperable = false;


    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(lhs != null)
            children.add(lhs);
        if(rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
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
}
