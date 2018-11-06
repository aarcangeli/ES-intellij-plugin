package com.github.aarcangeli.esj.psi.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class CParser implements PsiParser {
    public static final CParser INSTANCE = new CParser();

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        // TODO: Remove debug mode
        builder.setDebugMode(true);

        // this loop iterates over all tokens and marks them as a single psi element
        PsiBuilder.Marker rootMark = builder.mark();
        while (!builder.eof()) {
            builder.advanceLexer();
        }
        rootMark.done(root);

        return builder.getTreeBuilt();
    }
}
