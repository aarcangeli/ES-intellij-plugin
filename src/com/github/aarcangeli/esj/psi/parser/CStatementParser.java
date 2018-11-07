package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.parser.CExpressionParser.ExpressionContext;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.intellij.lang.PsiBuilder.Marker;
import static com.intellij.lang.PsiBuilderUtil.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CStatementParser implements CElementTypes {
    static boolean parseRequiredCodeBlock(PsiBuilder builder) {
        if (builder.getTokenType() != LBRACE) {
            builder.error("'{' expected");
            return false;
        }
        parseCodeBlock(builder);
        return true;
    }

    static void parseCodeBlock(PsiBuilder builder) {
        assert builder.getTokenType() == LBRACE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        parseStatements(builder);

        if (!expect(builder, RBRACE)) {
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
        if (tokenType == K_SWITCH) return parseSwitchStatement(builder);
        if (tokenType == K_CASE) return parseCaseStatement(builder);
        if (tokenType == K_WHILE) return parseWhileStatement(builder);
        if (tokenType == K_DO) return parseDoWhileStatement(builder);
        if (tokenType == K_FOR) return parseForStatement(builder);
        if (tokenType == K_RETURN) return parseReturnStatement(builder);
        if (tokenType == SEMICOLON) return parseEmptyStatement(builder);
        Marker expression = CExpressionParser.parseExpression(builder, ExpressionContext.STATEMENT);
        if (expression != null) {
            if (!expect(builder, SEMICOLON)) {
                builder.error("';' expected");
            }
            Marker statement = expression.precede();
            statement.done(SE_EXPRESSION_STATEMENT);
            return statement;
        }
        return null;
    }

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
            if (parseStatement(builder) == null) {
                builder.error("Statement expected");
            }
        }

        statement.done(SE_IF_STATEMENT);
        return statement;
    }

    private static Marker parseSwitchStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_SWITCH;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder)) {
            statement.done(SE_SWITCH_STATEMENT);
            return statement;
        }

        parseRequiredCodeBlock(builder);

        statement.done(SE_SWITCH_STATEMENT);
        return statement;
    }

    private static Marker parseCaseStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_CASE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        CExpressionParser.parseExpression(builder, ExpressionContext.CASE);

        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }

        statement.done(SE_CASE_STATEMENT);
        return statement;
    }

    private static Marker parseWhileStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_WHILE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder)) {
            statement.done(SE_WHILE_STATEMENT);
            return statement;
        }

        parseRequiredCodeBlock(builder);

        statement.done(SE_WHILE_STATEMENT);
        return statement;
    }

    private static Marker parseDoWhileStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_DO;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!parseRequiredCodeBlock(builder)) {
            statement.done(SE_DO_WHILE_STATEMENT);
            return statement;
        }

        CParserUtils.parseExpressionInParenth(builder);

        statement.done(SE_DO_WHILE_STATEMENT);
        return statement;
    }

    private static Marker parseForStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_FOR;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!expect(builder, LPARENTH)) {
            builder.error("'(' expected");
        }

        if (parseStatement(builder) == null) {
            builder.error("Statement expected");
        } else {
            CExpressionParser.parseExpression(builder, ExpressionContext.STANDARD);
            if (expect(builder, SEMICOLON)) {
                CExpressionParser.parseExpression(builder, ExpressionContext.STANDARD);
            }
        }

        if (!expect(builder, RPARENTH)) {
            builder.error("')' expected");
        }

        parseRequiredCodeBlock(builder);

        statement.done(SE_FOR_STATEMENT);
        return statement;
    }

    private static Marker parseReturnStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_RETURN;
        Marker statement = builder.mark();
        builder.advanceLexer();
        CExpressionParser.parseExpression(builder, ExpressionContext.STANDARD);
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }
        statement.done(SE_RETURN_STATEMENT);
        return statement;
    }

    private static Marker parseEmptyStatement(PsiBuilder builder) {
        assert builder.getTokenType() == SEMICOLON;
        Marker statement = builder.mark();
        builder.advanceLexer();
        statement.done(SE_EMPTY_STATEMENT);
        return statement;
    }
}
