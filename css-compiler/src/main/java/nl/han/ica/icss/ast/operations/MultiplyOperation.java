package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;

import static nl.han.ica.icss.ast.types.ExpressionType.SCALAR;

public class MultiplyOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Multiply";
    }

    @Override
    public boolean isValidOperation() {
        if (this.rhs.getExpressionType() == SCALAR || this.lhs.getExpressionType() == SCALAR) {
            this.value = lhs.getValue() * rhs.getValue();
            this.isOperable = true;
            this.expressionType = determineExpressionType();
            return true;
        }
        return false;
    }

    private ExpressionType determineExpressionType() {
        return rhs.getExpressionType() == SCALAR ? lhs.getExpressionType() : rhs.getExpressionType();

    }
}
