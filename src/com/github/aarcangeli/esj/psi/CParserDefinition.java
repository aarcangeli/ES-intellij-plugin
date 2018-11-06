package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.lexer.CLexerAdapter;
import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.parser.CParser;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class CParserDefinition implements ParserDefinition {
    private static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    private static final TokenSet COMMENTS = TokenSet.create(CTokens.COMMENT);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new CLexerAdapter();
    }

    @Override
    public PsiParser createParser(Project project) {
        return CParser.INSTANCE;
    }

    @Override
    public IFileElementType getFileNodeType() {
        return CElementTypes.SE_FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        IElementType type = node.getElementType();
        throw new IllegalStateException("Incorrect node for ESParserDefinition: " + node + " (" + type + ")");
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new CFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
