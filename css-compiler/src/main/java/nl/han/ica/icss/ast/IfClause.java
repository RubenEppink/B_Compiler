package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.checker.Check;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.transforms.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static nl.han.ica.icss.ast.types.ExpressionType.BOOL;
import static nl.han.ica.icss.checker.Checker.addScopeToVariableTypes;
import static nl.han.ica.icss.checker.Checker.removeScopeFromVariableTypes;

public class IfClause extends ASTNode {


    public Expression conditionalExpression;
    public ArrayList<ASTNode> body = new ArrayList<>();
    public ElseClause elseClause;

    public IfClause() {
    }

    public IfClause(Expression conditionalExpression, ArrayList<ASTNode> body) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
    }

    public IfClause(Expression conditionalExpression, ArrayList<ASTNode> body, ElseClause elseClause) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
        this.elseClause = elseClause;
    }

    @Override
    public String getNodeLabel() {
        return "If_Clause";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(conditionalExpression);
        children.addAll(body);
        if (elseClause != null)
            children.add(elseClause);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (child instanceof Expression)
            conditionalExpression = (Expression) child;
        else if (child instanceof ElseClause)
            elseClause = (ElseClause) child;
        else
            body.add(child);

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IfClause ifClause = (IfClause) o;
        if (this.elseClause == null)
            return Objects.equals(conditionalExpression, ifClause.getConditionalExpression()) &&
                    Objects.equals(body, ifClause.body);
        else
            return Objects.equals(conditionalExpression, ifClause.getConditionalExpression()) &&
                    Objects.equals(body, ifClause.body) &&
                    Objects.equals(elseClause, ifClause.elseClause);

    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionalExpression, body, elseClause);
    }

    public Expression getConditionalExpression() {
        return conditionalExpression;
    }

    public ElseClause getElseClause() {
        return elseClause;
    }

    @Override
    public void check() {
        HashMap<String, Literal> hashmap = new HashMap<>();
        addScopeToVariableTypes(hashmap);

        if (this.conditionalExpression.getExpressionType() != BOOL) {
            this.setError("Conditional expression has to be a boolean");
            //return;
        }

        this.getChildren().forEach(Check::check);

        removeScopeFromVariableTypes(hashmap);
    }

    @Override
    public ArrayList<ASTNode> transform() {
        ArrayList<ASTNode> astNodeArraylist = new ArrayList<>();

        if (((BoolLiteral) conditionalExpression).value) {
            body.forEach(child -> {
                if (!child.isClause()) {
                    astNodeArraylist.add(child);
                }
                astNodeArraylist.addAll(child.transform());
            });

        } else if (this.elseClause != null) {
            return elseClause.transform();
        }

        return astNodeArraylist;
    }

    @Override
    public boolean isClause() {
        return true;
    }

    @Override
    public void evaluate() {
        getChildren().forEach(Transformer::evaluate);
        conditionalExpression = conditionalExpression.getInstance(0);
    }
}


