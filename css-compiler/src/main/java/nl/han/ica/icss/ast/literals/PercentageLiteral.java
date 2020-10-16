package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Objects;

public class PercentageLiteral extends Literal {
    public int value;

    public PercentageLiteral(int value) {
        this.value = value;
    }
    public PercentageLiteral(String text) {
        this.value = Integer.parseInt(text.substring(0, text.length() - 1));
    }
    @Override
    public String getNodeLabel() {
        return "Percentage literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PercentageLiteral that = (PercentageLiteral) o;
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
        return ExpressionType.PERCENTAGE;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Literal getInstance(int value) {
        return new PercentageLiteral(value);
    }

    @Override
    public String generate() {
         return value + "%";
    }
}
