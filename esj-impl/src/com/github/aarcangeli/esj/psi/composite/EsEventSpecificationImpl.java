package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.lexer.CTokenSets;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.CGenericReference;
import com.github.aarcangeli.esj.psi.EsEventSpecification;
import com.github.aarcangeli.esj.utils.PsiUtils;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.Nullable;

public class EsEventSpecificationImpl extends CAbstractNamedIdentifier implements EsEventSpecification {

    public EsEventSpecificationImpl() {
        super(CElementTypes.SE_EVENT_SPECIFICATION);
    }

    @Override
    @Nullable
    public PsiElement getNameIdentifier() {
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
            return new CGenericReference(this, PsiUtils.getTextRangeInParent(refFrom), refFrom.getText());
        }
        return null;
    }
}
