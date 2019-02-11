package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsClass;
import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.github.aarcangeli.esj.utils.PsiUtils;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class CCompletionContributor extends CompletionContributor {
    CCompletionContributor() {
        PsiElementPattern.Capture<PsiElement> patternBasic = psiElement().andOr(
                psiElement().withAncestor(100, psiElement(CElementTypes.SE_CODE_BLOCK)),
                psiElement().withAncestor(100, psiElement(CElementTypes.SE_TYPE)),
                psiElement().withAncestor(100, psiElement(CElementTypes.SE_REFERENCE_EXPRESSION))
        );
        extend(CompletionType.BASIC, patternBasic, new NameCompletition(false));

        PsiElementPattern.Capture<PsiElement> patternSuperRef = psiElement().withAncestor(100,
                psiElement(CElementTypes.SE_CLASS_SUPER_REF)
        );
        extend(CompletionType.BASIC, patternSuperRef, new NameCompletition(true));
    }

    private class NameCompletition extends CompletionProvider<CompletionParameters> {
        private boolean onlyClasses;

        public NameCompletition(boolean onlyClasses) {
            this.onlyClasses = onlyClasses;
        }

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
            PsiUtils.processDeclarations(parameters.getOriginalPosition(), new BaseScopeProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
                    if (element instanceof CAbstractNamedIdentifier) {
                        if (onlyClasses && !(element instanceof EsClass)) return true;
                        result.consume(LookupElementBuilder.create((CAbstractNamedIdentifier) element));
                    }
                    return true;
                }
            });
        }
    }
}
