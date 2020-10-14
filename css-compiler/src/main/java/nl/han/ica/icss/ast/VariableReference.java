package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.Checker;

import java.util.HashMap;
import java.util.Objects;

public class VariableReference extends Expression {

	public String name;
	public ExpressionType expressionType;
	
	public VariableReference(String name) {
		super();
		this.name = name;
		this.expressionType = ExpressionType.UNDEFINED;
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
		HashMap<String, ExpressionType> hashmap = Checker.variableTypes.getLast();

		//if variabletypes only has one element, then there's only a global scope
		//check if the variable is in that scope and return, else return undefined
		if (Checker.variableTypes.size() == 1) {
			return hashmap.getOrDefault(name, ExpressionType.UNDEFINED);
		}

		//else check the last scope (current scope we're in) and return if present, else check global scope (first element in the list)
		//and return variable if present else return UNDEFINED
		return hashmap.containsKey(name) ? hashmap.get(name) : Checker.variableTypes.get(0).getOrDefault(name, ExpressionType.UNDEFINED);
	}
}
