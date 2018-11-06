package com.github.aarcangeli.esj.psi.parser;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import static com.github.aarcangeli.esj.psi.parser.CParserUtils.*;
import static com.intellij.lang.PsiBuilder.Marker;
import static com.intellij.lang.PsiBuilderUtil.expect;

public class CParser implements PsiParser, CElementTypes {
    public static final CParser INSTANCE = new CParser();

    public static final TokenSet SET_STATEMENT_START = TokenSet.create(CPP_BLOCK_BEGIN, K_USES, K_ENUM, K_EVENT, K_CLASS);

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        // TODO: Remove debug mode
        builder.setDebugMode(true);

        Marker rootMark = builder.mark();
        parseContent(builder);
        rootMark.done(root);

        return builder.getTreeBuilt();
    }

    private void parseContent(@NotNull PsiBuilder builder) {
        // check empty file
        if (builder.eof()) return;

        // Class ID
        Marker mark = builder.mark();
        if (!expect(builder, C_INT)) builder.error("Expected class ID");
        mark.done(SE_CLASS_ID);

        optionalBlock(builder);

        while (builder.getTokenType() == K_USES) {
            parseUsesStatement(builder);
        }

        while (!builder.eof()) {
            eatGarbage(builder, SET_STATEMENT_START, "'enum', 'event' or 'enum' expected");
            IElementType type = builder.getTokenType();

            if (type == CPP_BLOCK_BEGIN) {
                processBlock(builder);
            } else if (type == K_ENUM) {
                CEnumParser.parseEnumDeclaration(builder);
            } else if (type == K_EVENT) {
                CEventParser.parseEventDeclaration(builder);
            } else if (type == K_CLASS) {
                CClassParser.parseClass(builder);
            }
        }
    }

    private void parseUsesStatement(PsiBuilder builder) {
        assert builder.getTokenType() == K_USES;
        Marker statement = builder.mark();
        builder.advanceLexer();

        if (!expect(builder, C_STRING)) {
            builder.error("string expected");
        }
        if (!expect(builder, SEMICOLON)) {
            builder.error("';' expected");
        }

        statement.done(SE_USE_STATEMENT);
    }

}
