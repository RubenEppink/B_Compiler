package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.generator.Generate;

import java.util.ArrayList;

public abstract class Transformer extends Generate {

    /**
     * used for removing ifClauses.
     * Traverses the ast tree and determines
     * which code within an if statement is
     * relevant
     *
     * @return a list containing the nodes that were deemed relevant by the method
     */
    public ArrayList<ASTNode> transform() {
        return new ArrayList<>();
    }

    /**
     * used to check if something is an if or else clause
     *
     * @return true if current class is if or else clause
     */
    public boolean isClause() {
        return false;
    }

    /**
     * used to traverse the ast tree and
     * transform all expressions in to literals
     */
    public void evaluate() {

    }
}
