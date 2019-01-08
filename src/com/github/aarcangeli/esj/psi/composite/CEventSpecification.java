package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.lexer.CTokenSets;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.CGenericReference;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.Nullable;

public class CEventSpecification extends CAbstractNamedIdentifier {

    public CEventSpecification() {
        super(CElementTypes.SE_EVENT_SPECIFICATION);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        ASTNode[] childrens = getChildren(CTokenSets.IDENTIFIER_SET);
        if (childrens.length > 1) {
            return childrens[1].getPsi();
        }
        return null;
    }

    @Nullable
    public PsiElement getEventType() {
        ASTNode[] childrens = getChildren(CTokenSets.IDENTIFIER_SET);
        if (childrens.length > 0) {
            return childrens[0].getPsi();
        }
        return null;
    }

    @Override
    public PsiReference getReference() {
        PsiElement refFrom = getEventType();
        if (refFrom != null) {
            return new CGenericReference(this, refFrom.getTextRangeInParent(), refFrom.getText());
        }
        return null;
    }
}
