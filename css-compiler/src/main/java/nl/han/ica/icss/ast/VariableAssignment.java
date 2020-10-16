package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;

import java.util.ArrayList;
import java.util.Objects;

import static nl.han.ica.icss.ast.types.ExpressionType.*;
import static nl.han.ica.icss.checker.Checker.*;
import static nl.han.ica.icss.transforms.EvalExpressions.variableValues;

/**
 * An assignment binds a expression to an identifier.
 */
public class VariableAssignment extends ASTNode {

    public VariableReference name;
    public Expression expression;

    @Override
    public String getNodeLabel() {
        return "VariableAssignment (" + name.name + ")";
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (name == null) {
            name = (VariableReference) child;
        } else if (expression == null) {
            expression = (Expression) child;
        }

        return this;
    }

    @Override
    public ArrayList<ASTNode> getChildren() {

        ArrayList<ASTNode> children = new ArrayList<>();
        if (name != null)
            children.add(name);
        if (expression != null)
            children.add(expression);
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VariableAssignment that = (VariableAssignment) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expression);
    }

    @Override
    public void check() {
        Expression expression = this.expression;

        expressionError = false;
        expression.check();

        //extra uitbreiding
        if (this.name.getInstance(0) != null && this.name.getInstance(0).getExpressionType() != expression.getExpressionType()) {
            setError("You can only declare a variable type once");
        } else {
            addVariableReferenceToList(this.name.name, expression.getInstance(expression.getValue()));
        }
    }


        private void addVariableReferenceToList (String name, Literal literal){
            variableTypes.getLast().put(name, literal);
        }

        @Override
        public void evaluate () {
            Expression expression = this.expression;
            Literal literal = expression.getInstance(expression.getValue());

            addVariableValueToList(this.name.name, literal);
            this.expression = literal;
        }

        private void addVariableValueToList (String name, Literal literal){
            variableValues.getLast().put(name, literal);
        }

    }
