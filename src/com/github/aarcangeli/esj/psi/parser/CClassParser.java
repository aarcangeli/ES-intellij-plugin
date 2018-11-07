package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraComma;
import static com.intellij.lang.PsiBuilder.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CClassParser implements CElementTypes {
    private static final TokenSet CLASS_ATTRIBUTES = TokenSet.create(K_NAME, K_THUMBNAIL, K_FEATURES);
    private static final TokenSet CLASS_ATTRIBUTES_VALUES = TokenSet.create(C_STRING, C_INT, C_CHAR);
    public static final TokenSet CLASS_LABELS = TokenSet.create(K_PROPERTIES, K_COMPONENTS, K_FUNCTIONS, K_PROCEDURES);

    private static final TokenSet START_POINTS = TokenSet.orSet(CLASS_ATTRIBUTES, TokenSet.create(K_PROPERTIES));

    static void parseClass(PsiBuilder builder) {
        assert builder.getTokenType() == K_CLASS;
        Marker statement = builder.mark();
        builder.advanceLexer();
        expect(builder, K_EXPORT);

        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (expect(builder, COLON)) {
            if (!expect(builder, IDENTIFIER)) {
                builder.error("identifier expected");
            }
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
        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, START_POINTS, "statement expected")) continue;
            if (CLASS_ATTRIBUTES.contains(builder.getTokenType())) {
                parseAttribute(builder);
            } else if (builder.getTokenType() == K_PROPERTIES) {
                CPropertyParser.parseProperties(builder);
            } else {
                assert false;
            }
        }
    }

    private static void parseAttribute(PsiBuilder builder) {
        assert CLASS_ATTRIBUTES.contains(builder.getTokenType());
        Marker mark = builder.mark();
        builder.advanceLexer();

        while (!builder.eof()) {
            if (!expect(builder, CLASS_ATTRIBUTES_VALUES)) {
                builder.error("value expected");
                if (builder.getTokenType() != COMMA) break;
            }
            if (builder.getTokenType() != COMMA) break;
            if (!expect(builder, COMMA)) {
                builder.error("';' or ',' expected");
                break;
            }
            reportExtraComma(builder, "repeated ','");
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        mark.done(SE_CLASS_ATTRIBUTE);
    }
}
