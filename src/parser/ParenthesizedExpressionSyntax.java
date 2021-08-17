package parser;

import java.util.ArrayList;
import java.util.List;

public class ParenthesizedExpressionSyntax implements ExpressionSyntax {
    private SyntaxToken _openParenthesisToken;
    private ExpressionSyntax _expression;
    private SyntaxToken _closeParenthesisToken;

    public ParenthesizedExpressionSyntax(SyntaxToken openParenthesisToken, ExpressionSyntax expression, SyntaxToken closeParenthesisToken) {
        _openParenthesisToken = openParenthesisToken;
        _expression = expression;
        _closeParenthesisToken = closeParenthesisToken;
    }

    public SyntaxToken getOpenParenthesisToken() {
        return _openParenthesisToken;
    }

    public ExpressionSyntax getExpression() {
        return _expression;
    }

    public SyntaxToken getCloseParenthesisToken() {
        return _closeParenthesisToken;
    }

	@Override
	public SyntaxKind getKind() {
		return SyntaxKind.ParenthesizedExpression;
	}

	@Override
	public List<SyntaxNode> getChildren() {
        List<SyntaxNode> children = new ArrayList<>();
        children.add(_openParenthesisToken);
        children.add(_expression);
        children.add(_closeParenthesisToken);
		return children;
	}
}
