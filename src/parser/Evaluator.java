package parser;

public class Evaluator {
    private ExpressionSyntax _root;

    public Evaluator(ExpressionSyntax root) {
        _root = root;
    }

    public int evaluate() {
        try {
            return evaluateExpression(_root);

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int evaluateExpression(ExpressionSyntax node) throws Exception {
        if (node instanceof LiteralExpressionSyntax) {
            LiteralExpressionSyntax n = (LiteralExpressionSyntax) node;
            return (int) n.getNumberToken().getValue();
        }

        if (node instanceof BinaryExpressionSyntax) {
            BinaryExpressionSyntax b = (BinaryExpressionSyntax) node;
            int left = evaluateExpression(b.getLeft());
            int right = evaluateExpression(b.getRight());

            if (b.getOperatorToken().getKind() == SyntaxKind.PlusToken) {
                return left + right;
            } else if (b.getOperatorToken().getKind() == SyntaxKind.MinusToken) {
                return left - right;
            } else if (b.getOperatorToken().getKind() == SyntaxKind.StarToken) {
                return left * right;
            } else if (b.getOperatorToken().getKind() == SyntaxKind.SlashToken) {
                return left / right;
            } else {
                throw new Exception(String.format("Unexpected binary operator %s", b.getOperatorToken().getKind()));
            }

        }

        if (node instanceof ParenthesizedExpressionSyntax) {
            ParenthesizedExpressionSyntax p = (ParenthesizedExpressionSyntax) node;
            return evaluateExpression(p.getExpression());
        }

        throw new Exception(String.format("Unexpected node %s", node.getKind()));
    }
}
