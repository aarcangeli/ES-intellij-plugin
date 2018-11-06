package com.github.aarcangeli.esj.psi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.github.aarcangeli.esj.psi.CElementType.SE_ENUM_FIELD;
import static com.github.aarcangeli.esj.psi.CElementType.SE_ENUM_STATEMENT;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraComma;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CEnumParser {
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

        reportExtraComma(builder, "enum cannot start with ','");

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            eatGarbage(builder, NOT_GARBAGE, "field expected");
            if (expect(builder, COMMA)) continue;

            parseEnummField(builder);

            if (ENUM_FIELD_START.contains(builder.getTokenType())) {
                builder.error("',' expected");
            }

            if (expect(builder, COMMA)) {
                // report extra comma
                reportExtraComma(builder, "repeated ','");
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
