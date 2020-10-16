package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import static nl.han.ica.icss.checker.Checker.variableTypes;
import static nl.han.ica.icss.transforms.EvalExpressions.variableValues;

public class VariableReference extends Expression {

    public String name;


    public VariableReference(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "VariableReference (" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VariableReference that = (VariableReference) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean isOperable() {
        return true;
    }

    @Override
    public ExpressionType getExpressionType() {
        try {
            return variableTypes.getLast().containsKey(this.name) ?
                    variableTypes.getLast().get(this.name).getExpressionType() : variableTypes.get(0).get(this.name).getExpressionType();
        } catch (NoSuchElementException e) {
            return variableValues.getLast().containsKey(this.name) ?
                    variableValues.getLast().get(this.name).getExpressionType() : variableValues.get(0).get(this.name).getExpressionType();
        }

    }

    @Override
    public int getValue() {
        try {
            return variableTypes.getLast().containsKey(this.name) ?
                    variableTypes.getLast().get(this.name).getValue() : variableTypes.get(0).get(this.name).getValue();
        } catch (NoSuchElementException e) {
            return variableValues.getLast().containsKey(this.name) ?
                    variableValues.getLast().get(this.name).getValue() : variableValues.get(0).get(this.name).getValue();
        }
    }

    @Override
    public Literal getInstance(int value) {
        try {
            return variableTypes.getLast().containsKey(this.name) ?
                    variableTypes.getLast().get(this.name) : variableTypes.get(0).get(this.name);
        } catch (NoSuchElementException e) {
            return variableValues.getLast().containsKey(this.name) ?
                    variableValues.getLast().get(this.name) : variableValues.get(0).get(this.name);
        }
    }

}
