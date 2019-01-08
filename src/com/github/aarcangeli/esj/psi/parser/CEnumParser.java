package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraElement;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CEnumParser implements CElementTypes {
    public static final TokenSet NOT_GARBAGE = TokenSet.create(C_INT, IDENTIFIER, C_STRING, COMMA);
    public static final TokenSet ENUM_FIELD_START = TokenSet.create(C_INT, IDENTIFIER, C_STRING);

    static void parseEnumDeclaration(PsiBuilder builder) {
        assert builder.getTokenType() == K_ENUM;
        PsiBuilder.Marker statement = builder.mark();
        builder.advanceLexer();

        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!expect(builder, LBRACE)) {
            builder.error("'{' expected");
        }

        reportExtraElement(builder, COMMA, "enum cannot start with ','");

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE, "field expected", true)) continue;
            if (expect(builder, COMMA)) continue;

            parseEnummField(builder);

            if (ENUM_FIELD_START.contains(builder.getTokenType())) {
                builder.error("',' expected");
            }

            if (expect(builder, COMMA)) {
                // report extra comma
                reportExtraElement(builder, COMMA, "repeated ','");
            }
        }

        if (!expect(builder, RBRACE)) {
            builder.error("'}' expected");
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(SE_ENUM_STATEMENT);
    }

    private static void parseEnummField(PsiBuilder builder) {
        PsiBuilder.Marker field = builder.mark();
        if (!expect(builder, C_INT)) {
            builder.error("number expected");
        }
        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!expect(builder, C_STRING)) {
            builder.error("string expected");
        }
        field.done(SE_ENUM_FIELD);
    }
}
