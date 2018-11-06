package com.github.aarcangeli.esj.psi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.github.aarcangeli.esj.psi.CElementType.SE_TYPE;
import static com.github.aarcangeli.esj.psi.CElementType.SE_TYPE_MODIFIERS;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CTypeParser {
    public static final TokenSet SET_TYPES = TokenSet.create(IDENTIFIER, K_CTSTRING, K_CTSTRINGTRANS, K_CTFILENAME,
            K_CTFILENAMENODEP, K_BOOL, K_COLOR, K_FLOAT, K_INDEX, K_RANGE, K_CENTITYPOINTER, K_CMODELOBJECT,
            K_CMODELINSTANCE, K_CANIMOBJECT, K_CSOUNDOBJECT, K_CPLACEMENT3D, K_FLOATAABBOX3D, K_FLOATMATRIX3D,
            K_FLOATQUAT3D, K_ANGLE, K_ANIMATION, K_ILLUMINATIONTYPE, K_ANGLE3D, K_FLOAT3D, K_FLOATPLANE3D,
            K_VOID);

    public static final TokenSet SET_MODIFIERS = TokenSet.create(K_ENUM, K_CONST, K_INLINE, K_STATIC, K_CLASS);

    public static final TokenSet TYPE_START = TokenSet.orSet(SET_MODIFIERS, SET_TYPES);

    public static final TokenSet SET_APPENDIX = TokenSet.create(ASTERISK, AND);

    public static void parseType(PsiBuilder builder) {
        PsiBuilder.Marker type = builder.mark();

        PsiBuilder.Marker modifiersMark = builder.mark();
        while (SET_MODIFIERS.contains(builder.getTokenType())) {
            builder.advanceLexer();
        }
        modifiersMark.done(SE_TYPE_MODIFIERS);

        if (!expect(builder, SET_TYPES)) {
            builder.error("type expected");
        }

        if (builder.getTokenType() == LT) {
            builder.advanceLexer();
            parseType(builder);
            if (!expect(builder, GT)) {
                builder.error("'>' expected");
            }
        }

        while (SET_APPENDIX.contains(builder.getTokenType())) {
            builder.advanceLexer();
        }

        type.done(SE_TYPE);
    }

}
