package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.parser.CExpressionParser.ExpressionContext;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

import static com.intellij.lang.PsiBuilder.Marker;
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
            IElementType tokenType = builder.getTokenType();
            builder.advanceLexer();
            if (tokenType == K_ELSE) {
                error.error("'else' without 'if'");
            } else if (tokenType == K_CATCH) {
                error.error("'catch' without 'try'");
            } else {
                error.error("unexpected token");
            }
        }
    }

    private static Marker parseStatement(PsiBuilder builder) {
        IElementType tokenType = builder.getTokenType();
        if (tokenType == LBRACE) return parseBlockStatement(builder);
        if (tokenType == K_IF) return parseIfStatement(builder);
        if (tokenType == K_SWITCH) return parseParenthStatement(builder, SE_SWITCH_STATEMENT);
        if (tokenType == K_CASE) return parseCaseStatement(builder);
        if (tokenType == K_DEFAULT) return parseDefaultStatement(builder);
        if (tokenType == K_BREAK) return parseSingleTokenStatement(builder, SE_BREAK_STATEMENT);
        if (tokenType == K_WHILE) return parseParenthStatement(builder, SE_WHILE_STATEMENT);
        if (tokenType == K_DO) return parseDoWhileStatement(builder);
        if (tokenType == K_FOR) return parseForStatement(builder);
        if (tokenType == K_RETURN) return parseReturnStatement(builder);
        if (tokenType == K_WAIT) return parseWaitStatement(builder);
        if (tokenType == K_AUTOWAIT) return parseAutoWaitStatement(builder);
        if (tokenType == K_WAITEVENT) return parseWaitEventStatement(builder);
        if (tokenType == K_ON) return parseOnStatement(builder, SE_ON_STATEMENT);
        if (tokenType == K_OTHERWISE) return parseOnStatement(builder, SE_OTHERWISE_STATEMENT);
        if (tokenType == K_JUMP) return parseJumpStatement(builder, SE_JUMP_STATEMENT);
        if (tokenType == K_CALL) return parseJumpStatement(builder, SE_CALL_STATEMENT);
        if (tokenType == K_AUTOCALL) return parseAutoCallStatement(builder);
        if (tokenType == K_STOP) return parseSingleTokenStatement(builder, SE_STOP_STATEMENT);
        if (tokenType == K_RESUME) return parseSingleTokenStatement(builder, SE_RESUME_STATEMENT);
        if (tokenType == K_PASS) return parseSingleTokenStatement(builder, SE_PASS_STATEMENT);
        if (tokenType == K_TRY) return parseTryStatement(builder);
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

    private static Marker parseCaseStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_CASE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (CExpressionParser.parseExpression(builder, ExpressionContext.CASE) == null) {
            builder.error("Expression expected");
        }

        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }

        statement.done(SE_CASE_STATEMENT);
        return statement;
    }

    private static Marker parseDefaultStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_DEFAULT;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }

        statement.done(SE_DEFAULT_STATEMENT);
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

        if (!expect(builder, K_WHILE)) {
            builder.error("'while' expected");
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

    private static Marker parseWaitStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_WAIT;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder, true)) {
            statement.done(SE_WAIT_STATEMENT);
            return statement;
        }

        parseRequiredCodeBlock(builder);

        statement.done(SE_WAIT_STATEMENT);
        return statement;
    }

    private static Marker parseAutoWaitStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_AUTOWAIT;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder, true)) {
            statement.done(SE_AUTOWAIT_STATEMENT);
            return statement;
        }

        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(SE_AUTOWAIT_STATEMENT);
        return statement;
    }

    private static Marker parseWaitEventStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_WAITEVENT;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder, true)) {
            statement.done(SE_WAITEVENT_STATEMENT);
            return statement;
        }

        if (!expect(builder, IDENTIFIER)) {
            builder.error("Identifier expected");
        }

        expect(builder, IDENTIFIER);

        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(SE_WAITEVENT_STATEMENT);
        return statement;
    }

    private static Marker parseOnStatement(PsiBuilder builder, IElementType elementType) {
        assert builder.getTokenType() == K_ON || builder.getTokenType() == K_OTHERWISE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        CParserUtils.parseEventSpecification(builder);

        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }

        parseRequiredCodeBlock(builder);

        expect(builder, SEMICOLON);

        statement.done(elementType);
        return statement;
    }

    private static Marker parseJumpStatement(PsiBuilder builder, IElementType elementType) {
        assert builder.getTokenType() == K_JUMP || builder.getTokenType() == K_CALL;
        Marker statement = builder.mark();
        builder.advanceLexer();

        CParserUtils.parseJumpTarget(builder);

        if (!CParserUtils.parseExpressionInParenth(builder, true)) {
            statement.done(elementType);
            return statement;
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(elementType);
        return statement;
    }

    private static Marker parseAutoCallStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_AUTOCALL;
        Marker statement = builder.mark();
        builder.advanceLexer();

        CParserUtils.parseJumpTarget(builder);

        if (!CParserUtils.parseExpressionInParenth(builder, true)) {
            statement.done(SE_AUTOCALL_STATEMENT);
            return statement;
        }
        if (!expect(builder, IDENTIFIER)) {
            builder.error("Identifier expected");
        }
        expect(builder, IDENTIFIER);
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(SE_AUTOCALL_STATEMENT);
        return statement;
    }

    private static Marker parseSingleTokenStatement(PsiBuilder builder, IElementType elementType) {
        Marker statement = builder.mark();
        builder.advanceLexer();
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }
        statement.done(elementType);
        return statement;
    }

    private static Marker parseTryStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_TRY;
        Marker statement = builder.mark();
        builder.advanceLexer();

        parseRequiredCodeBlock(builder);

        while (builder.getTokenType() == K_CATCH) {
            builder.advanceLexer();

            if (!CParserUtils.parseExpressionInParenth(builder)) {
                statement.done(SE_TRY_STATEMENT);
                return statement;
            }

            parseRequiredCodeBlock(builder);
        }

        statement.done(SE_TRY_STATEMENT);
        return statement;
    }

    private static Marker parseParenthStatement(PsiBuilder builder, IElementType tokenType) {
        assert builder.getTokenType() == K_SWITCH || builder.getTokenType() == K_WHILE;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!CParserUtils.parseExpressionInParenth(builder)) {
            statement.done(tokenType);
            return statement;
        }

        parseRequiredCodeBlock(builder);

        statement.done(tokenType);
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
