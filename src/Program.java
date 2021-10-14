import parser.*;

class Program {
    public static void main(String[] args) {
        boolean showTree = false;
        while (true) {
            System.out.print("> ");
            String input = System.console().readLine();

            if (input.isBlank()) {
                return;
            }

            if (input.equals("#exit")) {
                break;
            }
            if (input.equals("#showTree")) {
                showTree = !showTree;
                continue;
            }
            if (input.equals("#cls")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                continue;
            }

            SyntaxTree tree = SyntaxTree.parse(input);
            if (showTree) {
                String TEXT_YELLOW = "\u001B[33m";
                System.out.print(TEXT_YELLOW);
                prettyPrintTree(tree.getRoot(), "", true);
                String TEXT_RESET = "\u001B[0m";
                System.out.print(TEXT_RESET);
            }

            if (!(tree.getDiagnostics().size() > 0)) {
                Evaluator e = new Evaluator(tree.getRoot());
                int result = e.evaluate();
                System.out.println(result);
            } else {
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

    public static void prettyPrintTree(SyntaxNode node, String indent, boolean isLast) {
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
            prettyPrintTree(child, indent, child == lastChild);
        }
    }
}
