package com.github.aarcangeli.esj.lexer;

import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.lexer.CTokens.*;

public interface CTokenSets {
    TokenSet IDENTIFIER_SET = TokenSet.create(IDENTIFIER);
    TokenSet COMMENT_SET = TokenSet.create(COMMENT);
    TokenSet LITERAL_SET = TokenSet.create(C_STRING, C_CHAR, C_INT);
}
