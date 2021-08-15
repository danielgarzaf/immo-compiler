package parser;

public class Lexer {
    private String _text;
    private int _position;

    public Lexer(String text) {
        _text = text;
    }

    private char current() {
        if (_position >= _text.length())
            return '\u001a';
        return _text.charAt(_position);
    }

    private void next() {
        _position++;
    }

    public SyntaxToken nextToken() {
        if (_position >= _text.length()) {
            return new SyntaxToken(SyntaxKind.EndOfFileToken, _position, "\0", null);
        }
        
        if (Character.isDigit(current())) {
            int start = _position;

            while (Character.isDigit(current())) {
                next();
            }

            String text = _text.substring(start, _position);
            int value = Integer.parseInt(text);

            return new SyntaxToken(SyntaxKind.NumberToken, start, text, value);
        }

        if (Character.isWhitespace(current())) {
            int start = _position;

            while (Character.isDigit(current())) {
                next();
            }

            String text = _text.substring(start, _position);
            return new SyntaxToken(SyntaxKind.WhiteSpaceToken, start, text, null);

        }

        if (current() == '+') {
            return new SyntaxToken(SyntaxKind.PlusToken, _position++, "+", null);
        }

        else if (current() == '-') {
            return new SyntaxToken(SyntaxKind.MinusToken, _position++, "-", null);
        }

        else if (current() == '*') {
            return new SyntaxToken(SyntaxKind.StarToken, _position++, "*", null);
        }

        else if (current() == '/') {
            return new SyntaxToken(SyntaxKind.SlashToken, _position++, "/", null);
        }

        else if (current() == '(') {
            return new SyntaxToken(SyntaxKind.OpenParenthesisToken, _position++, "(", null);
        }

        else if (current() == ')') {
            return new SyntaxToken(SyntaxKind.CloseParenthesisToken, _position++, ")", null);
        }

        return new SyntaxToken(SyntaxKind.BadToken, _position++, _text.substring(_position - 1, _position), null);
    }
}
