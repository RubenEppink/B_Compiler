package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Objects;

public class ScalarLiteral extends Literal {
    public int value;

    public ScalarLiteral(int value) {
        this.value = value;
    }
    public ScalarLiteral(String text) {
        this.value = Integer.parseInt(text);
    }
    @Override
    public String getNodeLabel() {
        return "Scalar literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ScalarLiteral that = (ScalarLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean isOperable() {
        return true;
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.SCALAR;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Literal getInstance(int value) {
        return new ScalarLiteral(value);
    }

    @Override
    public String generate() {
        return value + "";
    }
}
