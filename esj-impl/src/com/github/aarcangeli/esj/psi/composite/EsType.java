package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.lexer.CTokenSets;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.CGenericReference;
import com.github.aarcangeli.esj.utils.PsiUtils;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.annotations.Nullable;

public class EsType extends CompositePsiElement {
    public EsType() {
        super(CElementTypes.SE_TYPE);
    }

    @Nullable
    public PsiElement getTypeIdentifier() {
        ASTNode[] childrens = getChildren(CTokenSets.IDENTIFIER_SET);
        if (childrens.length > 0) {
            return childrens[0].getPsi();
        }
        return null;
    }

    @Override
    public PsiReference getReference() {
        PsiElement refTo = getTypeIdentifier();
        if (refTo != null) {
            return new CGenericReference(this, PsiUtils.getTextRangeInParent(refTo), refTo.getText());
        }
        return null;
    }
}
