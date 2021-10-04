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
            token = lexer.lex();

            if (token.getKind() != SyntaxKind.WhiteSpaceToken && token.getKind() != SyntaxKind.BadToken)
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
        ExpressionSyntax expression = parseExpression();
        SyntaxToken endOfFileToken = matchToken(SyntaxKind.EndOfFileToken);
        return new SyntaxTree(_diagnostics, expression, endOfFileToken);
    }

    public ExpressionSyntax parseExpression() {
        return parseExpression(0);
    }

    private ExpressionSyntax parseExpression(int parentPrecedence) {
        ExpressionSyntax left = parsePrimaryExpression();

        while (true) {
            int precedence = getBinaryOperatorPrecedence(current().getKind());
            if (precedence == 0 || precedence <= parentPrecedence)
                break;

            SyntaxToken operatorToken = nextToken();
            ExpressionSyntax right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }

        return left;
    }

    private static int getBinaryOperatorPrecedence(SyntaxKind kind) {
        switch (kind) {
            case StarToken:
            case SlashToken:
                return 2;

            case PlusToken:
            case MinusToken:
                return 1;

            default:
                return 0;
        }
    }

    private ExpressionSyntax parsePrimaryExpression() {
        if (current().getKind() == SyntaxKind.OpenParenthesisToken) {
            SyntaxToken left = nextToken();
            ExpressionSyntax expression = parseExpression();
            SyntaxToken right = matchToken(SyntaxKind.CloseParenthesisToken);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }
        SyntaxToken numberToken = matchToken(SyntaxKind.NumberToken);
        return new LiteralExpressionSyntax(numberToken);
    }

    private SyntaxToken matchToken(SyntaxKind kind) {
        if (current().getKind() == kind) {
            return nextToken();
        }

        _diagnostics.add(String.format("ERROR: Unexpected token <%s>, expected <%s>", current().getKind(), kind));
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }

}
