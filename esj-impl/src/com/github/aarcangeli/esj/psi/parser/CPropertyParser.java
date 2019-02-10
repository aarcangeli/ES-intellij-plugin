package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.parser.CExpressionParser.ExpressionContext;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraElement;
import static com.intellij.lang.PsiBuilder.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CPropertyParser implements CElementTypes {
    private static final TokenSet PROPERTY_TYPE = TokenSet.create(K_CTSTRING, K_CTSTRINGTRANS, K_CTFILENAME,
            K_CTFILENAMENODEP, K_BOOL, K_COLOR, K_FLOAT, K_INDEX, K_RANGE, K_CENTITYPOINTER, K_CMODELOBJECT,
            K_CMODELINSTANCE, K_CANIMOBJECT, K_CSOUNDOBJECT, K_CPLACEMENT3D, K_FLOATAABBOX3D, K_FLOATMATRIX3D,
            K_FLOATQUAT3D, K_ANGLE, K_ANGLE3D, K_FLOAT3D, K_FLOATPLANE3D, K_ILLUMINATIONTYPE, K_ANIMATION);

    private static final TokenSet PROPERTY_TYPE_WITH_IDENTIFIER = TokenSet.create(K_ENUM, K_FLAGS);

    private static final TokenSet PROPERTY_START = TokenSet.orSet(TokenSet.create(C_INT, IDENTIFIER, LBRACE), PROPERTY_TYPE,
            PROPERTY_TYPE_WITH_IDENTIFIER);

    private static final TokenSet NOT_GARBAGE = TokenSet.orSet(PROPERTY_START, CClassParser.CLASS_LABELS, TokenSet.create(COMMA));

    private static final TokenSet INTERNAL_PROPERTY_START = TokenSet.orSet(CTypeParser.TYPE_START, TokenSet.create(IDENTIFIER));
    private static final TokenSet NOT_GARBAGE_INTERNAL = TokenSet.orSet(INTERNAL_PROPERTY_START, CClassParser.CLASS_LABELS, TokenSet.create(SEMICOLON));

    static void parseProperties(PsiBuilder builder) {
        assert builder.getTokenType() == K_PROPERTIES;
        Marker mark = builder.mark();
        builder.advanceLexer();

        if (!PsiBuilderUtil.expect(builder, COLON)) {
            builder.error("':' expected");
        }

        reportExtraElement(builder, COMMA, "',' unexpected");

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE, "property expected", true)) continue;
            if (CClassParser.CLASS_LABELS.contains(builder.getTokenType())) break;
            if (expect(builder, COMMA)) continue;
            if (builder.getTokenType() == LBRACE) {
                parseInternalProperties(builder);
                break;
            }

            parseProperty(builder);

            if (PROPERTY_START.contains(builder.getTokenType())) {
                builder.error("',' expected");
            }

            if (expect(builder, COMMA)) {
                // report extra comma
                reportExtraElement(builder, COMMA, "repeated ','");
            }
        }

        mark.done(CElementTypes.SE_PROPERTIES_BLOCK);
    }

    private static void parseInternalProperties(PsiBuilder builder) {
        assert builder.getTokenType() == LBRACE;
        Marker internalProperty = builder.mark();
        builder.advanceLexer();

        reportExtraElement(builder, SEMICOLON, "';' unexpected");

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE_INTERNAL, "internal property expected", true)) continue;
            if (expect(builder, SEMICOLON)) continue;
            if (CClassParser.CLASS_LABELS.contains(builder.getTokenType())) break;

            parseInternalProperty(builder);

            if (INTERNAL_PROPERTY_START.contains(builder.getTokenType())) {
                builder.error("';' expected");
            }

            if (expect(builder, SEMICOLON)) {
                // report extra comma
                reportExtraElement(builder, SEMICOLON, "repeated ';'");
            }
        }

        if (!expect(builder, RBRACE)) {
            builder.error("'}' expected");
        }

        internalProperty.done(SE_INTERNAL_PROPERTY_BLOCK);
    }

    private static void parseInternalProperty(PsiBuilder builder) {
        Marker mark = builder.mark();
        CTypeParser.parseType(builder);
        if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        mark.done(SE_INTERNAL_PROPERTY);
    }

    private static void parseProperty(PsiBuilder builder) {
        assert PROPERTY_START.contains(builder.getTokenType());
        Marker property = builder.mark();
        if (!PsiBuilderUtil.expect(builder, C_INT)) {
            builder.error("number expected");
        }
        Marker type = builder.mark();
        if (!expect(builder, PROPERTY_TYPE)) {
            if (expect(builder, PROPERTY_TYPE_WITH_IDENTIFIER)) {
                if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
                    builder.error("identifier expected");
                }
            } else {
                builder.error("property type expected");
            }
        }
        type.done(CElementTypes.SE_TYPE);
        if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (builder.getTokenType() == C_STRING) {
            Marker wed = builder.mark();
            builder.advanceLexer();
            while (true) {
                if (builder.getTokenType() == C_CHAR) {
                    Marker whortcut = builder.mark();
                    builder.advanceLexer();
                    whortcut.done(CElementTypes.SE_PROPERTY_SHORTCUT);
                    continue;
                }
                if (builder.getTokenType() == K_COLOR) {
                    Marker whortcut = builder.mark();
                    builder.advanceLexer();
                    if (!PsiBuilderUtil.expect(builder, LPARENTH)) {
                        builder.error("'(' expected");
                    }
                    CExpressionParser.parseExpression(builder, ExpressionContext.COLOR);
                    if (!PsiBuilderUtil.expect(builder, RPARENTH)) {
                        builder.error("')' expected");
                    }
                    whortcut.done(CElementTypes.SE_PROPERTY_COLOR);
                    continue;
                }
                break;
            }
            wed.done(CElementTypes.SE_PROPERTY_WED);
        }
        if (expect(builder, EQ)) {
            Marker defaultValue = builder.mark();
            CExpressionParser.parseExpression(builder, ExpressionContext.DEFAULT_VALUE);
            defaultValue.done(CElementTypes.SE_PROPERTY_DEFAULT_VALUE);
        }
        if (expect(builder, K_FEATURES)) {
            if (!PsiBuilderUtil.expect(builder, LPARENTH)) {
                builder.error("'(' expected");
            }
            CExpressionParser.parseExpression(builder, ExpressionContext.FEATURES);
            if (!PsiBuilderUtil.expect(builder, RPARENTH)) {
                builder.error("')' expected");
            }
        }
        property.done(CElementTypes.SE_PROPERTY_FIELD);
    }

}
