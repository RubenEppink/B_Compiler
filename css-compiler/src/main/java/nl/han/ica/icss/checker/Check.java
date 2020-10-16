package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.transforms.Transformer;

public abstract class Check extends Transformer {

    /**
     * Value than can be used in a multiply,
     * add or subtract operation.
     * @return true if value can be used in operation
     */
    public boolean isOperable() {
        return false;
    }

    /**
     * Checks if the current expression is an operation
     * @return true if current expression is an operation
     */
    public boolean isOperation() {
        return false;
    }

    /**
     * Checks if the expressions on either side
     * of an operation are compatible
     * @return true if expressions are compatible
     */
    public boolean isValidOperation() {
        return false;
    }

    /**
     *
     * @return Enum containing the expression type
     */
    public ExpressionType getExpressionType() {
        return ExpressionType.UNDEFINED;
    }

    public void check(){

    }
}
