package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiteralExpressionSyntax implements ExpressionSyntax {
    private SyntaxToken _literalToken;

    public LiteralExpressionSyntax(SyntaxToken literalToken) {
        _literalToken = literalToken;
    }

    public SyntaxKind getKind() {
        return SyntaxKind.LiteralExpression;
    }

    public SyntaxToken getNumberToken() {
        return _literalToken;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return new ArrayList<>(Arrays.asList(_literalToken));
    }
}
