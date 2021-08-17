package parser;

import java.util.List;

public interface SyntaxNode {
    public SyntaxKind getKind();
    
    public List<SyntaxNode> getChildren();
}
