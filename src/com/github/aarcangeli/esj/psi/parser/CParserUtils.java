package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.parser.CExpressionParser.ExpressionContext;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.github.aarcangeli.esj.psi.CElementTypes.SE_CPP_BLOCK;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CParserUtils {
    static boolean eatGarbage(PsiBuilder builder, TokenSet notGarbage, String message) {
        return eatGarbage(builder, notGarbage, message, true);
    }

    static boolean eatGarbage(PsiBuilder builder, TokenSet notGarbage, String message, boolean closeWithBrace) {
        int braceCount = 0;
        IElementType type = builder.getTokenType();
        if (!builder.eof() && !notGarbage.contains(type)) {
            PsiBuilder.Marker err = builder.mark();
            while (!builder.eof() && (braceCount != 0 || !notGarbage.contains(type))) {
                if (type == LBRACE) braceCount++;
                if (type == RBRACE) {
                    if (closeWithBrace && braceCount == 0) break;
                    braceCount--;
                }
                builder.advanceLexer();
                type = builder.getTokenType();
            }
            err.error(message);
            return true;
        }
        return false;
    }

    static void optionalBlock(PsiBuilder builder) {
        while (builder.getTokenType() == CPP_BLOCK_BEGIN) {
            processBlock(builder);
        }
    }

    /**
     * This method report an error if a C++ block is putted in a certain zone
     * Should be called often
     */
    static void reportCppBlocks(PsiBuilder builder) {
        while (builder.getTokenType() == CPP_BLOCK_BEGIN) {
            PsiBuilder.Marker mark = builder.mark();
            processBlock(builder);
            mark.error("C++ block cannot be placed here");
        }
    }

    public static void processBlock(PsiBuilder builder) {
        assert builder.getTokenType() == CPP_BLOCK_BEGIN;
        PsiBuilder.Marker start = builder.mark();
        builder.advanceLexer();
        expect(builder, CPP_BLOCK);
        if (!expect(builder, CPP_BLOCK_END)) {
            builder.error("'%}' expected");
        }
        start.done(SE_CPP_BLOCK);
    }

    static void reportExtraElement(PsiBuilder builder, IElementType element, String message) {
        if (builder.getTokenType() == element) {
            PsiBuilder.Marker err = builder.mark();
            while (builder.getTokenType() == element) {
                builder.advanceLexer();
            }
            err.error(message);
        }
    }

    static void parseEventSpecification(PsiBuilder builder) {
        if (!expect(builder, LPARENTH)) {
            builder.error("'(' expected");
        } else {
            while (builder.getTokenType() == IDENTIFIER) {
                builder.advanceLexer();
            }
            if (!expect(builder, RPARENTH)) {
                builder.error("')' expected");
            }
        }
    }

    static boolean parseExpressionInParenth(PsiBuilder builder) {
        return parseExpressionInParenth(builder, false);
    }

    static boolean parseExpressionInParenth(PsiBuilder builder, boolean optional) {
        if (!expect(builder, LPARENTH)) {
            builder.error("'(' expected");
        }

        PsiBuilder.Marker beforeExpr = builder.mark();
        if (CExpressionParser.parseExpression(builder, ExpressionContext.STANDARD) == null) {
            if (!optional) {
                builder.error("expression expected");
            }
        } else {
            if (builder.getTokenType() == SEMICOLON || builder.getTokenType() == LBRACE) {
                beforeExpr.rollbackTo();
                builder.error("expression expected");
                return false;
            }
        }

        beforeExpr.drop();
        if (!expect(builder, RPARENTH)) {
            builder.error("')' expected");
        }

        return true;
    }

    static void parseJumpTarget(PsiBuilder builder) {
        if (!expect(builder, IDENTIFIER)) {
            builder.error("Identifier expected");
        }

        if (expect(builder, COLON)) {
            if (!expect(builder, COLON)) {
                builder.error("':' expected");
            }
            if (!expect(builder, IDENTIFIER)) {
                builder.error("Identifier expected");
            }
        }
    }
}
