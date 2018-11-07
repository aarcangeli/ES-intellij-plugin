package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.TokenSet;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.eatGarbage;
import static com.intellij.lang.PsiBuilderUtil.*;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CProcedureParser implements CElementTypes {
    private static final TokenSet PROCEDURE_START = TokenSet.create(IDENTIFIER);
    private static final TokenSet NOT_GARBAGE = TokenSet.orSet(PROCEDURE_START, CClassParser.CLASS_LABELS);

    public static void parseProceduresBlock(PsiBuilder builder) {
        assert builder.getTokenType() == K_PROCEDURES;
        PsiBuilder.Marker block = builder.mark();
        builder.advanceLexer();
        if (!expect(builder, COLON)) {
            builder.error("':' expected");
        }

        while (!builder.eof() && builder.getTokenType() != RBRACE) {
            if (eatGarbage(builder, NOT_GARBAGE, "procedure expected")) continue;
            if (CClassParser.CLASS_LABELS.contains(builder.getTokenType())) break;

            parseProcedure(builder);
        }

        block.done(SE_PROCEDURES_BLOCK);
    }

    private static void parseProcedure(PsiBuilder builder) {
        assert builder.getTokenType() == IDENTIFIER;
        PsiBuilder.Marker procedure = builder.mark();
        builder.advanceLexer();

        CParserUtils.parseEventSpecification(builder);

        if (builder.getTokenType() == COLON) {
            PsiBuilder.Marker override = builder.mark();
            builder.advanceLexer();

            if (!expect(builder, IDENTIFIER)) {
                builder.error("identifier expected");
            }
            if (!expect(builder, COLON)) {
                builder.error("':' expected");
            }
            if (!expect(builder, COLON)) {
                builder.error("':' expected");
            }
            if (!expect(builder, IDENTIFIER)) {
                builder.error("identifier expected");
            }

            override.done(SE_PROCEDURE_OVERRIDE);
        }

        CStatementParser.parseRequiredCodeBlock(builder);

        expect(builder, SEMICOLON);

        procedure.done(SE_PROCEDURE);
    }

}
