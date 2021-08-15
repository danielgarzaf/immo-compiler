import parser.*;

class Program {
    public static void main(String[] args) {
        while (true) {
            System.out.print("> ");
            String input = System.console().readLine();
            if (input.isBlank()) {
                return;
            }

            Lexer lexer = new Lexer(input);
            while (true) {
                SyntaxToken token = lexer.nextToken();
                if (token.getKind() == SyntaxKind.EndOfFileToken) break;

                System.out.println(token.getKind() + ": " + token.getText());
                if (token.getValue() != null) System.out.print(" " + token.getValue());
                
            }
        }
    }
}
