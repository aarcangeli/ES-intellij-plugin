package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.lang.PsiBuilderUtil.expect;

public class CExpressionParser implements CElementTypes {
    private static final TokenSet ALL_EXPRESSIONS = TokenSet.create(C_INT, C_CHAR, C_STRING, IDENTIFIER,
            LBRACE, RBRACE, EQ, PLUS, MINUS, LT, GT, EXCL, OR, AND, ASTERISK, DIV, PERC, XOR, LBRACKET, RBRACKET, COLON,
            DOT, QUEST, TILDE);

    public enum ExpressionContext {
        // property 1 CTString nome string COLOR(<color expression>) = <default value> features(<features>)
        COLOR,
        DEFAULT_VALUE,
        FEATURES,
        // other
        STANDARD,
    }

    public static void parseExpression(PsiBuilder builder, ExpressionContext context) {
        // todo: develop this
        int braceCount = 0;
        while (!builder.eof()) {
            IElementType type = builder.getTokenType();
            if (type == LBRACE) braceCount++;
            if (type == RBRACE) {
                if (braceCount == 0) break;
                braceCount--;
            }
            if (CTypeParser.TYPE_START.contains(type)) {
                CTypeParser.parseType(builder);
                continue;
            }
            if (type == LPARENTH) {
                builder.advanceLexer();
                parseExpression(builder, ExpressionContext.STANDARD);
                if (!expect(builder, RPARENTH)) {
                    builder.error("')' expected");
                }
                continue;
            }
            if (type == COMMA && context == ExpressionContext.STANDARD) {
                builder.advanceLexer();
                continue;
            }
            if (braceCount == 0 && !ALL_EXPRESSIONS.contains(type)) break;
            builder.advanceLexer();
        }
    }
}
