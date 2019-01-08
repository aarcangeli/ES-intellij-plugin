package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.github.aarcangeli.esj.psi.parser.CParserUtils.reportExtraElement;
import static com.intellij.lang.PsiBuilder.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CFunctionParser implements CElementTypes {

    private static final TokenSet MODIFIERS = TokenSet.create(K_EXPORT, K_VIRTUAL);
    private static final TokenSet FUNCTION_START = TokenSet.orSet(MODIFIERS, CTypeParser.TYPE_START);

    private static final TokenSet PARAMETER_STOPPERS = TokenSet.create(RPARENTH, LBRACE);

    private static final TokenSet NOT_GARBAGE = TokenSet.orSet(FUNCTION_START, CClassParser.CLASS_LABELS);


    public static void parseFunctionBlock(PsiBuilder builder) {
        assert builder.getTokenType() == K_FUNCTIONS;
        Marker block = builder.mark();
        builder.advanceLexer();

        if (!PsiBuilderUtil.expect(builder, COLON)) {
            builder.error("':' expected");
        }

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE, "function expected", true)) continue;
            if (CClassParser.CLASS_LABELS.contains(builder.getTokenType())) break;

            parseFunction(builder);
        }

        block.done(SE_FUNCTIONS_BLOCK);
    }

    private static void parseFunction(PsiBuilder builder) {
        assert FUNCTION_START.contains(builder.getTokenType());
        Marker function = builder.mark();

        Marker modifier = builder.mark();
        while (MODIFIERS.contains(builder.getTokenType())) {
            builder.advanceLexer();
        }
        modifier.done(SE_FUNCTION_MODIFIERS);

        CTypeParser.parseType(builder);

        expect(builder, TILDE);

        if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
            builder.error("identifier expected");
        }

        if (!PsiBuilderUtil.expect(builder, LPARENTH)) {
            builder.error("'(' expected");
        }

        parseParameterList(builder);

        if (!PsiBuilderUtil.expect(builder, RPARENTH)) {
            builder.error("')' expected");
        }

        expect(builder, K_CONST);

        CStatementParser.parseRequiredCodeBlock(builder);
        expect(builder, SEMICOLON);

        function.done(SE_FUNCTION);
    }

    private static void parseParameterList(PsiBuilder builder) {
        Marker parameters = builder.mark();

        if (expect(builder, K_VOID)) {
            parameters.done(SE_PARAMETER_LIST);
            return;
        }

        if (PARAMETER_STOPPERS.contains(builder.getTokenType())) {
            parameters.done(SE_PARAMETER_LIST);
            return;
        }

        while (!builder.eof()) {
            Marker par = builder.mark();
            CTypeParser.parseType(builder);
            if (!PsiBuilderUtil.expect(builder, IDENTIFIER)) {
                builder.error("identifier expected");
            }
            par.done(SE_PARAMETER);

            if (PARAMETER_STOPPERS.contains(builder.getTokenType())) break;
            if (expect(builder, COMMA)) continue;

            Marker err = builder.mark();
            while (!builder.eof() && !PARAMETER_STOPPERS.contains(builder.getTokenType()) && builder.getTokenType() != COMMA) {
                builder.advanceLexer();
            }
            err.error("',' or ')' expected");
        }

        parameters.done(SE_PARAMETER_LIST);
    }
}
