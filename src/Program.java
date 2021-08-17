import parser.*;

class Program {
    public static void main(String[] args) {
        while (true) {
            System.out.print("> ");
            String input = System.console().readLine();

            if (input.isBlank()) {
                return;
            }

            SyntaxTree tree = SyntaxTree.parse(input);
            prettyPrint(tree.getRoot(), "", true);

            if (!(tree.getDiagnostics().size() > 0)) {
                Evaluator e = new Evaluator(tree.getRoot());
                int result = e.evaluate();
                System.out.println(result);
            } 
            else {
                String TEXT_RED = "\u001B[31m";
                System.out.print(TEXT_RED);

                for (String diagnostic : tree.getDiagnostics()) {
                    System.out.println(diagnostic);
                }

                String TEXT_RESET = "\u001B[0m";
                System.out.print(TEXT_RESET);
            }

        }
    }

    public static void prettyPrint(SyntaxNode node, String indent, boolean isLast) {
        String marker = isLast ? "└──" : "├──";

        System.out.print(indent);
        System.out.print(marker);
        System.out.print(node.getKind());

        if (node instanceof SyntaxToken) {
            SyntaxToken t = (SyntaxToken) node;
            if (t.getValue() != null) {
                System.out.print(" ");
                System.out.print(t.getValue());
            }
        }

        System.out.println();

        indent += isLast ? "    " : "│   ";

        int size = node.getChildren().size();
        SyntaxNode lastChild = size > 0 ? node.getChildren().get(size - 1) : null;

        for (SyntaxNode child : node.getChildren()) {
            prettyPrint(child, indent, child == lastChild);
        }
    }
}
