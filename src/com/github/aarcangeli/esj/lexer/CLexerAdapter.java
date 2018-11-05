package com.github.aarcangeli.esj.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * This class adapt the api from lexer (yybegin(), yystate() ecc) to IntelliJ specific interface {@link com.intellij.lexer.Lexer}
 */
public class CLexerAdapter extends FlexAdapter {
    public CLexerAdapter() {
        super(new CLexer(null));
    }
}
