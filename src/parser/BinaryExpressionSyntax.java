package parser;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpressionSyntax implements ExpressionSyntax {
    private ExpressionSyntax _left;
    private SyntaxToken _operatorToken;
    private ExpressionSyntax _right;

    public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operatorToken, ExpressionSyntax right) {
        _left = left;
        _operatorToken = operatorToken;
        _right = right;
    }

    public SyntaxKind getKind() {
        return SyntaxKind.BinaryExpression;
    }

    public ExpressionSyntax getLeft() {
        return _left;
    }

    public SyntaxToken getOperatorToken() {
        return _operatorToken;
    }

    public ExpressionSyntax getRight() {
        return _right;
    }

	@Override
	public List<SyntaxNode> getChildren() {
        List<SyntaxNode> children = new ArrayList<>();
        children.add(_left);
        children.add(_operatorToken);
        children.add(_right);
        return children;
	}
}
