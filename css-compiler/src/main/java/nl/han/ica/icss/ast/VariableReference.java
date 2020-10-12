package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Objects;

public class VariableReference extends Expression {

	public String name;
	private ExpressionType expressionType;
	
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
}
