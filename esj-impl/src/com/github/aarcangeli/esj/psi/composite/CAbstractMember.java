package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsClass;
import com.github.aarcangeli.esj.psi.EsMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.tree.IElementType;

public class CAbstractMember extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, EsMember {

    public CAbstractMember(IElementType type) {
        super(type);
    }

    @Override
    public EsClass getContainingClass() {
        PsiElement parent = getParent().getParent();
        assert parent.getNode().getElementType() == CElementTypes.SE_CLASS_STATEMENT;
        return (EsClass) parent;
    }

    @Override
    public PsiElement getContext() {
        return getContainingClass();
    }
}
