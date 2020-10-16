package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.Check;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.transforms.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static nl.han.ica.icss.checker.Checker.addScopeToVariableTypes;
import static nl.han.ica.icss.checker.Checker.removeScopeFromVariableTypes;
import static nl.han.ica.icss.transforms.EvalExpressions.variableValues;

public class Stylerule extends ASTNode {
	
	public ArrayList<Selector> selectors = new ArrayList<>();
	public ArrayList<ASTNode> body = new ArrayList<>();

    public Stylerule() { }

    public Stylerule(Selector selector, ArrayList<ASTNode> body) {

    	this.selectors = new ArrayList<>();
    	this.selectors.add(selector);
    	this.body = body;
    }

    @Override
	public String getNodeLabel() {
		return "Stylerule";
	}

	@Override
	public ArrayList<ASTNode> getChildren() {
		ArrayList<ASTNode> children = new ArrayList<>();
		children.addAll(selectors);
		children.addAll(body);

		return children;
	}

    @Override
    public ASTNode addChild(ASTNode child) {
		if(child instanceof Selector)
			selectors.add((Selector) child);
		else
        	body.add(child);

		return this;
    }
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Stylerule stylerule = (Stylerule) o;
		return Objects.equals(selectors, stylerule.selectors) &&
				Objects.equals(body, stylerule.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(selectors, body);
	}

	@Override
	public void check() {
		HashMap<String, ExpressionType> hashmap = new HashMap<>();
		addScopeToVariableTypes(hashmap);

		this.getChildren().forEach(Check::check);

		removeScopeFromVariableTypes(hashmap);
	}

	@Override
	public ArrayList<ASTNode> transform() {
    	ArrayList<ASTNode> astNodeArrayList = new ArrayList<>();

		body.forEach(child -> {
			if (!child.isClause()) {
				astNodeArrayList.add(child);
			}
			astNodeArrayList.addAll(child.transform());
		});

		body = astNodeArrayList;

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
