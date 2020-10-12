package nl.han.ica.icss.ast.types;

import nl.han.ica.icss.ast.ASTNode;

public abstract class Checkers {

    /**
     * Value than can be used in a multiply,
     * add or subtract operation.
     * @return true if value can be used in operation
     */
    public boolean isOperable() {
        return false;
    }

    public boolean isValidOperation() {
        return false;
    }

    public ExpressionType getExpressionType() {
        return ExpressionType.INVALID;
    }
}
