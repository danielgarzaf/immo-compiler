package parser;

import java.util.List;

public class SyntaxTree {
    private List<String> _diagnostics;
    private ExpressionSyntax _root;
    private SyntaxToken _endOfFileToken;

    public SyntaxTree(List<String> diagnostics, ExpressionSyntax root, SyntaxToken endOfFileToken) {
        _diagnostics = diagnostics;
        _root = root;
        _endOfFileToken = endOfFileToken;
    }

    public List<String> getDiagnostics() {
        return _diagnostics;
    }

    public ExpressionSyntax getRoot() {
        return _root;
    }

    public SyntaxToken getEndOfFileToken() {
        return _endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        Parser parser = new Parser(text);
        return parser.parse();
    }
}
