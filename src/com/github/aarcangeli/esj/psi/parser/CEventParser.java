package com.github.aarcangeli.esj.psi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.github.aarcangeli.esj.psi.CElementType.SE_EVENT_FIELD;
import static com.github.aarcangeli.esj.psi.CElementType.SE_EVENT_STATEMENT;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraComma;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CEventParser {
    public static final TokenSet NOT_GARBAGE = TokenSet.orSet(CTypeParser.TYPE_START, TokenSet.create(COMMA));
    public static final TokenSet EVENT_FIELD_START = CTypeParser.TYPE_START;


    static void parseEventDeclaration(PsiBuilder builder) {
        assert builder.getTokenType() == K_EVENT;
        PsiBuilder.Marker statement = builder.mark();
        builder.advanceLexer();

        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!expect(builder, LBRACE)) {
            builder.error("'{' expected");
        }

        reportExtraComma(builder, "event cannot start with ','");

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            eatGarbage(builder, NOT_GARBAGE, "event field expected");
            if (expect(builder, COMMA)) continue;

            parseEventField(builder);

            if (EVENT_FIELD_START.contains(builder.getTokenType())) {
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

        statement.done(SE_EVENT_STATEMENT);
    }

    private static void parseEventField(PsiBuilder builder) {
        PsiBuilder.Marker field = builder.mark();
        CTypeParser.parseType(builder);
        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        field.done(SE_EVENT_FIELD);
    }
}
