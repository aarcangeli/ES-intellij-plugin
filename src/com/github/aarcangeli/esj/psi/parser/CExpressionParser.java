package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.lang.PsiBuilderUtil.expect;

public class CExpressionParser implements CElementTypes {
    private static final TokenSet ALL_EXPRESSIONS_TOKEN = TokenSet.create(C_INT, C_CHAR, C_STRING, IDENTIFIER,
            LBRACE, RBRACE, EQ, PLUS, MINUS, LT, GT, EXCL, OR, AND, ASTERISK, DIV, PERC, XOR, LBRACKET, RBRACKET, COLON,
            DOT, QUEST, TILDE);
    private static final TokenSet ALL_EXPRESSIONS = TokenSet.orSet(CTypeParser.TYPE_START, ALL_EXPRESSIONS_TOKEN);

    public enum ExpressionContext {
        // property 1 CTString nome string COLOR(<color expression>) = <default value> features(<features>)
        COLOR,
        DEFAULT_VALUE,
        FEATURES,
        // other
        CASE,
        STATEMENT,
        STANDARD,
    }

    public static PsiBuilder.Marker parseExpression(PsiBuilder builder, ExpressionContext context) {
        boolean blockAtComma = context == ExpressionContext.STANDARD || context == ExpressionContext.STATEMENT;
        // todo: develop this
        int braceCount = 0;
        PsiBuilder.Marker mark = null;
        while (!builder.eof()) {
            IElementType type = builder.getTokenType();
            if (type == LBRACE) braceCount++;
            if (type == RBRACE) {
                if (braceCount == 0) break;
                braceCount--;
            }
            if (type == LPARENTH) {
                if (mark == null) mark = builder.mark();
                builder.advanceLexer();
                parseExpression(builder, ExpressionContext.STANDARD);
                if (!expect(builder, RPARENTH)) {
                    builder.error("')' expected");
                }
                continue;
            }
            if (blockAtComma && type == COMMA) {
                if (mark == null) mark = builder.mark();
                builder.advanceLexer();
                continue;
            }
            if (braceCount == 0 && !ALL_EXPRESSIONS.contains(type)) break;
            if (mark == null) mark = builder.mark();
            builder.advanceLexer();
        }
        if (mark != null) {
            mark.done(SE_EXPRESSION);
        }
        return mark;
    }
}
