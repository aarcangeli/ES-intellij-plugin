package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.PsiBuilder.Marker;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CStatementParser implements CElementTypes {
    static void parseRequiredCodeBlock(PsiBuilder builder) {
        if (builder.getTokenType() == LBRACE) {
            parseCodeBlock(builder);
        } else {
            builder.error("'{' expected");
        }
    }


    static void parseCodeBlock(PsiBuilder builder) {
        assert builder.getTokenType() == CTokens.LBRACE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        parseStatements(builder);

        if (!PsiBuilderUtil.expect(builder, CTokens.RBRACE)) {
            builder.error("'}' expected");
        }

        statement.done(SE_CODE_BLOCK);
    }

    private static void parseStatements(PsiBuilder builder) {
        while (!builder.eof()) {
            Marker marker = parseStatement(builder);
            if (marker != null) continue;

            if (builder.getTokenType() == RBRACE) {
                break;
            }

            Marker error = builder.mark();
            builder.advanceLexer();
            error.error("unexpected token");
        }
    }

    private static Marker parseStatement(PsiBuilder builder) {
        IElementType tokenType = builder.getTokenType();
        if (tokenType == LBRACE) return parseBlockStatement(builder);
        if (tokenType == K_IF) return parseIfStatement(builder);
        return null;
    }

    @NotNull
    private static Marker parseBlockStatement(PsiBuilder builder) {
        Marker statement = builder.mark();
        parseCodeBlock(builder);
        statement.done(SE_BLOCK_STATEMENT);
        return statement;
    }

    private static Marker parseIfStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_IF;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder)) {
            statement.done(SE_IF_STATEMENT);
            return statement;
        }

        parseRequiredCodeBlock(builder);

        if (expect(builder, K_ELSE)) {
            parseRequiredCodeBlock(builder);
        }

        statement.done(SE_IF_STATEMENT);
        return statement;
    }
}
