package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CClassParser implements CElementTypes {

    static void parseClass(PsiBuilder builder) {
        assert builder.getTokenType() == K_CLASS;
        PsiBuilder.Marker statement = builder.mark();
        builder.advanceLexer();
        expect(builder, K_EXPORT);

        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }
        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!expect(builder, LBRACE)) {
            builder.error("'{' expected");
        }

        parseClassBody(builder);

        if (!expect(builder, RBRACE)) {
            builder.error("'}' expected");
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }
        statement.done(SE_CLASS_STATEMENT);
    }

    private static void parseClassBody(PsiBuilder builder) {
        // TODO: develop this
        // currently the parser skip everything inside braces
        int braceCount = 0;
        while (!builder.eof()) {
            if (builder.getTokenType() == LBRACE) braceCount++;
            if (builder.getTokenType() == RBRACE) {
                if (braceCount == 0) break;
                braceCount--;
            }
            builder.advanceLexer();
        }
    }
}
