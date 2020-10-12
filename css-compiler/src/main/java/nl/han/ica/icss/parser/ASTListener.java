package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private AST ast;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private IHANStack<ASTNode> currentContainer;

    public ASTListener() {
        ast = new AST();
        currentContainer = new HANStack<>();
    }

    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        currentContainer.push(new Stylesheet());
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        ast.setRoot((Stylesheet) currentContainer.pop());
    }

    @Override
    public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
        currentContainer.push(new Stylerule());
    }

    @Override
    public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        currentContainer.push(new Declaration());
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterSelector(ICSSParser.SelectorContext ctx) {

    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {

    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        currentContainer.push(new TagSelector(ctx.getText()));
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        currentContainer.push(new IdSelector(ctx.getText()));
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        currentContainer.push(new ClassSelector(ctx.getText()));
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterValue(ICSSParser.ValueContext ctx) {
    }

    @Override
    public void exitValue(ICSSParser.ValueContext ctx) {
    }

    @Override
    public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        currentContainer.push(new ColorLiteral(ctx.getText()));
    }

    @Override
    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ASTNode astNode = currentContainer.pop();
        System.out.println(currentContainer.peek());
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
         currentContainer.push(new PixelLiteral(ctx.getText()));
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        currentContainer.push(new PercentageLiteral(ctx.getText()));
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        ASTNode astNode = currentContainer.pop();
         currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
         currentContainer.push(new BoolLiteral(ctx.getText()));
    }

    @Override
    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
         ASTNode astNode = currentContainer.pop();
         currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        currentContainer.push(new ScalarLiteral(ctx.getText()));
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        currentContainer.push(new VariableAssignment());
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterPropertyName(ICSSParser.PropertyNameContext ctx) {
        currentContainer.push(new PropertyName(ctx.getText()));
    }

    @Override
    public void exitPropertyName(ICSSParser.PropertyNameContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterPlus(ICSSParser.PlusContext ctx) {

    }

    @Override
    public void exitPlus(ICSSParser.PlusContext ctx) {

    }

    @Override
    public void enterMin(ICSSParser.MinContext ctx) {
    }

    @Override
    public void exitMin(ICSSParser.MinContext ctx) {

    }

    @Override
    public void enterMultiply(ICSSParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(ICSSParser.MultiplyContext ctx) {

    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        currentContainer.push(new VariableReference(ctx.getText()));
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterExpression(ICSSParser.ExpressionContext ctx) {
        Expression expression = null;
        if (ctx.getChildCount() == 3) {
            if (ctx.getChild(1).getText().equals("*")) {
                expression = new MultiplyOperation();
            }
            if (ctx.getChild(1).getText().equals("+")) {
                expression = new AddOperation();
            }
            if (ctx.getChild(1).getText().equals("-")) {
                expression = new SubtractOperation();
            }
            currentContainer.push(expression);
        }
    }

    @Override
    public void exitExpression(ICSSParser.ExpressionContext ctx) {
        if (ctx.plus() != null | ctx.min() != null | ctx.multiply() != null) {
            ASTNode operation = currentContainer.pop();
            currentContainer.peek().addChild(operation);
        }
    }


    @Override
    public void enterIfClause(ICSSParser.IfClauseContext ctx) {
        currentContainer.push(new IfClause());
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
        currentContainer.push(new ElseClause());
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }
}