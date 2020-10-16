package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.Objects;

import static nl.han.ica.icss.ast.types.ExpressionType.*;

/*
 * A Declaration defines a style property. Declarations are things like "width: 100px"
 */
public class Declaration extends ASTNode {
    public PropertyName property;
    public Expression expression;

    public Declaration() {
        super();
    }

    public Declaration(String property) {
        super();
        this.property = new PropertyName(property);
    }

    @Override
    public String getNodeLabel() {
        return "Declaration";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {

        ArrayList<ASTNode> children = new ArrayList<>();
        if (property != null)
            children.add(property);
        if (expression != null)
            children.add(expression);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (child instanceof PropertyName) {
            property = (PropertyName) child;
        } else if (child instanceof Expression) {
            expression = (Expression) child;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Declaration that = (Declaration) o;
        return Objects.equals(property, that.property) &&
                Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, expression);
    }

    @Override
    public void check() {
        ExpressionType expressionType;

        this.expression.check();

        expressionType = this.expression.getExpressionType();

        switch (this.property.name) {
            case "color":
            case "background-color":
                if (expressionType != COLOR) {
                    this.setError(expressionType + " isn't compatible with " + this.property.name);
                }
                break;
            case "width":
            case "height":
                if (expressionType != PIXEL && expressionType != PERCENTAGE) {
                    this.setError(expressionType + " isn't compatible with " + this.property.name);
                }
                break;
            default:
                this.setError(this.property.name + " is not a valid property");
        }
    }

    @Override
    public void evaluate() {
        expression = expression.getInstance(expression.getValue());
    }
}
