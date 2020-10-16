package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;


public class SubtractOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Subtract";
    }

    @Override
    public boolean isValidOperation() {
        if (this.rhs.getExpressionType() == this.lhs.getExpressionType()) {
            this.value = lhs.getValue() - rhs.getValue();
            this.isOperable = true;
            this.expressionType = rhs.getExpressionType();
            return true;
        }
        return false;
    }
}
