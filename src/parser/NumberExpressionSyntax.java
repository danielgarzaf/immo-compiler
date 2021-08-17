package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberExpressionSyntax implements ExpressionSyntax {
    private SyntaxToken _numberToken;

    public NumberExpressionSyntax(SyntaxToken numberToken) {
        _numberToken = numberToken;
    }

    public SyntaxKind getKind() {
        return SyntaxKind.NumberExpression;
    }

    public SyntaxToken getNumberToken() {
        return _numberToken;
    }

	@Override
	public List<SyntaxNode> getChildren() {
        return new ArrayList<>(Arrays.asList(_numberToken));
	}
}
