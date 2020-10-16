package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.generator.Generate;

import java.util.ArrayList;

public abstract class Transformer extends Generate {

    public ArrayList<ASTNode> transform() {
        return new ArrayList<>();
    }

    public boolean isClause() {
        return false;
    }

    public void evaluate() {

    }
}
