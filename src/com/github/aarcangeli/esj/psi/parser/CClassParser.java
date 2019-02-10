package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraElement;
import static com.intellij.lang.PsiBuilder.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CClassParser implements CElementTypes {
    private static final TokenSet CLASS_ATTRIBUTES = TokenSet.create(K_NAME, K_THUMBNAIL, K_FEATURES);
    private static final TokenSet CLASS_ATTRIBUTES_VALUES = TokenSet.create(C_STRING, C_INT, C_CHAR);
    public static final TokenSet CLASS_LABELS = TokenSet.create(K_PROPERTIES, K_COMPONENTS, K_FUNCTIONS, K_PROCEDURES);

    private static final TokenSet START_POINTS = TokenSet.orSet(CLASS_ATTRIBUTES, CLASS_LABELS);

    static void parseClass(PsiBuilder builder) {
        assert builder.getTokenType() == K_CLASS;
        Marker statement = builder.mark();
        builder.advanceLexer();
        expect(builder, K_EXPORT);

        if (!expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (expect(builder, COLON)) {
            Marker mark = builder.mark();
            if (!expect(builder, IDENTIFIER)) {
                builder.error("identifier expected");
            }
            mark.done(SE_CLASS_SUPER_REF);
        }
        if (!expect(builder, LBRACE)) {
            builder.error("'{' expected");
            statement.done(SE_CLASS_STATEMENT);
            return;
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
            if (eatGarbage(builder, START_POINTS, "statement expected", true)) continue;
            IElementType type = builder.getTokenType();
            if (CLASS_ATTRIBUTES.contains(type)) {
                parseAttribute(builder);
            } else if (type == K_PROPERTIES) {
                CPropertyParser.parseProperties(builder);
            } else if (type == K_COMPONENTS) {
                CComponentParser.parseComponentBlock(builder);
            } else if (type == K_FUNCTIONS) {
                CFunctionParser.parseFunctionBlock(builder);
            } else if (type == K_PROCEDURES) {
                CProcedureParser.parseProceduresBlock(builder);
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
            if (!expect(builder, COMMA)) break;
            reportExtraElement(builder, COMMA, "repeated ','");
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        mark.done(SE_CLASS_ATTRIBUTE);
    }
}
