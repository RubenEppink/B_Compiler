package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.ScalarLiteral;

public abstract class Expression extends ASTNode {
    public int getValue() {
        return 0;
    }

    public Literal getInstance(int value){
        return new ScalarLiteral(value);
    }

}
