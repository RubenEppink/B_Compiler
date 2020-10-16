package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        HashMap<String, ExpressionType> hashmap = new HashMap<>();

        try {
            hashmap = Checker.variableTypes.getLast();
        } catch (NoSuchElementException e) {
            HashMap<String, Literal> map = variableValues.getLast();
            if (variableValues.size() == 1) {
                return map.getOrDefault(name, new ScalarLiteral(0)).getExpressionType();
            }
            return map.containsKey(name) ? map.get(name).getExpressionType() : variableValues.get(0).getOrDefault(name, new ScalarLiteral(0)).getExpressionType();
        }
        //if variabletypes only has one element, then there's only a global scope
        // or you're in the local scope.
        //check if the variable is in that scope and return, else return undefined
        if (Checker.variableTypes.size() == 1) {
            return hashmap.getOrDefault(name, ExpressionType.UNDEFINED);
        }

        //else check the last scope (current scope we're in) and return if present, else check global scope (first element in the list)
        //and return variable if present else return UNDEFINED
        return hashmap.containsKey(name) ? hashmap.get(name) : Checker.variableTypes.get(0).getOrDefault(name, ExpressionType.UNDEFINED);

    }

    @Override
    public int getValue() {
        return variableValues.getLast().getOrDefault(this.name, new ScalarLiteral(0)).getValue();
    }

    @Override
    public Literal getInstance(int value) {
        return variableValues.getLast().getOrDefault(this.name, new ScalarLiteral(0));
    }
}
