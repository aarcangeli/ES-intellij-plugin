package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.github.aarcangeli.esj.utils.PsiUtils;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.scope.BaseScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CGenericReference extends PsiReferenceBase<PsiElement> {
    private final String name;
    private PsiElement resolved;
    private boolean onlyClasses;

    public CGenericReference(@NotNull PsiElement element, @NotNull TextRange textRangeInElement, String name) {
        super(element, true);
        this.name = name;
        setRangeInElement(textRangeInElement);
    }

    @Override
    @Nullable
    public PsiElement resolve() {
        resolved = null;
        findInside(getElement());
        return resolved;
    }

    private void findInside(PsiElement element) {
        PsiUtils.processDeclarations(element, new BaseScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element1, @NotNull ResolveState state) {
                if (element1 instanceof CAbstractNamedIdentifier) {
                    if (onlyClasses && !(element1 instanceof EsClass)) return true;
                    if (Objects.equals(((CAbstractNamedIdentifier) element1).getName(), name)) {
                        resolved = element1;
                        return false;
                    }
                }
                return true;
            }

            @Override
            public <T> T getHint(@NotNull Key<T> hintKey) {
                if (hintKey == PsiUtils.PSI_NAME) {
                    return (T) name;
                }
                return super.getHint(hintKey);
            }
        });
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        return super.isReferenceTo(element);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[]{};
    }

    public void setOnlyClasses(boolean onlyClasses) {
        this.onlyClasses = onlyClasses;
    }
}
