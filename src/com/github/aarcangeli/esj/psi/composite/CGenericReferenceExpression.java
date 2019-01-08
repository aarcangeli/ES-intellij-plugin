package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.CGenericReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.tree.CompositePsiElement;

public class CGenericReferenceExpression extends CompositePsiElement {
    public CGenericReferenceExpression() {
        super(CElementTypes.SE_REFERENCE_EXPRESSION);
    }

    @Override
    public PsiReference getReference() {
        return new CGenericReference(this, new TextRange(0, getTextLength()), getText());
    }
}
