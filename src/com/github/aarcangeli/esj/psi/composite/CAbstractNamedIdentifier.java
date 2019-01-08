package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.SePsiUtils;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CAbstractNamedIdentifier extends CompositePsiElement implements PsiNameIdentifierOwner {

    protected CAbstractNamedIdentifier(IElementType type) {
        super(type);
    }

    @Override
    @Nullable
    public PsiElement getNameIdentifier() {
        return findPsiChildByType(CTokens.IDENTIFIER);
    }

    @Override
    public String getName() {
        PsiElement identifier = getNameIdentifier();
        if (identifier != null) {
            return identifier.getText();
        }
        return super.getName();
    }

    @Override
    public PsiElement setName(@NotNull String newName) throws IncorrectOperationException {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            SePsiUtils.replaceWithIdentifier(nameIdentifier, newName);
        }
        return this;
    }

    @Override
    public int getTextOffset() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            return nameIdentifier.getTextRange().getStartOffset();
        }
        return -1;
    }
}
