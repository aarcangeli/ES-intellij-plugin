package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.CGenericReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.tree.CompositePsiElement;

public class SeClassSuperReference extends CompositePsiElement {
    public SeClassSuperReference() {
        super(CElementTypes.SE_CLASS_SUPER_REF);
    }

    @Override
    public PsiReference getReference() {
        CGenericReference result = null;
        if (getTextLength() != 0) {
            result = new CGenericReference(this, new TextRange(0, getTextLength()), getText());
            result.setOnlyClasses(true);
        }
        return result;
    }
}
