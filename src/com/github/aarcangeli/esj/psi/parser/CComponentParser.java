package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraElement;
import static com.intellij.lang.PsiBuilder.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CComponentParser implements CElementTypes {
    private static final TokenSet COMPONENT_TYPE = TokenSet.create(K_MODEL, K_TEXTURE, K_SOUND, K_CLASS);

    private static final TokenSet COMPONENT_START = TokenSet.orSet(COMPONENT_TYPE, TokenSet.create(C_INT, IDENTIFIER));

    private static final TokenSet NOT_GARBAGE = TokenSet.orSet(COMPONENT_START, CClassParser.CLASS_LABELS, TokenSet.create(COMMA));

    public static void parseComponentBlock(PsiBuilder builder) {
        assert builder.getTokenType() == K_COMPONENTS;
        Marker block = builder.mark();
        builder.advanceLexer();

        if (!PsiBuilderUtil.expect(builder, COLON)) {
            builder.error("':' expected");
        }

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE, "component expected")) continue;
            if (CClassParser.CLASS_LABELS.contains(builder.getTokenType())) break;
            if (expect(builder, COMMA)) continue;

            parseComponent(builder);

            if (COMPONENT_START.contains(builder.getTokenType())) {
                builder.error("',' expected");
            }

            if (expect(builder, COMMA)) {
                // report extra comma
                reportExtraElement(builder, COMMA, "repeated ','");
            }
        }

        block.done(SE_COMPONENTS_BLOCK);
    }

    private static void parseComponent(PsiBuilder builder) {
        assert COMPONENT_START.contains(builder.getTokenType());
        Marker component = builder.mark();

        if (!PsiBuilderUtil.expect(builder, C_INT)) {
            builder.error("number expected");
        }
        if (!expect(builder, COMPONENT_TYPE)) {
            builder.error("component type expected");
        }
        if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }
        if (!PsiBuilderUtil.expect(builder, C_STRING)) {
            builder.error("filename expected");
        }

        component.done(CElementTypes.SE_COMPONENT_FIELD);
    }
}
