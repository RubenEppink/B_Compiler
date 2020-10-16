package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.Check;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.transforms.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static nl.han.ica.icss.checker.Checker.removeScopeFromVariableTypes;
import static nl.han.ica.icss.transforms.EvalExpressions.variableValues;

/**
 * A stylesheet is the root node of the AST, it consists of one or more statements
 */
public class Stylesheet extends ASTNode {


    public ArrayList<ASTNode> body;

    public Stylesheet() {
        this.body = new ArrayList<>();
    }

    public Stylesheet(ArrayList<ASTNode> body) {
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Stylesheet";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return this.body;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        body.add(child);
        return this;
    }

    @Override
    public ASTNode removeChild(ASTNode child) {
        body.remove(child);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stylesheet that = (Stylesheet) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public void check() {
        HashMap<String, ExpressionType> hashmap = new HashMap<>();
        Checker.addScopeToVariableTypes(hashmap);

        getChildren().forEach(Check::check);

        removeScopeFromVariableTypes(hashmap);
    }

    @Override
    public ArrayList<ASTNode> transform() {
        this.getChildren().forEach(Transformer::transform);
        return new ArrayList<>();
    }

    @Override
    public void evaluate() {
        HashMap<String, Literal> hashMap = new HashMap<>();
        variableValues.add(hashMap);

        body.forEach(Transformer::evaluate);

        variableValues.removeLast();
    }
}
