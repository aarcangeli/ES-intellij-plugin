package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;

public class EsParameter extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public static EsParameter[] EMPTY = new EsParameter[0];

    public EsParameter() {
        super(CElementTypes.SE_PARAMETER);
    }

    @Override
    public PsiElement getContext() {
        PsiElement parent = getParent();
        if (parent.getNode().getElementType() == CElementTypes.SE_PARAMETER_LIST) {
            parent = parent.getParent();
        }
        return parent;
    }
}
