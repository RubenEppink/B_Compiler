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
        currentContainer.push(new AddOperation());
    }

    @Override
    public void exitPlus(ICSSParser.PlusContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterMin(ICSSParser.MinContext ctx) {
        currentContainer.push(new SubtractOperation());
    }

    @Override
    public void exitMin(ICSSParser.MinContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
    }

    @Override
    public void enterMultiply(ICSSParser.MultiplyContext ctx) {
        currentContainer.push(new MultiplyOperation());
    }

    @Override
    public void exitMultiply(ICSSParser.MultiplyContext ctx) {
        ASTNode astNode = currentContainer.pop();
        currentContainer.peek().addChild(astNode);
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


    }

    @Override
    public void exitExpression(ICSSParser.ExpressionContext ctx) {
        Expression expression;
        if (ctx.getChildCount() == 1) {

            String classname = ctx.children.get(0).getChild(0).getClass().getSimpleName();

            switch (classname) {
                case "BoolLiteralContext":
                    expression = new BoolLiteral(ctx.getChild(0).getText());
                    break;
                case "ColorLiteralContext":
                    expression = new ColorLiteral(ctx.getChild(0).getText());
                    break;
                case "PixelLiteralContext":
                    expression = new PixelLiteral(ctx.getChild(0).getText());
                    break;
                case "VariableReferenceContext":
                    expression = new VariableReference(ctx.getChild(0).getText());
                    break;
                case "ScalarLiteralContext":
                    expression = new ScalarLiteral(ctx.getChild(0).getText());
                    break;
                case "PercentageLiteralContext":
                    expression = new PercentageLiteral(ctx.getChild(0).getText());
                    break;
                default:
                    expression = null;
            }
            currentContainer.push(expression);
        }

        if (ctx.getChildCount() == 3) {

            char operator = ctx.getChild(1).getText().charAt(0);
            switch (operator) {
                case '+':
                    expression = new AddOperation();
                    break;
                case '*':
                    expression = new MultiplyOperation();
                default:
                    expression = new SubtractOperation();
            }
            expression.addChild(currentContainer.pop());
            expression.addChild(currentContainer.pop());
            currentContainer.push(expression);

        }


    }


    @Override
    public void enterIfClause(ICSSParser.IfClauseContext ctx) {
        super.enterIfClause(ctx);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        super.exitIfClause(ctx);
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
        super.enterElseClause(ctx);
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        super.exitElseClause(ctx);
    }
}