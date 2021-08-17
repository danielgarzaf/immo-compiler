package parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private SyntaxToken _tokens[];
    private int _position;
    private List<String> _diagnostics = new ArrayList<>();

    public Parser(String text) {
        Lexer lexer = new Lexer(text);
        SyntaxToken token;
        ArrayList<SyntaxToken> tokens = new ArrayList<>();
        do {
            token = lexer.nextToken();
            
            if (token.getKind() != SyntaxKind.WhiteSpaceToken &&
                    token.getKind() != SyntaxKind.BadToken)
                tokens.add(token);

        } while (token.getKind() != SyntaxKind.EndOfFileToken);

        _tokens = toArray(tokens);
        _diagnostics.addAll(lexer.getDiagnostics());

    }

    public List<String> getDiagnostics() {
        return _diagnostics;
    }

    private SyntaxToken[] toArray(ArrayList<SyntaxToken> tokens) {
        SyntaxToken[] arrTokens = new SyntaxToken[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            arrTokens[i] = tokens.get(i);
        }
        return arrTokens;
    }

    private SyntaxToken peek(int offset) {
        int index = _position + offset;
        if (index >= _tokens.length) {
            return _tokens[_tokens.length - 1];
        }
        return _tokens[index];
    }

    private SyntaxToken current() {
        return peek(0);
    }

    private SyntaxToken nextToken() {
        return _tokens[_position++];
    }

    public SyntaxTree parse() {
        ExpressionSyntax expression = parseTerm();
        SyntaxToken endOfFileToken = match(SyntaxKind.EndOfFileToken);
        return new SyntaxTree(_diagnostics, expression, endOfFileToken);
    }

    public ExpressionSyntax parseTerm() {
        ExpressionSyntax left = parseFactor();

        while (current().getKind() == SyntaxKind.PlusToken || 
                current().getKind() == SyntaxKind.MinusToken)
        {
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parseFactor();
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }

        return left;

    }

    public ExpressionSyntax parseFactor() {
        ExpressionSyntax left = parsePrimaryExpression();

        while (current().getKind() == SyntaxKind.StarToken || 
                current().getKind() == SyntaxKind.SlashToken) 
        {
            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parsePrimaryExpression();
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }

        return left;
    }

    private ExpressionSyntax parsePrimaryExpression() {
        if (current().getKind() == SyntaxKind.OpenParenthesisToken) {
            SyntaxToken left = nextToken();
            ExpressionSyntax expression = parseTerm();
            SyntaxToken right = match(SyntaxKind.CloseParenthesisToken);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }
        SyntaxToken numberToken = match(SyntaxKind.NumberToken);
        return new NumberExpressionSyntax(numberToken);
    }

    private SyntaxToken match(SyntaxKind kind) {
        if (current().getKind() == kind) {
            return nextToken();
        }

        _diagnostics.add(String.format("ERROR: Unexpected token <%s>, expected <%s>", current().getKind(), kind));
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }

}
        



