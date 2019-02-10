package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class CCompletitionContributor extends CompletionContributor {
    CCompletitionContributor() {
        PsiElementPattern.Capture<PsiElement> pattern = psiElement().andOr(
                psiElement().withAncestor(100, psiElement(CElementTypes.SE_CODE_BLOCK)),
                psiElement().withAncestor(100, psiElement(CElementTypes.SE_TYPE))
        );
        extend(CompletionType.BASIC, pattern, new NameCompletition());
    }

    private class NameCompletition extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
            PsiElement element = parameters.getOriginalPosition();
            if (element == null) return;

            PsiScopeProcessor processor = new BaseScopeProcessor() {
                @Override
                public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
                    if (element instanceof CAbstractNamedIdentifier) {
                        result.consume(LookupElementBuilder.create((CAbstractNamedIdentifier) element));
                    }
                    return true;
                }
            };

            PsiFile file = element.getContainingFile();
            PsiElement lastParent = null;
            PsiElement it = element;
            while (it != null && lastParent != file) {
                if (!processor.execute(it, ResolveState.initial())) return;
                if (!it.processDeclarations(processor, ResolveState.initial(), lastParent, element)) return;
                lastParent = it;
                it = it.getContext();
            }
        }
    }
}
