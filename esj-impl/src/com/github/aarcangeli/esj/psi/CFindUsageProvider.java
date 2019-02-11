package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.lexer.CLexerAdapter;
import com.github.aarcangeli.esj.psi.composite.*;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.aarcangeli.esj.lexer.CTokenSets.*;

public class CFindUsageProvider implements FindUsagesProvider {
    @Override
    @Nullable
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new CLexerAdapter(), IDENTIFIER_SET, COMMENT_SET, LITERAL_SET);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof CAbstractNamedIdentifier;
    }

    @Override
    @Nullable
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    @NotNull
    public String getType(@NotNull PsiElement psiElement) {
        if (psiElement instanceof EsEventImpl) return "event";
        if (psiElement instanceof EsEventSpecificationImpl) return "field";
        if (psiElement instanceof EsEventField) return "event";
        if (psiElement instanceof EsEnumImpl) return "enum";
        if (psiElement instanceof EsEnumField) return "enum";
        if (psiElement instanceof EsProcedureImpl) return "procedure";
        if (psiElement instanceof EsFunctionImpl) return "function";
        if (psiElement instanceof EsPropertyImpl) return "property";
        if (psiElement instanceof EsClassImpl) return "class";
        if (psiElement instanceof EsComponentImpl) return "component";
        return "type";
    }

    @Override
    @NotNull
    public String getDescriptiveName(@NotNull PsiElement psiElement) {
        return getNodeText(psiElement, false);
    }

    @Override
    @NotNull
    public String getNodeText(@NotNull PsiElement psiElement, boolean useFullName) {
        if (psiElement instanceof CAbstractNamedIdentifier) {
            String name = ((CAbstractNamedIdentifier) psiElement).getName();
            if (name != null) {
                return name;
            }
        }
        return "name";
    }
}
