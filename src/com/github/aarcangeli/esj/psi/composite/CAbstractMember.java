package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.headers.SeClass;
import com.github.aarcangeli.esj.psi.headers.SeMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.tree.IElementType;

public class CAbstractMember extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, SeMember {

    public CAbstractMember(IElementType type) {
        super(type);
    }

    @Override
    public SeClass getContainingClass() {
        PsiElement parent = getParent().getParent();
        assert parent.getNode().getElementType() == CElementTypes.SE_CLASS_STATEMENT;
        return (SeClass) parent;
    }

    @Override
    public PsiElement getContext() {
        return getContainingClass();
    }
}
